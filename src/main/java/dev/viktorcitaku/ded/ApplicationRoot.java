package dev.viktorcitaku.ded;

import dev.viktorcitaku.ded.resources.DungeonsDragonsResource;
import dev.viktorcitaku.ded.resources.HelloResource;
import dev.viktorcitaku.ded.resources.UserResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApplicationRoot extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    var classes = new HashSet<Class<?>>();
    classes.add(HelloResource.class);
    classes.add(UserResource.class);
    classes.add(DungeonsDragonsResource.class);
    return classes;
  }
}
