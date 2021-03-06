package dev.viktorcitaku.ded.servlets;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/swagger-ui"})
public class SwaggerUiServlet extends HttpServlet {

  private static final Logger LOGGER = Logger.getLogger(SwaggerUiServlet.class.getName());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    var servletContext = getServletContext();
    try (var swaggerUiHtml = servletContext.getResourceAsStream("/swagger/swagger-ui.html")) {
      resp.setStatus(HttpServletResponse.SC_OK);
      swaggerUiHtml.transferTo(resp.getOutputStream());
    } catch (IOException ex) {
      LOGGER.severe(ex.getMessage());
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().println("Something went wrong!");
    }
  }
}
