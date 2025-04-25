import java.util.List;

public class Frontend implements FrontendInterface {
  private BackendInterface backend;

  /**
   * Implementing classes should support the constructor below.
   * 
   * @param backend is used for shortest path computations
   */
  public Frontend(BackendInterface backend) {
    this.backend = backend;
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a larger html page. This HTML
   * output should include: - a text input field with the id="start", for the start location - a
   * text input field with the id="end", for the destination - a button labelled "Find Shortest
   * Path" to request this computation Ensure that these text fields are clearly labelled, so that
   * the user can understand how to use them.
   * 
   * @return an HTML string that contains input controls that the user can make use of to request a
   *         shortest path computation
   */
  @Override
  public String generateShortestPathPromptHTML() {
    return "<div>" + "<label for='start'>Start Location:</label>"
        + "<input type='text' id='start' name='start'><br>"
        + "<label for='end'>Destination:</label>" + "<input type='text' id='end' name='end'><br>"
        + "<button onclick='requestShortestPath()'>Find Shortest Path</button>" + "</div>";
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a larger html page. This HTML
   * output should include: - a paragraph (p) that describes the path's start and end locations - an
   * ordered list (ol) of locations along that shortest path - a paragraph (p) that includes the
   * total travel time along this path Or if there is no such path, the HTML returned should instead
   * indicate the kind of problem encountered.
   * 
   * @param start is the starting location to find a shortest path from
   * @param end   is the destination that this shortest path should end at
   * @return an HTML string that describes the shortest path between these two locations
   */
  @Override
  public String generateShortestPathResponseHTML(String start, String end) {
    if(start == null || end == null){
	return "";
	}
	// gets locations and times
    List<String> locations = backend.findLocationsOnShortestPath(start, end);
    List<Double> times = backend.findTimesOnShortestPath(start, end);
    // calculates travel time
    double totalTravelTime = 0;
    for (Double t : times) {
      totalTravelTime += t;
    }
    // converts locations to a string
    StringBuilder locationsListBuilder = new StringBuilder("<ol>");
    for (String location : locations) {
      locationsListBuilder.append("<li>").append(location).append("</li>");
    }
    locationsListBuilder.append("</ol>");
    if (totalTravelTime == 0 || locations == null || times == null) {
      return "<div>" + "<p><strong>Error</strong></p>";
    }

    return "<div>" + "<h2>Shortest Path Results</h2>" + "<p><strong>Start:</strong> " + start
        + "</p>" + "<p><strong>End:</strong> " + end + "</p>" + "<p><strong>Description:</strong> "
        + locationsListBuilder + "</p>" + "<p><strong>Total Travel Time:</strong> "
        + totalTravelTime + " minutes</p>" + "</div>";
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a larger html page. This HTML
   * output should include: - a text input field with the id="from", for the start location - a
   * button labelled "Furthest Destination From" to submit this request Ensure that this text field
   * is clearly labelled, so that the user can understand how to use it.
   * 
   * @return an HTML string that contains input controls that the user can make use of to request a
   *         furthest destination calculation
   */
  @Override
  public String generateFurthestDestinationFromPromptHTML() {

    return "<div>" + "<label for='from'>Start Location:</label>"
        + "<input type='text' id='from' name='from'><br>"
        + "<button onclick='requestFurthestDestination()'>Furthest Destination From</button>"
        + "</div>";
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a larger html page. This HTML
   * output should include: - a paragraph (p) that describes the starting point being searched from
   * - a paragraph (p) that describes the furthest destination found - an ordered list (ol) of
   * locations on the path between these locations Or if there is no such destination, the HTML
   * returned should instead indicate the kind of problem encountered.
   * 
   * @param start is the starting location to find the furthest dest from
   * @return an HTML string that describes the furthest destination from the specified start
   *         location
   */
  @Override
  public String generateFurthestDestinationFromResponseHTML(String start) {
    // gets locations
    String furthestDestination = backend.getFurthestDestinationFrom(start);
    List<String> pathLocations = backend.findLocationsOnShortestPath(start, furthestDestination);
    if (furthestDestination == null) {
      return "<div>" + "<h2>Furthest Destination from " + start + "</h2>"
          + "<p><strong>Start:</strong> " + start + "</p>"
          + "<p><strong>Furthest Destination:</strong> " + "null" + "</p>"
          + "<p><strong>Path Description:</strong> " + "null" + "</p>" + "</div>";
    }
    // converts locations into string
    StringBuilder locationsListBuilder = new StringBuilder("<ol>");
    for (String location : pathLocations) {
      locationsListBuilder.append("<li>").append(location).append("</li>");
    }
    locationsListBuilder.append("</ol>");

    return "<div>" + "<h2>Furthest Destination from " + start + "</h2>"
        + "<p><strong>Start:</strong> " + start + "</p>"
        + "<p><strong>Furthest Destination:</strong> " + furthestDestination + "</p>"
        + "<p><strong>Path Description:</strong> " + locationsListBuilder + "</p>" + "</div>";
  }
}
