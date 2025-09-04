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
WIP
