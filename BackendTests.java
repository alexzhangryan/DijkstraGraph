//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P209 Role Code
// Course:   CS 400 Spring 2025
//
// Author:   Anjali Marella
// Email:    marella2@wisc.edu
// Lecturer: Florian Heimerl
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;


public class BackendTests {

  /**
   * BackendTest1: tests getListOfAllLocations() and loadGraphData() methods
   */
  @Test
  public void BackendTest1() {
    // set-up
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);


    // test getListOfAllLocations() method
    List<String> nodes = backend.getListOfAllLocations();
    if (nodes.size() != 3) {
      Assertions.fail("getListOfAllLocations() returns list with incorrect number of nodes");
    } else if (!nodes.contains("Union South") || !nodes.contains("Computer Sciences and Statistics")
        || !nodes.contains("Weeks Hall for Geological Sciences")) {
      Assertions.fail("getListOfAllLocations() returns list with incorrect nodes");
    }


    // test loadGraphData() method
    // loadGraphData() given invalid filename -> should throw IOException
    try {
      backend.loadGraphData("");
      Assertions.fail("loadGraphData() did not throw error when given invalid filename");
    } catch (IOException e) {
      // ignore, correct outcome
    } catch (Exception e) {
      Assertions.fail(e + "\nloadGraphData() threw incorrect exception type when given invalid filename");
    }

    // loadGraphData() given valid filename
    try {
      backend.loadGraphData("campus.dot");

      nodes = graph.getAllNodes();
      if (nodes.size() != 4) {
        Assertions.fail("Incorrect number of nodes in graph after calling loadGraphData()");
      } else if (!nodes.contains("Union South")
          || !nodes.contains("Computer Sciences and Statistics")
          || !nodes.contains("Weeks Hall for Geological Sciences")
          || !nodes.contains("Memorial Union")) {
        Assertions.fail("Incorrect nodes in graph after calling loadGraphData()");
      } else if (graph.getEdgeCount() != 3) {
        Assertions.fail(graph.getEdgeCount() + ": Incorrect number of edges in graph after calling loadGraphData()");
      } else if (!graph.containsEdge("Union South", "Computer Sciences and Statistics")
          || !graph.containsEdge("Computer Sciences and Statistics",
              "Weeks Hall for Geological Sciences")
          || !graph.containsEdge("Weeks Hall for Geological Sciences", "Memorial Union")) {
        Assertions.fail("Incorrect edges in graph after calling loadGraphData()");
      }
    } catch (Exception e) {
      Assertions.fail("loadGraphData() threw exception when given valid filename");
    }
  }


  /**
   * BackendTest2: tests findLocationsOnShortestPath() and findTimesOnShortestPath() methods
   */
  @Test
  public void BackendTest2() {
    // set-up
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);


    // test findLocationsOnShortestPath() method
    List<String> nodes =
        backend.findLocationsOnShortestPath("Union South", "Computer Sciences and Statistics");
     if (nodes.size() != 2) {
      Assertions.fail("findLocationsOnShortestPath() returned list of incorrect size");
    } else if (!nodes.contains("Union South")
        || !nodes.contains("Computer Sciences and Statistics")) {
      Assertions.fail("findLocationsOnShortestPath() returned list with incorrect locations");
    }


    // test findTimesOnShortestPath() method
    List<Double> times =
        backend.findTimesOnShortestPath("Union South", "Computer Sciences and Statistics");
    if (times.size() != 1) {
      Assertions.fail("findTimesOnShortestPath() returned list of incorrect size");
    } else if (!times.contains(1.0)) {
      Assertions.fail(times + ": findTimesOnShortestPath() returned list with incorrect locations");
    }
  }


  /**
   * BackendTest3: tests getFurthestDestinationFrom() method
   */
  @Test
  public void BackendTest3() {
    // set-up
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);


    // getFurthestDestinationFrom() given null start node -> should throw NoSuchElementException
    try {
      String farthest = backend.getFurthestDestinationFrom(null);
      Assertions.fail("getFurthestDestinationFrom() did not throw error when given invalid node");
    } catch (NoSuchElementException e) {
      // ignore, correct outcome
    } catch (Exception e) {
      Assertions.fail("getFurthestDestinationFrom() with invalid node throws incorrect exception");
    }

    // getFurthestDestinationFrom() w/ unconnected start node -> should throw NoSuchElementException
    try {
      String farthest = backend.getFurthestDestinationFrom("Weeks Hall for Geological Sciences");
      Assertions.fail("getFurthestDestinationFrom() with unconnected node does not throw error");
    } catch (NoSuchElementException e) {
      // ignore, correct outcome
    } catch (Exception e) {
      Assertions.fail("getFurthestDestinationFrom() with unconnected node throws wrong exception");
    }

    // getFurthestDestinationFrom() given valid start node
    try {
      String farthest = backend.getFurthestDestinationFrom("Union South");
      if (!farthest.equals("Weeks Hall for Geological Sciences")) {
        Assertions.fail("getFurthestDestinationFrom() did not return correct node");
      }
    } catch (Exception e) {
      Assertions.fail(e + "getFurthestDestinationFrom() throws exception when given valid node");
    }
  }
}
