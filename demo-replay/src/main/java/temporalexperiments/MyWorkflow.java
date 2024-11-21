package temporalexperiments;

import java.util.ArrayList;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface MyWorkflow {

    @WorkflowMethod
    void collectRandomNumbers(boolean longRunning);
}