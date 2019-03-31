# TESLA Time Synchronization Server
A TCP-based loose time synchronization server for the TESLA protocol, implemented in Java.

To compile and run the project, read the documentation below:
## Requirements
* Apache Maven 3
* Java 8

## Compile
`mvn clean package`

## Run the application
To run the server, you must provide a port number and a location of a private key file as command line arguments. You can run the application simply with the following command:

`java -jar target/tesla-time-synchronization-server-1.0-SNAPSHOT-jar-with-dependencies.jar 9999 private.key`