package temporalexperiments;

import java.util.ArrayList;
import java.util.UUID;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class WorkflowStarter {
    public static void main(String[] args) {
        // Create a stub that accesses a Temporal Service on the local development
        // machine
        WorkflowServiceStubs serviceStubs = WorkflowServiceStubs.newLocalServiceStubs();

        // The WorkflowClient communicates with the Temporal Service
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);

        // Create a WorkflowOptions instance to configure the Workflow
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.TASK_QUEUE)
                .setWorkflowId("ephemeral-storage-workflow-" + UUID.randomUUID())
                .build();

        // Create a Workflow stub
        MyWorkflow workflow = client.newWorkflowStub(MyWorkflow.class, workflowOptions);

        // Start the Workflow and wait for it to complete
        workflow.collectRandomNumbers(true);

        // Print the result
        System.out.println("Workflow has completed ");
        System.exit(0);
    }
}
