package temporalexperiements;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import temporalexperiments.MyWorkflow;
import temporalexperiments.MyWorkflowImpl;
import temporalexperiments.RandomActivityImpl;
import temporalexperiments.Shared;

public class MyWorkflowTest {

    private TestWorkflowEnvironment testEnv;
    private Worker worker;
    private WorkflowClient workflowClient;

    @Before
    public void setUp() {
        testEnv = TestWorkflowEnvironment.newInstance();
        worker = testEnv.newWorker(Shared.TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(MyWorkflowImpl.class);
        worker.registerActivitiesImplementations(new RandomActivityImpl());
        workflowClient = testEnv.getWorkflowClient();
    }

    @After
    public void tearDown() {
        testEnv.close();
    }

    @Test
    public void testIt() {

        testEnv.start();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.TASK_QUEUE)
                .build();
        MyWorkflow workflow = workflowClient.newWorkflowStub(MyWorkflow.class, options);
        workflow.collectRandomNumbers(false);
        assert (true);
    }
}
