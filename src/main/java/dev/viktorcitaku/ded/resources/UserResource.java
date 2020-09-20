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

@Path("/users")
@SessionScoped
public class UserResource implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response saveUser(@FormParam("email") @Email String email) {
    if (email != null && !email.isEmpty()) {
      LOGGER.info(String.format("The email is: %s\n", email));
      var cookie = new NewCookie("DED_SESSION", email);
      return Response.ok().cookie(cookie).build();
    } else {
      return Response.serverError().entity("The email provided is not valid!").build();
    }
  }
}
