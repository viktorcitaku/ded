package dev.viktorcitaku.ded.repository;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class User {

  @Getter @Setter private String email;

  @Getter private final LocalDateTime lastUpdated;

  public User() {
    lastUpdated = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }
}
