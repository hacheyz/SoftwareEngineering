package lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<String, Node> nodes;
    // 相当于邻接表
    private Map<Node, Map<Node, Integer>> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
    }

    // 功能需求1：通过读入的文本生成有向图
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

            // 获取与sourceNode相关联的边集（Map<Node, Integer>），如果没有则创建新的
            Map<Node, Integer> sourceNodeEdges = edges.get(sourceNode);
            if (sourceNodeEdges == null) {
                sourceNodeEdges = new HashMap<>();
                edges.put(sourceNode, sourceNodeEdges);
            }

            int weight = sourceNodeEdges.getOrDefault(targetNode, 0);
            sourceNodeEdges.put(targetNode, weight + 1);
        }
    }

    public void addNode(String name) {
        nodes.put(name, new Node(name));
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public void displayEdges() {
        for (Node source : edges.keySet()) {
            Map<Node, Integer> sourceNodeEdges = edges.get(source);
            for (Node target : sourceNodeEdges.keySet()) {
                int weight = sourceNodeEdges.get(target);
                System.out.println(source.getName() + " -> " + target.getName() + " (weight: " + weight + ")");
            }
        }
    }

    // word1 -> word3 -> word2, return word3List
    // 功能需求3：返回所有的桥接词，如果有多个通过空格分割，再拼接
    public String queryBridgeWords(String word1, String word2){
        Node node1 = getNode(word1);
        Node node2 = getNode(word2);
        ArrayList<String> word3List = new ArrayList<>();
        if(node1 == null || node2 == null){
            System.out.println("No \"" + word1 + "\" or \"" + word2 + "\" in the graph!");
            return null;
        }
        Map<Node, Integer> node1Edges = edges.get(node1);
        for (Node node3 : node1Edges.keySet()){
            Map<Node, Integer> node3Edges = edges.get(node3);
            if(node3Edges.containsKey(node2)){
                word3List.add(node3.getName());
            }
        }

        if(word3List.size() == 0){
            System.out.println("No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!");
            return null;
        }
        else {
            String words = "";
            for (int i = 0; i<word3List.size(); ++i){
                words = words + word3List.get(i) + " ";
            }
            return words;
        }
    }

    // word1 -> word3 -> word2, return 1 word3 only
    // 如果有多个桥接词，只返回第一个，功能需求4中需要调用
    public String queryBridgeWord(String word1, String word2){
        Node node1 = getNode(word1);
        Node node2 = getNode(word2);
        ArrayList<String> word3List = new ArrayList<>();
        if(node1 == null || node2 == null){
            return null;
        }
        Map<Node, Integer> node1Edges = edges.get(node1);
        for (Node node3 : node1Edges.keySet()){
            Map<Node, Integer> node3Edges = edges.get(node3);
            if(node3Edges.containsKey(node2)){
                word3List.add(node3.getName());
            }
        }

        if(word3List.size() == 0){
            return null;
        } else {
            return word3List.get(0);
        }
    }

    // 功能需求4：根据桥接词生成新文本
    public String generateNewText(String inputText){
        String[] inputWords = inputText.split("\\s+");
        String newSentence = "";
        for (int i = 0; i<inputWords.length - 1; ++i){
            newSentence += inputWords[i] + " ";
            String bridgeWord = queryBridgeWord(inputWords[i].toLowerCase(), inputWords[i+1].toLowerCase());
            if(bridgeWord != null){
                newSentence += (bridgeWord + " ");
            }
        }
        newSentence += inputWords[inputWords.length-1];
        return newSentence;
    }

    // 功能需求5：计算两个单词之间的最短路径
    public String calcShortestPath(String word1, String word2) {
        /* Use Dijkstra to find the shortest path */
        StringBuilder path = new StringBuilder();
        Node node1 = getNode(word1);
        Node node2 = getNode(word2);
        if (node1 == null || node2 == null) {
            System.err.println("No \"" + word1 + "\" or \"" + word2 + "\" in the graph!");
            return null;
        }
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        ArrayList<Node> visited = new ArrayList<>();
        for (Node node : nodes.values()) {
            distance.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }
        distance.put(node1, 0);
        while (visited.size() < nodes.size()) {
            // find the node with the minimum distance
            Node minNode = null;
            int minDistance = Integer.MAX_VALUE;
            for (Node node : nodes.values()) {
                if (!visited.contains(node) && distance.get(node) < minDistance) {
                    minNode = node;
                    minDistance = distance.get(node);
                }
            }
            if (minNode == null) {
                System.err.println("No path from \"" + word1 + "\" to \"" + word2 + "\"!");
                return null;
            }
            visited.add(minNode);
            // update the distance of the neighbors of the minNode
            Map<Node, Integer> minNodeEdges = edges.get(minNode);
            for (Node neighbor : minNodeEdges.keySet()) {
                int weight = minNodeEdges.get(neighbor);
                if (distance.get(minNode) + weight < distance.get(neighbor)) {
                    distance.put(neighbor, distance.get(minNode) + weight);
                    previous.put(neighbor, minNode);
                }
            }
        }
        if (distance.get(node2) == Integer.MAX_VALUE) {
            System.err.println("No path from \"" + word1 + "\" to \"" + word2 + "\"!");
            return null;
        }
        Node current = node2;
        path.append(current.getName());
        while (current != node1) {
            current = previous.get(current);
            // head insertion method
            path.insert(0, current.getName() + " -> ");
        }
        return path.toString();
    }

    public String randomWalk(){
        return null;
    }
}