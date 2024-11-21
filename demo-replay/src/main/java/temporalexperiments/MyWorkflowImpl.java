package temporalexperiments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

public class MyWorkflowImpl implements MyWorkflow {
    // private static final String PERSON = "GetPerson";

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

    // private final Map<String, ActivityOptions> perActivityMethodOptions = new
    // HashMap<String, ActivityOptions>() {
    // {
    // // A heartbeat time-out is a proof-of life indicator that an activity is
    // still
    // // working.
    // // The 5 second duration used here waits for up to 5 seconds to hear a
    // // heartbeat.
    // // If one is not heard, the Activity fails.
    // // The `withdraw` method is hard-coded to succeed, so this never happens.
    // // Use heartbeats for long-lived event-driven applications.
    // put(PERSON,
    // ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    // }
    // };

    // ActivityStubs enable calls to methods as if the Activity object is local but
    // actually perform an RPC invocation
    private final RandomActivity randomActivityStub = Workflow.newActivityStub(RandomActivity.class,
            defaultActivityOptions, null);

    @Override
    public void collectRandomNumbers(boolean longRunning) {

        System.out.println("The workflow has been initiated - collectRandomNumbers called");

        ArrayList<Integer> numbers = new ArrayList<>();

        do {

            try {
                // Launch `makeNumber` Activity
                numbers.add(randomActivityStub.makeNumber());
                System.out.printf("numbers: %s\n", numbers);
                Workflow.sleep(10000);

            } catch (Exception e) {
                // If the activity throws an exception, report but continue
                System.out.printf("create number failed: %s\n", e.getMessage());
                System.out.flush();

            }
        } while (longRunning);

        // As written, this loop will run indefinitely and we'll never finish the
        // workflow,
        // though perhaps we'll refactor to allow a signal to stop it
        System.out.printf("Finishing workflow - numbers: %s\n", numbers);
        return;
    }
}
