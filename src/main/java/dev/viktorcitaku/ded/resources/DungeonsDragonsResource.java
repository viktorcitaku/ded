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

@Path("/dnd")
@RequestScoped
public class DungeonsDragonsResource {

  private static final Logger LOGGER = Logger.getLogger(DungeonsDragonsResource.class.getName());

  @Inject UserRepo userRepo;

  @Inject UserCharacterRepo userCharacterRepo;

  @GET
  @Path("/classes")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getClasses() throws IOException, InterruptedException {
    // Retrieve the Array and transform into the Object
    final var reader = doHttpCall("https://www.dnd5eapi.co/api/classes");
    var jsonArray = parseIntoAbstractJsonValue(reader);
    return Response.ok(jsonArray).build();
  }

  @GET
  @Path("/races")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRaces() throws IOException, InterruptedException {
    // Retrieve the Array and transform into the Object
    final var reader = doHttpCall("https://www.dnd5eapi.co/api/races");
    var jsonArray = parseIntoAbstractJsonValue(reader);
    return Response.ok(jsonArray).build();
  }

  @GET
  @Path("/equipment")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getEquipment() throws IOException, InterruptedException {
    // Retrieve the Array and transform into the Object
    final var reader = doHttpCall("https://www.dnd5eapi.co/api/equipment");
    var jsonArray = parseIntoAbstractJsonValue(reader);
    return Response.ok(jsonArray).build();
  }

  @GET
  @Path("/spells/{clazz}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSpells(@PathParam("clazz") @NonNull String clazz)
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
  public Response saveCharacter(
      @CookieParam("DED_SESSION") String email, DungeonsDragonsPayload payload) {
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
