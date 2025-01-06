package temporalexperiments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

public class MyWorkflowImpl implements MyWorkflow {

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

    // ActivityStubs enable calls to methods as if the Activity object is local but
    // actually perform an RPC invocation
    private final RandomActivity randomActivityStub = Workflow.newActivityStub(RandomActivity.class,
            defaultActivityOptions, null);

    private ArrayList<Integer> numbers;

    @Override
    public void collectRandomNumbers(boolean longRunning) {

        System.out.println("[WORKFLOW] The workflow has been initiated - collectRandomNumbers called");

        numbers = new ArrayList<>();

        do {

            try {
                // Launch `makeNumber` Activity
                numbers.add(randomActivityStub.makeNumber());
                System.out.printf("[WORKFLOW] numbers: %s\n", numbers);
                Workflow.sleep(10000);

            } catch (Exception e) {
                // If the activity throws an exception, report but continue
                System.out.printf("[WORKFLOW] create number failed: %s\n", e.getMessage());
                System.out.flush();

            }
        } while (longRunning);

        // As written, this loop will run indefinitely and we'll never finish the
        // workflow,
        // though perhaps we'll refactor to allow a signal to stop it
        System.out.printf("[WORKFLOW] Finishing workflow - numbers: %s\n", numbers);
        return;
    }

    @Override
    public ArrayList<Integer> getListOfRandomNumbers() {

        return numbers;

    }
}
