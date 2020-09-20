package dev.viktorcitaku.ded.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import lombok.Getter;

@ApplicationScoped
public class UserRepo {

  private static final Logger LOGGER = Logger.getLogger(UserRepo.class.getName());

  @Getter private final Map<String, User> database = new HashMap<>();

  public void saveUser(String email) {
    var user = new User();
    user.setEmail(email);
    saveUser(user);
  }

  public void saveUser(User user) {
    if (user != null) {
      database.put(user.getEmail(), user);
    } else {
      LOGGER.warning("The User is null!");
    }
  }
}
