package dev.viktorcitaku.ded.resources;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/hello")
@RequestScoped
public class HelloResource {

  private static final Logger LOGGER = Logger.getLogger(HelloResource.class.getName());

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponse(
      responseCode = "200",
      description = "Simple message.",
      content = {
        @Content(
            mediaType = "application/json",
            schema = @Schema(type = SchemaType.OBJECT, example = "{\"message\":\"Hello World!\"}"))
      })
  @Operation(summary = "Simple endpoint that returns a Hello World message.")
  public Response getHelloWorld() {
    LOGGER.info("Hello World!");
    return Response.ok(Json.createObjectBuilder().add("message", "Hello World!").build()).build();
  }
}
