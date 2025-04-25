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


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;


public class Backend implements BackendInterface {

  ///////////////// DATA FIELDS /////////////////
  private GraphADT<String, Double> graph;


  ///////////////// CONSTRUCTOR /////////////////
  /**
   * Constructor for classes implementing BackendInterface.
   *
   * @param graph object to store the backend's graph data
   */
  public Backend(GraphADT<String, Double> graph) {
    this.graph = graph;
  }


  ////////////// INTERFACE METHODS //////////////
  @Override
  public void loadGraphData(String filename) throws IOException {
    // if graph is nonempty, deletes contents
    if (graph.getNodeCount() != 0) {
      for (String node: graph.getAllNodes()) {
        graph.removeNode(node);
      }
    }

    File file = new File(filename);

    // throws IOException if unable to read file
    Scanner reader = new Scanner(file);
    while (reader.hasNextLine()) {
      String line = reader.nextLine().trim();

      // if line w/ data (has ->), then extract values
      int arrowInd = line.indexOf(" -> ");
      if (arrowInd != -1) {
        // source node value
        String source = line.substring(1, arrowInd - 1);
        // target node value
        String target = line.substring(arrowInd + 5, line.indexOf("\" [seconds="));
        // edge weight value (as String)
        String edgeWeight = line.substring(line.indexOf("=") + 1, line.length() - 2);

        // ensure that source node is in graph
        graph.insertNode(source);
        // ensure that target node is in graph
        graph.insertNode(target);
        // add edge between source and target node
        graph.insertEdge(source, target, Double.parseDouble(edgeWeight));
      }
    }

    reader.close();
  }

  @Override
  public List<String> getListOfAllLocations() {
    return graph.getAllNodes();
  }

  @Override
  public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
    // if path exists, return list with node values
    try {
      return graph.shortestPathData(startLocation, endLocation);

      // if no path exists, return empty list
    } catch (NoSuchElementException e) {
      return Collections.emptyList();
    }
  }

  @Override
  public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {
    // find nodes on shortest path
    List<String> nodes = graph.shortestPathData(startLocation, endLocation);

    // if no path exists, return empty list
    if (nodes.isEmpty()) {
      return Collections.emptyList();
    }

    // if path exists, create and return list of edge values
    List<Double> times = new ArrayList<>();
    for (int i = 0; i < nodes.size() - 1; i++) {
      times.add(graph.getEdge(nodes.get(i), nodes.get(i + 1)));
    }
    return times;
  }

  @Override
  public String getFurthestDestinationFrom(String startLocation) throws NoSuchElementException {
    // throws NoSuchElementException if startLocation doesn't exist
    if (!graph.containsNode(startLocation)) {
      throw new NoSuchElementException("Cannot find " + startLocation);
    }

    // find farthest node
    String farthestNode = null;
    Double longestTime = 0.0;
    for (String node : graph.getAllNodes()) {
      if (!node.equals(startLocation)) {
        try {
          Double time = graph.shortestPathCost(startLocation, node);
          if (time > longestTime) {
            farthestNode = node;
            longestTime = time;
          }
        } catch (NoSuchElementException e) {
        // ignore, move to next node
        }
      }
    }

    // if no paths exist, throw NoSuchElementException
    if (farthestNode == null) {
      throw new NoSuchElementException("Cannot find paths starting at " + startLocation);

      // otherwise, return farthest node
    } else {
      return farthestNode;
    }
  }
}
