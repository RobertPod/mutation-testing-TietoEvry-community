package pl.robert.trening.properties;

import pl.robert.trening.breathalyser.CalculateAlcohol;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import pl.robert.trening.breathalyser.CalculateAlcoholImpl;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BreathalyserControllerPropertiesTest {

    @Example
    void simpleTest() {
        // Given
        CalculateAlcohol calculateAlcohol = new CalculateAlcoholImpl();
        // When
        TreeMap<Integer, Integer> drinkingHistory = new TreeMap<>();
        drinkingHistory.put(2, 10);
        calculateAlcohol.drink(drinkingHistory);
        // Then
        assertEquals(12, calculateAlcohol.whenCanIDrive(), "ćwiarteczka");
    }

    @Property
    void simplePositiveProperties(@ForAll @IntRange(min = 1, max = 24) int key,
                                  @ForAll @IntRange(min = 1, max = 20) int value) {
        // Given
        CalculateAlcohol calculateAlcohol = new CalculateAlcoholImpl();
        // When
        TreeMap<Integer, Integer> drinkingHistory = new TreeMap<>();
        drinkingHistory.put(key, value);
        calculateAlcohol.drink(drinkingHistory);
        // Then
        int hoursForSobriety = calculateAlcohol.whenCanIDrive();
        System.out.println("t = " + key + "\tunit = " + value + "\thours = " + hoursForSobriety);
        assertEquals(key + value, calculateAlcohol.whenCanIDrive());
    }

}
