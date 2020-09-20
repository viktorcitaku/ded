package dev.viktorcitaku.ded.repository;

import dev.viktorcitaku.ded.resources.DungeonsDragonsPayload;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserCharacter {

  @Getter @Setter private String name;

  @Getter @Setter private int age;

  @Getter @Setter private String characterClass;

  @Getter @Setter private String characterRace;

  @Getter @Setter private List<String> characterEquipments;

  @Getter @Setter private List<String> characterSpells;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCharacter character = (UserCharacter) o;
    return Objects.equals(name, character.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  public static UserCharacter fromDungeonsDragonsPayload(DungeonsDragonsPayload payload) {
    var character = new UserCharacter();
    character.name = payload.getName();
    character.age = payload.getAge();
    character.characterClass = payload.getClazz();
    character.characterRace = payload.getRace();
    character.characterEquipments = payload.getEquipment();
    character.characterSpells = payload.getSpells();
    return character;
  }

  public DungeonsDragonsPayload toDungeonsDragonsPayload() {
    var payload = new DungeonsDragonsPayload();
    payload.setName(this.name);
    payload.setAge(this.age);
    payload.setClazz(this.characterClass);
    payload.setRace(this.characterRace);
    payload.setEquipment(this.characterEquipments);
    payload.setSpells(this.characterSpells);
    return payload;
  }
}
