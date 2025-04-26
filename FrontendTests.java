import java.util.List;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FrontendTests {
  /**
   * tests prompts for correct values
   */
  @Test
  public void roleTest1() {
    Frontend frontend = new Frontend(new Backend_Placeholder(new Graph_Placeholder()));
    String shortPrompt = frontend.generateShortestPathPromptHTML();
    String farPrompt = frontend.generateFurthestDestinationFromPromptHTML();
    // tests to see if it returns the correct string
    Assertions.assertTrue(shortPrompt.contains("<div>"
        + "<label for='start'>Start Location:</label>"
        + "<input type='text' id='start' name='start'><br>"
        + "<label for='end'>Destination:</label>" + "<input type='text' id='end' name='end'><br>"
        + "<button onclick='requestShortestPath()'>Find Shortest Path</button>" + "</div>"));
    Assertions.assertTrue(farPrompt.contains("<div>" + "<label for='from'>Start Location:</label>"
        + "<input type='text' id='from' name='from'><br>"
        + "<button onclick='requestFurthestDestination()'>Furthest Destination From</button>"
        + "</div>"));
  }

  /**
   * tests to see if shortest path response is correct
   */
  @Test
  public void roleTest2() {
    Frontend frontend = new Frontend(new Backend_Placeholder(new Graph_Placeholder()));
    String shortResponse = frontend.generateShortestPathResponseHTML("Union South",
        "Weeks Hall for Geological Sciences");
    // checks to see if path is constructed and minutes are present
    Assertions.assertTrue(shortResponse.contains("Union South"));
    Assertions.assertTrue(shortResponse.contains("Weeks Hall for Geological Sciences"));
    Assertions.assertTrue(shortResponse.contains("6.0 minutes"));
    Assertions.assertTrue(shortResponse.contains("Computer Sciences and Statistics"));
  }

  /**
   * tests generate furthest destination and also loading graph data
   */
  @Test
  public void roleTest3() {
    // generates new backend and loads data
    Backend_Placeholder backend = new Backend_Placeholder(new Graph_Placeholder());
    try {
      backend.loadGraphData("");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      Assertions.fail();
    }
    Frontend frontend = new Frontend(backend);
    // calls generate furthest destination response
    String farResponse = frontend.generateFurthestDestinationFromResponseHTML("Union South");
    // checks to see if path is correctly constructed
    Assertions.assertTrue(farResponse.contains("Union South"));
    Assertions.assertTrue(farResponse.contains("Weeks Hall for Geological Sciences"));
    Assertions.assertTrue(farResponse.contains("Mosse Humanities Building"));
    Assertions.assertTrue(farResponse.contains("Computer Sciences and Statistics"));
  }

  /**
   * tests shortest path response with given graph
   */
  @Test
  public void IntegrationTest1() {
    DijkstraGraph graph = new DijkstraGraph();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("C");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertEdge("A", "B", 1.0);
    graph.insertEdge("A", "C", 2.0);
    graph.insertEdge("A", "E", 5.0);
    graph.insertEdge("B", "D", 1.0);
    graph.insertEdge("B", "E", 7.0);
    graph.insertEdge("C", "D", 3.0);
    graph.insertEdge("D", "E", 1.0);

    Backend backend = new Backend(graph);
    Frontend frontend = new Frontend(backend);

    String response = frontend.generateShortestPathResponseHTML("A", "E");

    Assertions.assertTrue(response.contains("A"));
    Assertions.assertTrue(response.contains("E"));
    Assertions.assertTrue(response.contains("B"));
    Assertions.assertTrue(response.contains("D"));
    Assertions.assertTrue(response.contains("3"));


  }

  // tests furthest response with given graph
  @Test
  public void IntegrationTest2() {
    DijkstraGraph graph = new DijkstraGraph();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("C");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertEdge("A", "B", 1.0);
    graph.insertEdge("A", "C", 2.0);
    graph.insertEdge("A", "E", 5.0);
    graph.insertEdge("B", "D", 1.0);
    graph.insertEdge("B", "E", 7.0);
    graph.insertEdge("C", "D", 3.0);
    graph.insertEdge("D", "E", 1.0);

    Backend backend = new Backend(graph);
    Frontend frontend = new Frontend(backend);

    String response = frontend.generateFurthestDestinationFromResponseHTML("B");

    Assertions.assertTrue(response.contains("E"));
    Assertions.assertTrue(response.contains("B"));
    Assertions.assertTrue(response.contains("D"));

  }

  /**
   * tests get all locations
   */
  @Test
  public void IntegrationTest3() {
    DijkstraGraph graph = new DijkstraGraph();
    graph.insertNode("Top Lane");
    graph.insertNode("Baron Pit");
    graph.insertNode("Mid Lane");
    graph.insertNode("Bot lane");
    graph.insertNode("Dragon Pit");

    Backend backend = new Backend(graph);
    Frontend frontend = new Frontend(backend);

    List<String> response = backend.getListOfAllLocations();

    Assertions.assertTrue(response.contains("Top Lane"));
    Assertions.assertTrue(response.contains("Baron Pit"));
    Assertions.assertTrue(response.contains("Mid Lane"));
    Assertions.assertTrue(response.contains("Bot lane"));
    Assertions.assertTrue(response.contains("Dragon Pit"));
  }

  /**
   * tests null responses
   */
  @Test
  public void IntegrationTest4() {
    DijkstraGraph graph = new DijkstraGraph();
    graph.insertNode("Top Lane");
    graph.insertNode("Baron Pit");
    graph.insertNode("Mid Lane");
    graph.insertNode("Bot lane");
    graph.insertNode("Dragon Pit");

    Backend backend = new Backend(graph);
    Frontend frontend = new Frontend(backend);

    try {
      frontend.generateFurthestDestinationFromResponseHTML("jungle");
    } catch (Exception e) {
      Assertions.assertTrue(true);
    }
    try {
      frontend.generateShortestPathResponseHTML("jungle", "Baron Pit");
    } catch (Exception e) {
      Assertions.assertTrue(true);
    }
  }

}
