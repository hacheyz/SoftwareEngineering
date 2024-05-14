package lab1;

import java.io.IOException;

public class GraphRenderer {
    private final Graph graph;

    public GraphRenderer(Graph graph) {
        this.graph = graph;
    }

    private void convertGraphToDot() {
        String dotContent = graph.toString();
        MyUtils.writeWalkToFile(dotContent, "./output/graph.dot");
    }

    public void renderGraph() {
        convertGraphToDot();
        try {
            Runtime.getRuntime().exec("dot -Tpng ./output/graph.dot -o ./output/graph.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
