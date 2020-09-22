package dev.viktorcitaku.ded.resources;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Email;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/users")
@SessionScoped
public class UserResource implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @APIResponses(
      value = {
        @APIResponse(
            responseCode = "200",
            description = "It returns a cookie with key DED_SESSION"),
        @APIResponse(
            responseCode = "500",
            description = "Returned when the provided email is invalid.")
      })
  @Operation(summary = "Registers a user with given email.")
  public Response saveUser(
      @Parameter(
              name = "email",
              description = "The email address used for registration",
              example = "drake@owl.com",
              content = @Content(mediaType = "application/x-www-form-urlencoded"))
          @FormParam("email")
          @Email
          String email) {
    if (email != null && !email.isEmpty()) {
      LOGGER.info(String.format("The email is: %s\n", email));
      var cookie = new NewCookie("DED_SESSION", email);
      return Response.ok().cookie(cookie).build();
    } else {
      return Response.serverError().entity("The email provided is not valid!").build();
    }
  }
}
