package lab1;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


/**
 * The Graph class represents a directed graph constructed from a list of words.
 * It provides methods to add nodes, display edges, query bridge words, generate new text,
 * calculate the shortest path between words, and perform a random walk.
 */
public class Graph {
  private Map<String, Node> nodes;
  private Map<Node, Map<Node, Integer>> edges;

  /**
   * Constructs an empty Graph.
   */
  public Graph() {
    this.nodes = new HashMap<>();
    this.edges = new HashMap<>();
  }

  /**
   * Constructs a Graph from an array of words.
   *
   * @param words the array of words to construct the graph from
   */
  public Graph(String[] words) {
    this();
    for (int i = 0; i < words.length - 1; i++) {
      String sourceName = words[i];
      String targetName = words[i + 1];

      if (!nodes.containsKey(sourceName)) {
        addNode(sourceName);
      }
      if (!nodes.containsKey(targetName)) {
        addNode(targetName);
      }
      Node sourceNode = getNode(sourceName);
      Node targetNode = getNode(targetName);

      Map<Node, Integer> sourceNodeEdges = edges.get(sourceNode);
      if (sourceNodeEdges == null) {
        sourceNodeEdges = new HashMap<>();
        edges.put(sourceNode, sourceNodeEdges);
      }

      int weight = sourceNodeEdges.getOrDefault(targetNode, 0);
      sourceNodeEdges.put(targetNode, weight + 1);
    }
  }

  /**
   * Adds a node with the given name to the graph.
   *
   * @param name the name of the node to add
   */
  public void addNode(String name) {
    nodes.put(name, new Node(name));
  }

  /**
   * Retrieves the node with the given name from the graph.
   *
   * @param name the name of the node to retrieve
   * @return the node with the specified name, or null if no such node exists
   */
  public Node getNode(String name) {
    return nodes.get(name);
  }

  /**
   * Displays the edges of the graph.
   * <p>
   * Each edge is printed in the format "source -> target (weight: weight)".
   * </p>
   */
  public void displayEdges() {
    for (Map.Entry<Node, Map<Node, Integer>> entry : edges.entrySet()) {
      Node source = entry.getKey();
      Map<Node, Integer> sourceNodeEdges = entry.getValue();
      for (Map.Entry<Node, Integer> edgeEntry : sourceNodeEdges.entrySet()) {
        Node target = edgeEntry.getKey();
        int weight = edgeEntry.getValue();
        System.out.println(source.getName() + " -> "
            + target.getName() + " (weight: " + weight + ")");
      }
    }
  }

  /**
   * Returns all bridge words between word1 and word2, joined by spaces.
   *
   * @param word1 the source word
   * @param word2 the target word
   * @return a space-separated string of bridge words, or null if no bridge words exist
   */
  public String queryBridgeWords(String word1, String word2) {
    Node node1 = getNode(word1);
    Node node2 = getNode(word2);
    ArrayList<String> word3List = new ArrayList<>();
    if (node1 == null || node2 == null) {
      System.err.println("No \"" + word1 + "\" or \"" + word2 + "\" in the graph!");
      return null;
    }
    Map<Node, Integer> node1Edges = edges.get(node1);
    for (Node node3 : node1Edges.keySet()) {
      Map<Node, Integer> node3Edges = edges.get(node3);
      if (node3Edges != null && node3Edges.containsKey(node2)) {
        word3List.add(node3.getName());
      }
    }

    if (word3List.isEmpty()) {
      System.err.println("No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!");
      return null;
    } else {
      return String.join(" ", word3List);
    }
  }

  /**
   * Returns the first bridge word between word1 and word2.
   * <p>
   * If there are multiple bridge words, only the first one is returned.
   * </p>
   *
   * @param word1 the source word
   * @param word2 the target word
   * @return the first bridge word, or null if no bridge word exists
   */
  public String queryBridgeWord(String word1, String word2) {
    Node node1 = getNode(word1);
    Node node2 = getNode(word2);
    if (node1 == null || node2 == null) {
      return null;
    }
    Map<Node, Integer> node1Edges = edges.get(node1);
    for (Node node3 : node1Edges.keySet()) {
      Map<Node, Integer> node3Edges = edges.get(node3);
      if (node3Edges != null && node3Edges.containsKey(node2)) {
        return node3.getName();
      }
    }
    return null;
  }

  /**
   * Generates a new text by inserting bridge words between adjacent words in the input text.
   *
   * @param inputText the input text to transform
   * @return the new text with bridge words inserted
   */
  public String generateNewText(String inputText) {
    String[] inputWords = inputText.split("\\s+");
    StringBuilder newSentence = new StringBuilder();
    for (int i = 0; i < inputWords.length - 1; ++i) {
      newSentence.append(inputWords[i]).append(" ");
      String bridgeWord =
              queryBridgeWord(inputWords[i].toLowerCase(), inputWords[i + 1].toLowerCase());
      if (bridgeWord != null) {
        newSentence.append(bridgeWord).append(" ");
      }
    }
    newSentence.append(inputWords[inputWords.length - 1]);
    return newSentence.toString();
  }

