package lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Graph {
    private Map<String, Node> nodes;
    // 相当于邻接表
    private Map<Node, Map<Node, Integer>> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
    }

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
            System.out.println("The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + words);
            return words;
        }
    }

    // word1 -> word3 -> word2, return 1 word3
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
        }
        else {
            String words = "";
            for (int i = 0; i<word3List.size(); ++i){
                words = words + word3List.get(i) + " ";
            }
            return words;
        }
    }

    public String generateNewText(String inputText){
        // 先转化成小写
        String inputTextLowCase = inputText.toLowerCase();
        System.out.println(inputTextLowCase);
        return null;
    }

    public String calcShortestPath(String word1, String word2){
        return null;
    }

    public String randomWalk(){
        return null;
    }
}