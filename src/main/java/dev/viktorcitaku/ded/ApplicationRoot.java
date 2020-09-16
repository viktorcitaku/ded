package dev.viktorcitaku.ded;

import dev.viktorcitaku.ded.resources.HelloResource;
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
    return classes;
  }
}
