package dev.viktorcitaku.ded.servlets;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "/",
    urlPatterns = {
      "*.html",
      "*.css",
      "*.css.map",
      "*.js",
      "*.json",
      "*.png",
      "*.ttf",
      "*.svg",
      "*.eot",
      "*.woff"
    })
public class StaticFileServlet extends HttpServlet {

  private static final Logger LOGGER = Logger.getLogger(StaticFileServlet.class.getName());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    var requestURI = req.getRequestURI();
    var filePath = requestURI.substring(req.getContextPath().length());
    if (filePath.equals("/")) {
      filePath += "index.html";
    }
    try (var indexHtml = getServletContext().getResourceAsStream(filePath)) {
      String mimeType = getContentTypeFor(filePath);
      LOGGER.info(
          String.format(
              "The Request URI: %s\n  The File Path: %s\n  The content type: %s\n",
              requestURI, filePath, mimeType));
      resp.setStatus(HttpServletResponse.SC_OK);
      resp.setContentType(mimeType);
      indexHtml.transferTo(resp.getOutputStream());
    } catch (IOException ioe) {
      LOGGER.severe(ioe.getMessage());
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().println("Something went wrong!");
    }
  }

  private String getContentTypeFor(String filePath) {
    if (filePath == null) {
      return "text/plain"; // The default when we don't know
    }

    if (filePath.endsWith(".js")) {
      return "application/javascript";
    } else if (filePath.endsWith(".css")) {
      return "text/css";
    } else if (filePath.endsWith(".html")) {
      return "text/html";
    } else if (filePath.endsWith(".json")) {
      return "application/json";
    } else if (filePath.endsWith(".png")) {
      return "image/png";
    } else if (filePath.endsWith(".svg")) {
      return "image/svg+xml";
    } else if (filePath.endsWith(".ttf")) {
      return "font/ttf";
    } else if (filePath.endsWith(".woff")) {
      return "font/woff";
    } else {
      return ""; // The default when we don't know
    }
  }
}
