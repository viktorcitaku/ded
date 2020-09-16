package dev.viktorcitaku.ded.resources;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@RequestScoped
public class HelloResource {

  private static final Logger LOGGER = Logger.getLogger(HelloResource.class.getName());

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getHelloWorld() {
    LOGGER.info("Hello World!");
    return Response.ok(Json.createObjectBuilder().add("message", "Hello World!").build()).build();
  }
}
