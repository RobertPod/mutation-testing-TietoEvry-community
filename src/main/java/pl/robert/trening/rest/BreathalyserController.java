package pl.robert.trening.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pl.robert.trening.breathalyser.CalculateAlcoholImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.TreeMap;

@RestController
public class BreathalyserController {

    @PostMapping("/whenICanDrive")
    ResponseEntity<Integer> whenCanIDrive(@RequestBody String drinkingHistoryJson) {
        ResponseEntity<Integer> result = null;
        Gson gson = new Gson();
        Type type = new TypeToken<TreeMap<Integer, Integer>>() {}.getType();
        TreeMap<Integer, Integer> timeTable = gson.fromJson(drinkingHistoryJson, type);

        CalculateAlcoholImpl calculateAlcohol = new CalculateAlcoholImpl();
        try {
            calculateAlcohol.drink(timeTable);
        } catch (IllegalArgumentException e) {
            result = new ResponseEntity<>(null, null, HttpStatus.NOT_ACCEPTABLE);
        }
        if (result == null) {
            result = new ResponseEntity<>(calculateAlcohol.whenCanIDrive(), HttpStatus.OK);
        }
        return result;
    }
}
