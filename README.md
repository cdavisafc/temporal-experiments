# Cornelia's Temporal Sandbox

This is just a sandbox in which I can explore various things in a minimalistic env. Nothing really to see here but saving history so that I personally may go back and recall earlier experiments. Presumably, the things I've learned here in the sandbox made their way into more durable (pun intended! ;-) projects.

In this particular version of the project I was trying to create a fat jar with my worker. I've tried various things

- using the shade plugin did not work. When running the resulting jar it crashed on the worker factory start (in the worker)
  - `java -cp target/temporalexperiments-1.0.0.jar temporalexperiments.MyWorker`
  - to create the fat jar - used this pom with the command `mvn clean package shade:shade`
