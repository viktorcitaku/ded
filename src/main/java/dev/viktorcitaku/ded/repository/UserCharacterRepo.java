package dev.viktorcitaku.ded.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import lombok.Getter;

@ApplicationScoped
public class UserCharacterRepo {

  private static final Logger LOGGER = Logger.getLogger(UserCharacterRepo.class.getName());

  @Getter private final Map<String, List<UserCharacter>> characters = new HashMap<>();

  public void saveCharacter(String email, UserCharacter character) {
    var list = this.characters.get(email);
    if (list == null) {
      list = new ArrayList<>();
    }
    list.add(character);
    characters.put(email, list);
  }
}
