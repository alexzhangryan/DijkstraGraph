import java.util.List;

public class Frontend implements FrontendInterface {
  private BackendInterface backend;

  public Frontend(BackendInterface backend) {
    this.backend = backend;
  }

  @Override
  public String generateShortestPathPromptHTML() {
    return "<div>" + "<label for='start'>Start Location:</label>"
        + "<input type='text' id='start' name='start'><br>"
        + "<label for='end'>Destination:</label>" + "<input type='text' id='end' name='end'><br>"
        + "<button onclick='requestShortestPath()'>Find Shortest Path</button>" + "</div>";
  }

  @Override
  public String generateShortestPathResponseHTML(String start, String end) {
    List<String> locations = backend.findLocationsOnShortestPath(start, end);
    List<Double> times = backend.findTimesOnShortestPath(start, end);

    double totalTravelTime = 0;
    for (Double t : times) {
      totalTravelTime += t;
    }
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

  @Override
  public String generateFurthestDestinationFromPromptHTML() {
    return "<div>" + "<label for='from'>Start Location:</label>"
        + "<input type='text' id='from' name='from'><br>"
        + "<button onclick='requestFurthestDestination()'>Furthest Destination From</button>"
        + "</div>";
  }

  @Override
  public String generateFurthestDestinationFromResponseHTML(String start) {
    String furthestDestination = backend.getFurthestDestinationFrom(start);
    List<String> pathLocations = backend.findLocationsOnShortestPath(start, furthestDestination);

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
