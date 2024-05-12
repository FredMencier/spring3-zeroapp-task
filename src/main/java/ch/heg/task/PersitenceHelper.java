package ch.heg.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@Component
public class PersitenceHelper {

    private static final Logger LOG = LoggerFactory.getLogger(PersitenceHelper.class);
    private static final EntityManagerFactory emf;

    /**
     * Static block for creating EntityManagerFactory. The Persistence class looks for META-INF/persistence.xml in the classpath.
     */
    static {
        LOG.info("Creating EntityManagerFactory for persitence unit : TaskPU");
        emf = Persistence.createEntityManagerFactory("TaskPU");
    }

    /**
     * Static method returning EntityManager.
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
