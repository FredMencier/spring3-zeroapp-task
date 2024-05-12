package ch.heg.task.servlet;

import ch.heg.task.entities.Task;
import ch.heg.task.services.TachesServices;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/task/*")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    TachesServices tachesServices;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestURI = request.getRequestURI();
        String htmlResponse = "";
        if (requestURI.equals("/task/search")) {
            String id = request.getParameter("id");
            if (id != null) {
                htmlResponse = getSearchResponse(id);
            } else {
                htmlResponse = "id is null";
            }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(htmlResponse);
    }

    private String getSearchResponse(String id) {
        Task task = tachesServices.findById(Long.valueOf(id));
        return """
                <h3>Found Task : %s</h3>
                <ul>
                    <li>id : %s
                    <li>creation date : %s
                    <li>status : %s
                    <li>email : %s
                </ul>
            """.formatted(task.getDescription(), task.getId(), task.getDateCreation(), task.getStatut(), task.getEmailResponsable());
    }
}
