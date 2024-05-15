package ch.heg.task.servlet;

import ch.heg.task.entities.Statut;
import ch.heg.task.entities.Task;
import ch.heg.task.services.TachesServices;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/tasks/create")
public class TaskCreateServlet extends HttpServlet {

    @Autowired
    private TachesServices tachesServices;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String desc = request.getParameter("desc");
        String mail = request.getParameter("mail");
        String htmlResponse = "";
        if (desc != null && mail != null) {
            Task task = new Task();
            task.setDescription(desc);
            task.setEmailResponsable(mail);
            task.setDateCreation(new Date());
            task.setStatut(Statut.TODO);
            Task taskCreated = tachesServices.addTask(task);
            htmlResponse = "Task Created with id : %s".formatted(taskCreated.getId());
        } else {
            htmlResponse = "bad parameters";
        }
        htmlResponse = htmlResponse + "<br><br><a href=\"http://localhost:8081/index.html\">http://localhost:8081/index.html</a>";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(htmlResponse);
    }



}
