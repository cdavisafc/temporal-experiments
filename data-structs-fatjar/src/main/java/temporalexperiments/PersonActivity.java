package temporalexperiments;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PersonActivity {
    // Withdraw an amount of money from the source account
    @ActivityMethod
    Person makePerson(String name, int age);
}
