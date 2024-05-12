package ch.heg.task.services;

import ch.heg.task.PersitenceHelper;
import ch.heg.task.entities.Person;
import ch.heg.task.entities.Statut;
import ch.heg.task.entities.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class TachesServices {

    private static final Logger LOG = LoggerFactory.getLogger(TachesServices.class);

    /**
     * add new Task
     * @param task
     * @return
     */
    public Task addTask(final Task task) {
        EntityManager entityManager = PersitenceHelper.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(task);
        transaction.commit();
        return task;
    }

    /**
     * find task by id
     * @param id
     * @return
     */
    public Task findById(final Long id) {
        return PersitenceHelper.getEntityManager().find(Task.class, id);
    }

    /**
     * find task by statut
     * @param statut
     * @return
     */
    public List<Task> findByStatus(final Statut statut) {
        TypedQuery<Task> namedQuery = PersitenceHelper.getEntityManager().createQuery("SELECT t FROM Task t where t.statut = :statut", Task.class);
        namedQuery.setParameter("statut", statut);
        return namedQuery.getResultList();
    }

    /**
     * Update task
     * @param task
     * @return
     */
    public Task updateTask(final Task task) {
        EntityManager entityManager = PersitenceHelper.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(task);
        transaction.commit();
        return task;
    }

    /**
     * add multiple task
     * @param tasks
     */
    public void addTasks(final List<Task> tasks) {
        EntityManager entityManager = PersitenceHelper.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            for (Task task: tasks) {
                entityManager.persist(task);
            }
            transaction.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            transaction.rollback();
        }
    }

    /**
     * add multiple task
     * @param lists
     */
    public List<String> addTasksBestEffort(final List<List<String>> lists) {
        List<String> errorList = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        for (List<String> line : lists) {
            if (line.size() == 5) {
                try {
                    tasks.add(buildTaskFromLine(line));
                } catch (Exception e) {
                    LOG.error("Unable to create task from line " + line);
                    errorList.add(line.toString());
                }
            } else {
                LOG.error("Unable to create task from line " + line);
                errorList.add(line.toString());
            }
        }

        EntityManager entityManager = PersitenceHelper.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        for (Task task: tasks) {
            try {
                transaction.begin();
                entityManager.persist(task);
                transaction.commit();
            } catch (Exception e) {
                LOG.error(e.getMessage());
                transaction.rollback();
                errorList.add(task.toString());
            }
        }
        return errorList;
    }


    /**
     * helper method to crete task object
     * @param line
     * @return
     * @throws Exception
     */
    private Task buildTaskFromLine(final List<String> line) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        Task newTask = new Task();
        newTask.setDescription(line.get(0));
        String creationDate = line.get(1);
        if (creationDate != null && !creationDate.isEmpty()) {
            newTask.setDateCreation(sdf.parse(creationDate));
        }
        String executionDate = line.get(2);
        if (executionDate != null && !executionDate.isEmpty()) {
            newTask.setDateExecution(sdf.parse(executionDate));
        }
        newTask.setStatut(Statut.valueOf(line.get(3)));
        newTask.setEmailResponsable(line.get(4));
        return newTask;
    }

    /**
     * add new person
     * @param person
     * @return
     */
    public Person addPerson(final Person person) {
        EntityManager entityManager = PersitenceHelper.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(person);
        transaction.commit();
        return person;
    }

    /**
     *
     * @param personId
     * @return
     */
    public Person findPersonById(final Long personId) {
        return PersitenceHelper.getEntityManager().find(Person.class, personId);
    }
}
