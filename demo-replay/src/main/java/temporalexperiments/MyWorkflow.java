package temporalexperiments;

import java.util.ArrayList;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.QueryMethod;

@WorkflowInterface
public interface MyWorkflow {

    @WorkflowMethod
    void collectRandomNumbers(boolean longRunning);

    @QueryMethod
    ArrayList<Integer> getListOfRandomNumbers();

}