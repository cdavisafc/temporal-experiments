package temporalexperiments;

import java.util.Random;

public class RandomActivityImpl implements RandomActivity {
    // generate a new random number
    @Override
    public int makeNumber() {
        Random rand = new Random();
        int number = rand.nextInt(100);
        System.out.printf("\n[ACTIVITY] creating number: %d\n", number);
        System.out.flush();
        return number;
    }

}
