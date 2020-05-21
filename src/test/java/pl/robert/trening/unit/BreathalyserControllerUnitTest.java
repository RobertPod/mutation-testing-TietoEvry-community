package pl.robert.trening.unit;

import pl.robert.trening.breathalyser.CalculateAlcohol;
import pl.robert.trening.breathalyser.CalculateAlcoholImpl;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BreathalyserControllerUnitTest {

    @Test
    void whenCanIDriveNoDrink() {
        // Given
        CalculateAlcohol calculateAlcohol = new CalculateAlcoholImpl();
        // When
        // Then
        assertEquals(0, calculateAlcohol.whenCanIDrive(), "NoDrink");
    }

    @Test
    void whenCanIDriveDrink1l2p2h() {
        // Given
        CalculateAlcohol calculateAlcohol = new CalculateAlcoholImpl();
        TreeMap<Integer, Integer> drinkingHistory = new TreeMap<>();
        drinkingHistory.put(1, 10);
        drinkingHistory.put(2, 10);
        // When
        calculateAlcohol.drink(drinkingHistory);
        // Then
        assertEquals(21, calculateAlcohol.whenCanIDrive(), "1l2p2h");
    }
}