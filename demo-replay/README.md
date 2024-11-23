# Cornelia's Temporal Sandbox - ephemeral storage example

This sample is crafted to demonstrate replay.

The app is very simple - a workflow that loops indefinitely making calls to an activity that generates and returns a random number. That is, it is intentionally non-deterministic, as most activities are. The workflow appends the latest random number to a list, prints out the list and loops again. The activity also prints out the number when it is generated. Strung together, the print statements allow us to see very clearly when previously computed values are used - skipping an activity call - and when new acitivity calls are made.

# To run the sample locally:

- Build the worker (fat) jar with: `mvn clean package`
- Run the worker with: `java -jar target/temporalexperiments-1.0.0.jar`
- Kick off the workflow with: `mvn compile exec:java -Dexec.mainClass="temporalexperiments.WorkflowStarter" -Dorg.slf4j.simpleLogger.defaultLogLevel=warn`

Watch the logs being printed in the worker window.

- Kill the worker (i.e. ctrl-C) and then restart it.

Look at the messages - you can clearly see where the replay leveraged values stored in the event log, and when it picks up further execution, invoking the activity again.

# To run the sample in a docker container connecting to Temporal server running on the host


- Build the worker (fat) jar with: `mvn clean package`
- Put the fat jar in a docker image: `docker build -t demo-replay .`
- Run the worker with: `docker run demo-replay` - you can run as many of these as you like. Do so in different windows will allow you to see some interesting behaviors.
- Kick off the workflow with: `mvn compile exec:java -Dexec.mainClass="temporalexperiments.WorkflowStarter" -Dorg.slf4j.simpleLogger.defaultLogLevel=warn`

Watch the logs being printed in the worker window.

- Kill one of the workers (i.e. ctrl-C) and then restart it some time later.
