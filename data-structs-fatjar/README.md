# Cornelia's Temporal Sandbox

This is just a sandbox in which I can explore various things in a minimalistic env. Nothing really to see here but saving history so that I personally may go back and recall earlier experiments. Presumably, the things I've learned here in the sandbox made their way into more durable (pun intended! ;-) projects.

In this particular version of the project I was trying to create a fat jar with my worker. I've tried various things

- using the shade plugin did not work. When running the resulting jar it crashed on the worker factory start (in the worker)
  - `java -cp target/temporalexperiments-1.0.0.jar temporalexperiments.MyWorker`
  - to create the fat jar - used this pom with the command `mvn clean package shade:shade`
- using the assembly plugin yielded exactly the same results. When running the resulting jar it crashed on the worker factory start (in the worker)
  - `java -cp target/temporalexperiments-1.0.0-jar-with-dependencies.jar temporalexperiments.MyWorker`
  - to create the fat jar - used this pom with the command `mvn clean package assembly:single`
  - note that ChatGPT had given me a plugin config which had the `configuration` within the `execution` which was incorrect. I could get it to build but had to use the command `mvn clean package assembly:single@make-assembly`
- using the spring boot plugin just worked. Note that I also added to the spring boot parent to the pom. Super easy.
  - `mvn clean package`
  - `java -jar target/temporalexperiments-1.0.0.jar`

To run the sample:

- Build the worker (fat) jar with: `mvn clean package`
- Run the worker with: `java -jar target/temporalexperiments-1.0.0.jar`
- Kick off the workflow with: `mvn compile exec:java -Dexec.mainClass="temporalexperiments.WorkflowStarter" -Dorg.slf4j.simpleLogger.defaultLogLevel=warn`