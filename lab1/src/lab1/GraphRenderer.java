package lab1;

import java.io.IOException;

/**
 * GraphRenderer class responsible for rendering a graph as an image.
 * <p>
 * This class converts a {@link Graph} object to DOT format
 * and then uses the Graphviz tool to generate a PNG image of the graph.
 * </p>
 */
public class GraphRenderer {
  private final Graph graph;

  /**
   * Constructs a GraphRenderer for the specified graph.
   *
   * @param graph the graph to be rendered
   */
  public GraphRenderer(Graph graph) {
    this.graph = graph;
  }

  /**
   * Converts the graph to DOT format and writes it to a file.
   * <p>
   * The DOT format is a plain text graph description language used by Graphviz.
   * </p>
   */
  private void convertGraphToDot() {
    String dotContent = graph.toString();
    MyUtils.writeWalkToFile(dotContent, "./output/graph.dot");
  }

  /**
   * Renders the graph to a PNG image.
   * <p>
   * This method first converts the graph to DOT format
   * and then uses the Graphviz `dot` command to generate a PNG image.
   * </p>
   */
  public void renderGraph() {
    convertGraphToDot();
    try {
      Runtime.getRuntime().exec("dot -Tpng ./output/graph.dot -o ./output/graph.png");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
