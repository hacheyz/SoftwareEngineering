package lab1;

import java.util.Scanner;

public class Main {

    // 功能需求2：展示有向图
    public static void showDirectedGraph(Graph G){
        System.out.println("**********************************************");
        System.out.println("有向图: ");
        G.displayEdges();
    }


    public static void main(String[] args) {
        // 读取文件，得到String[] words
        String[] words = MyUtils.readFile("./src/hello.txt");
        System.out.println("**********************************************");
        System.out.println("文件读入的文本: ");
        for (String word : words) {
            System.out.print(word + " ");
        }
        System.out.println();

        // 利用words构建有向图并展示
        Graph g = new Graph(words);
        showDirectedGraph(g);

        // 查询桥接词
        System.out.println("**********************************************");
        String word3 = g.queryBridgeWords("to", "strange");
        System.out.println("word3: " + word3);

        // 根据桥接词生成新的文本
        System.out.println("**********************************************");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入需要改造的文本: ");
        String inputText =  scanner.nextLine();
        String newText = g.generateNewText(inputText);
        System.out.println("加入桥接词后的新文本: ");
        System.out.println(newText);

    }

}