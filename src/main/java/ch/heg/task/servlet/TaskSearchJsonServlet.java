package ch.heg.task.servlet;

import ch.heg.task.entities.Task;
import ch.heg.task.services.TachesServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/tasks/searchjson")
public class TaskSearchJsonServlet extends HttpServlet {

    @Autowired
    TachesServices tachesServices;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String htmlResponse = getSearchResponse(id);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(htmlResponse);
    }

    private String getSearchResponse(String id) {
        Task task = tachesServices.findById(Long.valueOf(id));
        ObjectMapper objectMapper= new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
