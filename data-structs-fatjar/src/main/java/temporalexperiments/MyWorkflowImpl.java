package temporalexperiments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

public class MyWorkflowImpl implements MyWorkflow {
    private static final String PERSON = "GetPerson";

    // RetryOptions specify how to automatically handle retries when Activities fail
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1)) // Wait 1 second before first retry
            .setMaximumInterval(Duration.ofSeconds(20)) // Do not exceed 20 seconds between retries
            .setBackoffCoefficient(2) // Wait 1 second, then 2, then 4, etc
            .setMaximumAttempts(7) // Fail after 5000 attempts
            .build();

    // ActivityOptions specify the limits on how long an Activity can execute before
    // being interrupted by the Orchestration service
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            .setRetryOptions(retryoptions) // Apply the RetryOptions defined above
            .setStartToCloseTimeout(Duration.ofSeconds(2)) // Max execution time for single Activity
            .setScheduleToCloseTimeout(Duration.ofSeconds(5000)) // Entire duration from scheduling to completion
                                                                 // including queue time
            .build();

    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<String, ActivityOptions>() {
        {
            // A heartbeat time-out is a proof-of life indicator that an activity is still
            // working.
            // The 5 second duration used here waits for up to 5 seconds to hear a
            // heartbeat.
            // If one is not heard, the Activity fails.
            // The `withdraw` method is hard-coded to succeed, so this never happens.
            // Use heartbeats for long-lived event-driven applications.
            put(PERSON, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
        }
    };

    // ActivityStubs enable calls to methods as if the Activity object is local but
    // actually perform an RPC invocation
    private final PersonActivity personActivityStub = Workflow.newActivityStub(PersonActivity.class,
            defaultActivityOptions, perActivityMethodOptions);

    @Override
    public ArrayList<Person> getPeople() {

        System.out.println("getPeople() called");

        ArrayList<Person> people = new ArrayList<>();

        try {
            // Launch `getPerson` Activity
            people.add(personActivityStub.makePerson("Alice", 30));
            people.add(personActivityStub.makePerson("Bob", 25));
            people.add(personActivityStub.makePerson("Charlie", 35));
            // personActivityStub.getPerson("foo");
        } catch (Exception e) {
            // If the withdrawal fails, for any exception, it's caught here
            System.out.printf("create Person failed: %s\n", e.getMessage());
            System.out.flush();

            // Transaction ends here
            return null;
        }

        return people;
    }
}
