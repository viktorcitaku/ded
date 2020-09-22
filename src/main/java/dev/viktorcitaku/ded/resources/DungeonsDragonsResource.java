package dev.viktorcitaku.ded.resources;

import dev.viktorcitaku.ded.repository.UserCharacter;
import dev.viktorcitaku.ded.repository.UserCharacterRepo;
import dev.viktorcitaku.ded.repository.UserRepo;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.NonNull;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

@SecuritySchemes({
  @SecurityScheme(
      type = SecuritySchemeType.APIKEY,
      in = SecuritySchemeIn.COOKIE,
      apiKeyName = "DED_SESSION")
})
@Path("/dnd")
@RequestScoped
public class DungeonsDragonsResource {

  private static final Logger LOGGER = Logger.getLogger(DungeonsDragonsResource.class.getName());

  @Inject UserRepo userRepo;

  @Inject UserCharacterRepo userCharacterRepo;

  @GET
  @Path("/classes")
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponses(
      value = {
        @APIResponse(
            responseCode = "200",
            description = "The list of Character classes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  example =
                      "[{\"value\":\"foo\",\"label\":\"Foo\"},{\"value\":\"bar\",\"label\":\"Bar\"}]")
            }),
        @APIResponse(responseCode = "500", description = "If something goes wrong.")
      })
  @Operation(summary = "Returns a list of Character classes.")
  public Response getClasses() throws IOException, InterruptedException {
    // Retrieve the Array and transform into the Object
    final var reader = doHttpCall("https://www.dnd5eapi.co/api/classes");
    var jsonArray = parseIntoAbstractJsonValue(reader);
    return Response.ok(jsonArray).build();
  }

  @GET
  @Path("/races")
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponses(
      value = {
        @APIResponse(
            responseCode = "200",
            description = "The list of Character races.",
            content = {
              @Content(
                  mediaType = "application/json",
                  example =
                      "[{\"value\":\"foo\",\"label\":\"Foo\"},{\"value\":\"bar\",\"label\":\"Bar\"}]")
            }),
        @APIResponse(responseCode = "500", description = "If something goes wrong.")
      })
  @Operation(summary = "Returns a list of Character races.")
  public Response getRaces() throws IOException, InterruptedException {
    // Retrieve the Array and transform into the Object
    final var reader = doHttpCall("https://www.dnd5eapi.co/api/races");
    var jsonArray = parseIntoAbstractJsonValue(reader);
    return Response.ok(jsonArray).build();
  }

  @GET
  @Path("/equipment")
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponses(
      value = {
        @APIResponse(
            responseCode = "200",
            description = "The list of Character equipment.",
            content = {
              @Content(
                  mediaType = "application/json",
                  example =
                      "[{\"value\":\"foo\",\"label\":\"Foo\"},{\"value\":\"bar\",\"label\":\"Bar\"}]")
            }),
        @APIResponse(responseCode = "500", description = "If something goes wrong.")
      })
  @Operation(summary = "Returns a list of Character equipment.")
  public Response getEquipment() throws IOException, InterruptedException {
    // Retrieve the Array and transform into the Object
    final var reader = doHttpCall("https://www.dnd5eapi.co/api/equipment");
    var jsonArray = parseIntoAbstractJsonValue(reader);
    return Response.ok(jsonArray).build();
  }

  @GET
  @Path("/spells/{clazz}")
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponses(
      value = {
        @APIResponse(
            responseCode = "200",
            description = "The list of Character classes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  example =
                      "[{\"value\":\"foo\",\"label\":\"Foo\"},{\"value\":\"bar\",\"label\":\"Bar\"}]")
            }),
        @APIResponse(responseCode = "500", description = "If something goes wrong.")
      })
  @Operation(summary = "Returns a list of Character classes.")
  public Response getSpells(
      @Parameter(
              required = true,
              example = "foo",
              schema = @Schema(type = SchemaType.STRING, example = "foo"))
          @PathParam("clazz")
          @NonNull
          String clazz)
      throws IOException, InterruptedException {
    LOGGER.info(String.format("The Class to look for Spells: %s\n", clazz));
    // Retrieve the Array and transform into the Object
    final var reader = doHttpCall("https://www.dnd5eapi.co/api/classes/" + clazz + "/spells");
    var jsonArray = parseIntoAbstractJsonValue(reader);
    return Response.ok(jsonArray).build();
  }

  @POST
  @Path("/characters")
  @Consumes(MediaType.APPLICATION_JSON)
  @APIResponses(
      value = {
        @APIResponse(
            responseCode = "200",
            description = "If payload was successfully saved.",
            headers = {
              @Header(
                  required = true,
                  name = "Set-Cookie",
                  description = "The cookie DED_SESSION should be set.")
            }),
        @APIResponse(responseCode = "500", description = "If something went wrong."),
      })
  @Operation(summary = "Saves character by given email (transmitted via Cookie)")
  @SecurityRequirement(name = "DED_SESSION")
  public Response saveCharacter(
      @CookieParam("DED_SESSION") String email,
      @Parameter(
              required = true,
              schema =
                  @Schema(type = SchemaType.OBJECT, implementation = DungeonsDragonsPayload.class))
          DungeonsDragonsPayload payload) {
    // Retrieve the Array and transform into the Object
    LOGGER.info(String.format("The email that the character is going to be saved: %s\n", email));

    userRepo.saveUser(email); // this does an update!

    var userCharacter = UserCharacter.fromDungeonsDragonsPayload(payload);

    userCharacterRepo.saveCharacter(email, userCharacter);

    return Response.ok().build();
  }

  @GET
  @Path("/characters")
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponses(
      value = {
        @APIResponse(
            responseCode = "200",
            description = "The list of saved characters",
            headers = {
              @Header(
                  required = true,
                  name = "Set-Cookie",
                  description = "The cookie DED_SESSION should be set.")
            },
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            type = SchemaType.ARRAY,
                            implementation = DungeonsDragonsPayload.class))),
        @APIResponse(responseCode = "500", description = "If something went wrong."),
      })
  @Operation(
      summary = "Returns the list of saved character by given email (transmitted via Cookie)")
  @SecurityRequirement(name = "DED_SESSION")
  public Response getCharacters(@CookieParam("DED_SESSION") String email) {
    // Retrieve the Array and transform into the Object
    LOGGER.info(String.format("The email that the characters are going to be loaded: %s\n", email));

    userRepo.saveUser(email); // this does an update!

    var charactersDatabase = userCharacterRepo.getCharacters();
    var characters = charactersDatabase.get(email);
    var payloads =
        characters.stream()
            .map(UserCharacter::toDungeonsDragonsPayload)
            .collect(Collectors.toList());

    return Response.ok(payloads).build();
  }

  private JsonReader doHttpCall(@NonNull final String url)
      throws IOException, InterruptedException {
    var uri = URI.create(url);
    var request = HttpRequest.newBuilder(uri).GET().build();
    var response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
    return Json.createReader(new StringReader(response.body()));
  }

  // luckily the API has quite a consistent response body
  private JsonArray parseIntoAbstractJsonValue(@NonNull final JsonReader reader) {
    var jsonArrayBuilder = Json.createArrayBuilder();
    var jsonObject = reader.readObject();
    if (jsonObject != null) {
      var jsonArray = jsonObject.getJsonArray("results");
      for (var i = 0; i < jsonArray.size(); i++) {

        // Extract the JSON values
        var object = jsonArray.getJsonObject(i);
        var index = object.getString("index");
        var name = object.getString("name");

        // Construct the familiar response
        var customObject =
            Json.createObjectBuilder().add("value", index).add("label", name).build();

        jsonArrayBuilder.add(customObject);
      }
    }

    return jsonArrayBuilder.build();
  }
}
