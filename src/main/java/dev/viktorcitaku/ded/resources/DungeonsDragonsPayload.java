package dev.viktorcitaku.ded.resources;

import java.util.List;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class DungeonsDragonsPayload {

  @Getter @Setter private String name;

  @Getter @Setter private int age;

  @Getter @Setter private String clazz;

  @Getter @Setter private String race;

  @Getter @Setter private List<String> equipment;

  @Getter @Setter private List<String> spells;
}
