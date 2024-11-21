# Cornelia's Temporal Sandbox - ephemeral storage example

This sample is crafted to demonstrate replay.

The app is very simple - a workflow that loops indefinitely making calls to an activity that generates and returns a random number. That is, it is intentionally non-deterministic, as most activities are. The workflow appends the latest random number to a list, prints out the list and loops again. The activity also prints out the number when it is generated. Strung together, the print statements allow us to see very clearly when previously computed values are used - skipping an activity call - and when new acitivity calls are made.

To run the sample:

- Build the worker (fat) jar with: `mvn clean package`
- Run the worker with: `java -jar target/temporalexperiments-1.0.0.jar`
- Kick off the workflow with: `mvn compile exec:java -Dexec.mainClass="temporalexperiments.WorkflowStarter" -Dorg.slf4j.simpleLogger.defaultLogLevel=warn`

Watch the logs being printed in the worker window.

- Kill the worker (i.e. ctrl-C) and then restart it.

Look at the messages - you can clearly see where the replay leveraged values stored in the event log, and when it picks up further execution, invoking the activity again.