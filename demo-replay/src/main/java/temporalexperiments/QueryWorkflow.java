package temporalexperiments;

import java.util.ArrayList;
import java.util.UUID;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class QueryWorkflow {
    public static void main(String[] args) {
        // Create a stub that accesses a Temporal Service on the local development
        // machine
        WorkflowServiceStubs serviceStubs = WorkflowServiceStubs.newLocalServiceStubs();

        // The WorkflowClient communicates with the Temporal Service
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);

        // Create a WorkflowOptions instance to configure the Workflow
        // WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
        // .setTaskQueue(Shared.TASK_QUEUE)
        // .setWorkflowId("relay-demo-workflow-fb73b74f-83da-403b-8ede-c0f4a17cf17f")
        // .build();

        // Create a Workflow stub
        MyWorkflow workflow = client.newWorkflowStub(MyWorkflow.class, args[0]);
        // "relay-demo-workflow-fb73b74f-83da-403b-8ede-c0f4a17cf17f");

        // Query the state of the workflow - get the current list of numbers
        ArrayList<Integer> theNumbers = workflow.getListOfRandomNumbers();

        // Print the result
        System.out.printf("Workflow has these numbers: %s\n", theNumbers);
        System.exit(0);
    }
}