  /**
   * Calculates the shortest path between two words using Dijkstra's algorithm.
   *
   * @param word1 the start word
   * @param word2 the end word
   * @return a string representing the shortest path, or null if no path exists
   */
  public String calcShortestPath(String word1, String word2) {
    StringBuilder path = new StringBuilder();
    if (Objects.equals(word1, "") && Objects.equals(word2, "")) {
      System.err.println("Please input at least one word!");
      return null;
    }
    if (Objects.equals(word1, "")) {
      String temp = word1;
      word1 = word2;
      word2 = temp;
    }
    Node node1 = getNode(word1);
    Node node2 = getNode(word2);
    if (node1 == null || (!Objects.equals(word2, "") && node2 == null)) {
      System.err.println("No \"" + word1 + "\" or \"" + word2 + "\" in the graph!");
      return null;
    }
    DijkstraResult result = dijkstra(node1);
    Map<Node, Integer> distance = result.distance();
    Map<Node, Node> previous = result.previous();

    if (Objects.equals(word2, "")) {
      for (Node node : distance.keySet()) {
        if (node != node1 && distance.get(node) != Integer.MAX_VALUE) {
          StringBuilder singlePath = new StringBuilder();
          Node current = node;
          singlePath.append(current.getName());
          while (current != node1) {
            current = previous.get(current);
            singlePath.insert(0, current.getName() + " -> ");
          }
          singlePath.append(", length = ").append(distance.get(node)).append("\n");
          path.append(singlePath);
        }
      }
      if (path.isEmpty()) {
        System.err.println("No path from \"" + word1 + "\" to any other words!");
        return null;
      }
      return path.toString();
    }

    if (distance.get(node2) == Integer.MAX_VALUE) {
      System.err.println("No path from \"" + word1 + "\" to \"" + word2 + "\"!");
      return null;
    }

    Node current = node2;
    path.append(current.getName());
    while (current != node1) {
      current = previous.get(current);
      path.insert(0, current.getName() + " -> ");
    }
    path.append(", length = ").append(distance.get(node2));
    return path.toString();
  }

  /**
   * Performs a random walk starting from a random node.
   * <p>
   * The walk continues until a visited edge is encountered,
   * a node without out-edges is reached,
   * or 'q' is entered.
   * </p>
   *
   * @return a string representing the random walk
   */
  public String randomWalk() {
    int randomIndex = (int) (Math.random() * nodes.size());
    Node startNode = (Node) nodes.values().toArray()[randomIndex];
    StringBuilder walk = new StringBuilder();
    walk.append(startNode.getName());
    Node currentNode = startNode;
    Map<Node, Map<Node, Boolean>> visited = new HashMap<>();
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    while (true) {
      System.out.print(walk);
      if (scanner.nextLine().equals("q")) {
        break;
      }
      Map<Node, Integer> currentNodeEdges = edges.get(currentNode);
      if (currentNodeEdges == null || currentNodeEdges.isEmpty()) {
        break;
      }
      int randomEdgeIndex = (int) (Math.random() * currentNodeEdges.size());
      Node nextNode = (Node) currentNodeEdges.keySet().toArray()[randomEdgeIndex];

      if (visited.get(currentNode) == null) {
        visited.put(currentNode, new HashMap<>());
      }
      if (visited.get(currentNode).containsKey(nextNode)) {
        walk.append(" -> ").append(nextNode.getName());
        System.out.println(walk);
        break;
      } else {
        visited.get(currentNode).put(nextNode, true);
        walk.append(" -> ").append(nextNode.getName());
      }
      currentNode = nextNode;
    }
    return walk.toString();
  }

  /**
   * Returns a string representation of the graph in DOT format.
   *
   * @return a DOT format string representing the graph
   */
  @Override
  public String toString() {
    StringBuilder graphString = new StringBuilder();
    graphString.append("digraph G {\n");
    for (Map.Entry<Node, Map<Node, Integer>> entry : edges.entrySet()) {
      Node source = entry.getKey();
      Map<Node, Integer> sourceNodeEdges = entry.getValue();
      for (Map.Entry<Node, Integer> edgeEntry : sourceNodeEdges.entrySet()) {
        Node target = edgeEntry.getKey();
        int weight = edgeEntry.getValue();
        graphString.append("  ").append(source.getName())
            .append(" -> ").append(target.getName())
            .append(" [label=\"")
            .append(weight).append("\"];\n");
      }
    }
    graphString.append("}");
    return graphString.toString();
  }

  // Helper methods for Dijkstra's algorithm

  /**
   * Helper class to store the results of Dijkstra's algorithm.
   */
  private record DijkstraResult(Map<Node, Integer> distance, Map<Node, Node> previous) {
  }

  /**
   * Performs Dijkstra's algorithm to find the shortest paths from a start node to all other nodes.
   *
   * @param startNode the starting node
   * @return a DijkstraResult containing the distances and previous nodes for the shortest paths
   */
  private DijkstraResult dijkstra(Node startNode) {
    Map<Node, Integer> distance = new HashMap<>();
    Map<Node, Node> previous = new HashMap<>();
    ArrayList<Node> visited = new ArrayList<>();

    for (Node node : nodes.values()) {
      distance.put(node, Integer.MAX_VALUE);
      previous.put(node, null);
    }
    distance.put(startNode, 0);

    while (visited.size() < nodes.size()) {
      Node minNode = null;
      int minDistance = Integer.MAX_VALUE;
      for (Node node : nodes.values()) {
        if (!visited.contains(node) && distance.get(node) < minDistance) {
          minNode = node;
          minDistance = distance.get(node);
        }
      }
      if (minNode == null) {
        break;
      }
      visited.add(minNode);
      Map<Node, Integer> minNodeEdges = edges.get(minNode);
      if (minNodeEdges == null) {
        continue;
      }
      for (Node neighbor : minNodeEdges.keySet()) {
        int weight = minNodeEdges.get(neighbor);
        if (distance.get(minNode) + weight < distance.get(neighbor)) {
          distance.put(neighbor, distance.get(minNode) + weight);
          previous.put(neighbor, minNode);
        }
      }
    }

    return new DijkstraResult(distance, previous);
  }
}
