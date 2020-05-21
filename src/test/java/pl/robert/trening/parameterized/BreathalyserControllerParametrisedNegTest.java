package pl.robert.trening.parameterized;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.TreeMap;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.robert.trening.breathalyser.CalculateAlcohol;
import pl.robert.trening.breathalyser.CalculateAlcoholImpl;

public class BreathalyserControllerParametrisedNegTest {

  @ParameterizedTest
  @MethodSource("createTreeMaps")
  void whenCanIDriveDrink1l2p2h(TreeMap<Integer, Integer> drinkingHistory, int hour) {
    // Given
    CalculateAlcohol calculateAlcohol = new CalculateAlcoholImpl();
    // When
    // Then
    assertThrows(IllegalArgumentException.class, () -> {
      calculateAlcohol.drink(drinkingHistory);
    });
  }

  private static Stream<Arguments> createTreeMaps() {
    TreeMap<Integer, Integer> drinkingHistory1 = new TreeMap<>();
    drinkingHistory1.put(25, 10);
    drinkingHistory1.put(2, 1);
    TreeMap<Integer, Integer> drinkingHistory2 = new TreeMap<>();
    drinkingHistory2.put(11, 1);
    drinkingHistory2.put(2, 100);
    return Stream.of(
        Arguments.of(drinkingHistory1, 21),
        Arguments.of(drinkingHistory2, 3));
  }
}
