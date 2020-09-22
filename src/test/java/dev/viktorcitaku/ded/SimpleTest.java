package dev.viktorcitaku.ded;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(Lifecycle.PER_CLASS)
public class SimpleTest {

  @ParameterizedTest
  @ValueSource(strings = {"apple", "bannana", "orange"})
  void simpleScenario(String fruits) {
    Assumptions.assumeTrue(fruits.contains("a"));
  }
}
