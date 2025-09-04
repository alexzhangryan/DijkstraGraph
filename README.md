# Dijkstra Campus Navigator
A web app that runs Dijkstra's shortest-path on a sample campus map dataset that also findds the furthest reachable location from a starting point.
The sample data is a Graphviz (.dot) file provided by the UW-Madison computer science department

## Features
* Java HTTP server
* Shortest path between two places
* Furthest path between two places
* Simple HTML frontend
* LOTS of unit tests (JUnit 5)
* Sample data visulaizer in campus.dot.svg

## Requirements
* JDK 17+
* JUnit5
* Thats it!

## How to Run
1. Download JUnit in the parent directory
2. Compile classes
```bash
javac -cp .:../junit5.jar *.java
```
3. Start server
```
java -cp .:../junit5.jar WebApp
```
4. Go to [http://localhost:8080/](http://localhost:8080/)
5. Type in any locations from the test data

## How to Run Tests
1. Download JUnit in the parent directory
2. Compile classes
```bash
javac -cp .:../junit5.jar *.java
```
3. Run Tests:
```
java -cp .:../junit5.jar org.junit.platform.console.ConsoleLauncher --select-class=FrontendTests
```
