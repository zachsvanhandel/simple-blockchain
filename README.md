# Simple Blockchain
A simple blockchain proof-of-concept written in Java. 

## Getting Started
### Requirements

#### Java
The project requires [Java 8](https://www.java.com/), but is compatible up to Java 11. To check your current version of Java, run the following command:
```
$ java -version
```

#### Maven
The project also requires [Maven 3.3](https://maven.apache.org/) or higher. To check your current version of Maven, run the following command:
```
$ mvn -v
```

### Cloning the repository
To clone the project, navigate to the directory where you would like it to be saved and run the following command:
```
$ git clone https://github.com/zachsvanhandel/simple-blockchain.git
```

### Building and running the project
The project uses Maven, a build automation tool, to generate an executable JAR file. To build the project, run the following commands: 
```
$ cd simple-blockchain
$ mvn clean package
```

The executable JAR file can then be run by Java. To start the application, run the following command:
```
$ java -jar target/blockchain-*.jar
```

The project will need to be rebuilt any time the source code is changed or the executable JAR file is removed.

## Command-line interface
When the executable JAR file is run, the command-line interface (CLI) should automatically load. The project uses [Spring Shell](https://projects.spring.io/spring-shell/) to provide an interactive shell with custom commands.

Below is a simple example of how to use the command line interface for the project:
<pre>
# First, create a new chain with a difficulty of 5
■ simple-blockchain <b>init</b> 5
...
# Add a new block containing the data string 'hello world'
■ simple-blockchain <b>add</b> 'hello world'
...
# Add another block containing the data string 'abc'
■ simple-blockchain <b>add</b> 'abc'
...
# Print a JSON representation of the current chain
■ simple-blockchain <b>display</b>
...
</pre>

The `help` command can also be used to display more information about available commands.

## Testing
The project uses [JUnit](https://junit.org/) to create automated unit tests that can be run with Maven. To start the tests, run the following command:
```
$ mvn clean test
```

The project also uses [JaCoCo](https://www.jacoco.org/jacoco/index.html) to create code coverage reports. These reports show which lines and branches were executed by the tests. A new coverage report is generated every time the tests are run by Maven, and can be viewed by opening the following file: *`target/site/jacoco/index.html`*.  
