package pl.robert.trening.breathalyser;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class CalculateAlcoholImpl implements CalculateAlcohol {

    private DrinkingHistory drinkingHistory = new DrinkingHistory();

    @Override
    public void drink(TreeMap<Integer, Integer> drunkHistory) {

        inputDataValidation(drunkHistory);
        drinkingHistory.setTimeTable(drunkHistory);
    }

    @Override
    public int whenCanIDrive() {
        TreeMap<Integer, Integer> timeTable = drinkingHistory.getTimeTable();
        if (timeTable.size() == 0) {
            return 0;
        }

        int maxKey = timeTable.lastKey();
        while (!canIDrive(maxKey)) {
            ++maxKey;
        }
        return maxKey;
    }

    private boolean canIDrive(int time) {
        TreeMap<Integer, Integer> timeTable = drinkingHistory.getTimeTable();
        if (timeTable.size() == 0) {
            return true;
        }

        return Stream
                .iterate(new int[]{0, timeTable.get(0) == null ? 0 : timeTable.get(0)},
                        p -> new int[]{p[0] + 1,
                                p[1] + (p[1] > 0 ? -1 : 0) + (timeTable.get(p[0] + 1) == null ? 0
                                        : timeTable.get(p[0] + 1))
                        })
                .limit((long) time + 1)
                .skip(time)
                .findFirst()
                .get()[1] == 0;
    }

    private void inputDataValidation(TreeMap<Integer, Integer> drunkHistory) {
        for (Map.Entry<Integer, Integer> entry : drunkHistory.entrySet()) {
            if (entry.getKey() < 1 || entry.getKey() > 24)
                throw new IllegalArgumentException("We can drink 24 hours, start in 1");
            if (entry.getValue() < 1)
                throw new IllegalArgumentException("Cola is not alcohol");
            if (entry.getValue() > 20)
                throw new IllegalArgumentException("You've drunk too much, you're dead");
        }
    }
}
