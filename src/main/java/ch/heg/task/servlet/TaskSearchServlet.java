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

@WebServlet(urlPatterns = "/tasks/search")
public class TaskSearchServlet extends HttpServlet {

    @Autowired
    private TachesServices tachesServices;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String htmlResponse = "";
        if (id != null) {
            htmlResponse = getSearchResponse(id);
        } else {
            htmlResponse = "id is null";
        }
        htmlResponse = htmlResponse + "<br><br><a href=\"http://localhost:8081/index.html\">http://localhost:8081/index.html</a>";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(htmlResponse);
    }

    /**
     *
     * @param id
     * @return
     */
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
