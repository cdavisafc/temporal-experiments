package temporalexperiments;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface RandomActivity {
    // just going to return a random number
    @ActivityMethod
    int makeNumber();
}
