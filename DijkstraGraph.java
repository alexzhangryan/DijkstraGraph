// === CS400 File Header Information ===
// Name: Alex Ryan
// Email: apryan3
// Group and Team: n/a
// Group TA: n/a
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes. This class makes use of Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in its node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor field (this field is
   * null within the SearchNode containing the starting node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   */
  public DijkstraGraph() {
    super(new HashtableMap<>());
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references represent all of the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
      throw new NoSuchElementException("start/end node not present");
    }
    ArrayList<Node> visited = new ArrayList<>();
    PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
    pq.add(new SearchNode(nodes.get(start), 0, null));
    while (!pq.isEmpty()) {
      SearchNode search = pq.remove();
      if (!visited.contains(search.node)) {
        if (search.node.data.equals(end)) {
          return search;
        }
        visited.add(search.node);
        int edges = search.node.edgesLeaving.size();
        for (int i = 0; i < edges; i++) {
          pq.add(new SearchNode(search.node.edgesLeaving.get(i).successor,
              search.cost + search.node.edgesLeaving.get(i).data.doubleValue(),
              new SearchNode(search.node, search.cost, search.predecessor)));
        }
      }
    }
    throw new NoSuchElementException("path not found");



  }

  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // implement in step 5.4
    ArrayList<NodeType> list = new ArrayList<>();
    SearchNode search = computeShortestPath(start, end);
    while (search.predecessor != null) {
      list.add(0, search.node.data);
      search = search.predecessor;
    }
    list.add(0, search.node.data);
    return list;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path freom the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // implement in step 5.4
    return computeShortestPath(start, end).cost;
  }

  // TODO: implement 3+ tests in step 4.1
  @Test
  public void test1() {
    DijkstraGraph graph = new DijkstraGraph();
    graph.insertNode('A');
    graph.insertNode('B');
    graph.insertNode('C');
    graph.insertNode('D');
    graph.insertNode('E');
    graph.insertNode('F');
    graph.insertNode('G');
    graph.insertNode('H');
    graph.insertEdge('A', 'B', 4);
    graph.insertEdge('A', 'C', 2);
    graph.insertEdge('A', 'E', 15);
    graph.insertEdge('B', 'D', 1);
    graph.insertEdge('B', 'E', 10);
    graph.insertEdge('C', 'D', 5);
    graph.insertEdge('D', 'E', 3);
    graph.insertEdge('D', 'F', 0);
    graph.insertEdge('F', 'D', 2);
    graph.insertEdge('F', 'H', 4);
    graph.insertEdge('G', 'H', 4);
    Assertions.assertTrue(graph.computeShortestPath('A', 'E').cost == 8);
    ArrayList<NodeType> list = (ArrayList<NodeType>) graph.shortestPathData('A', 'E');
    Assertions.assertTrue(list.get(0).equals('A'));
    Assertions.assertTrue(list.get(1).equals('B'));
    Assertions.assertTrue(list.get(2).equals('D'));
    Assertions.assertTrue(list.get(3).equals('E'));
  }

  @Test
  public void test2() {
    DijkstraGraph graph = new DijkstraGraph();
    graph.insertNode('A');
    graph.insertNode('B');
    graph.insertNode('C');
    graph.insertNode('D');
    graph.insertNode('E');
    graph.insertNode('F');
    graph.insertNode('G');
    graph.insertNode('H');
    graph.insertEdge('A', 'B', 4);
    graph.insertEdge('A', 'C', 2);
    graph.insertEdge('A', 'E', 15);
    graph.insertEdge('B', 'D', 1);
    graph.insertEdge('B', 'E', 10);
    graph.insertEdge('C', 'D', 5);
    graph.insertEdge('D', 'E', 3);
    graph.insertEdge('D', 'F', 0);
    graph.insertEdge('F', 'D', 2);
    graph.insertEdge('F', 'H', 4);
    graph.insertEdge('G', 'H', 4);
    Assertions.assertTrue(graph.computeShortestPath('B', 'F').cost == 1);
    ArrayList<NodeType> list = (ArrayList<NodeType>) graph.shortestPathData('B', 'F');
    Assertions.assertTrue(list.get(0).equals('B'));
    Assertions.assertTrue(list.get(1).equals('D'));
    Assertions.assertTrue(list.get(2).equals('F'));
  }

  @Test
  public void test3() {
    DijkstraGraph graph = new DijkstraGraph();
    graph.insertNode('A');
    graph.insertNode('B');
    graph.insertNode('C');
    graph.insertNode('D');
    graph.insertNode('E');
    graph.insertNode('F');
    graph.insertNode('G');
    graph.insertNode('H');
    graph.insertEdge('A', 'B', 4);
    graph.insertEdge('A', 'C', 2);
    graph.insertEdge('A', 'E', 15);
    graph.insertEdge('B', 'D', 1);
    graph.insertEdge('B', 'E', 10);
    graph.insertEdge('C', 'D', 5);
    graph.insertEdge('D', 'E', 3);
    graph.insertEdge('D', 'F', 0);
    graph.insertEdge('F', 'D', 2);
    graph.insertEdge('F', 'H', 4);
    graph.insertEdge('G', 'H', 4);
    try {
      graph.computeShortestPath('Z', 'B');
    } catch (NoSuchElementException e) {
      Assertions.assertTrue(true);
    }
    try {
      graph.computeShortestPath('A', 'Z');
    } catch (NoSuchElementException e) {
      Assertions.assertTrue(true);
    }
    try {
      graph.computeShortestPath('A', 'G');
    } catch (NoSuchElementException e) {
      Assertions.assertTrue(true);
    }
  }
}
