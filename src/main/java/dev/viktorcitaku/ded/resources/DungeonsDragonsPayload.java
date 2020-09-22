package dev.viktorcitaku.ded.resources;

import java.util.List;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(required = true, description = "The Dungeons and Dragons payload aka the Character.")
@NoArgsConstructor
@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class DungeonsDragonsPayload {

  @Schema(required = true)
  @Getter
  @Setter
  private String name;

  @Schema(required = true)
  @Getter
  @Setter
  private int age;

  @Schema(required = true)
  @Getter
  @Setter
  private String clazz;

  @Schema(required = true)
  @Getter
  @Setter
  private String race;

  @Schema(required = true, type = SchemaType.ARRAY, implementation = String.class)
  @Getter
  @Setter
  private List<String> equipment;

  @Schema(required = true, type = SchemaType.ARRAY, implementation = String.class)
  @Getter
  @Setter
  private List<String> spells;
}
