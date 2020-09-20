package dev.viktorcitaku.ded.timer;

import dev.viktorcitaku.ded.repository.User;
import dev.viktorcitaku.ded.repository.UserCharacterRepo;
import dev.viktorcitaku.ded.repository.UserRepo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CleanDatabase {

  private static final Logger LOGGER = Logger.getLogger(CleanDatabase.class.getName());

  @Resource ManagedScheduledExecutorService managedScheduledExecutorService;

  @Inject UserRepo userRepo;

  @Inject UserCharacterRepo userCharacterRepo;

  @PostConstruct
  public void postConstruct() {
    managedScheduledExecutorService.scheduleWithFixedDelay(
        () -> {
          StringBuilder logString = new StringBuilder("\n===== START DB CLEANUP ====\n");
          var now = LocalDateTime.now();
          var userRepoDatabase = userRepo.getDatabase();
          var charactersRepoDatabase = userCharacterRepo.getCharacters();
          for (Map.Entry<String, User> entry : userRepoDatabase.entrySet()) {
            var user = entry.getValue();
            if (Duration.between(user.getLastUpdated(), now).abs().toMinutes() > 10) {
              logString.append(
                  String.format("Data for %s is going to be deleted!\n", entry.getKey()));
              userRepoDatabase.remove(entry.getKey());
              charactersRepoDatabase.remove(entry.getKey());
            }
          }
          logString.append("===== END DB CLEANUP ====\n");
          LOGGER.info(logString.toString());
        },
        10,
        60,
        TimeUnit.SECONDS);
  }
}
