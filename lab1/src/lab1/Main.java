package lab1;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Main class to demonstrate various functionalities for handling directed graphs.
 * <p>
 * The functionalities include displaying the directed graph, querying bridge words,
 * generating new text based on bridge words, calculating the shortest path between
 * two words, and performing a random walk on the graph.
 * </p>
 */
public class Main {

  /**
   * Displays the directed graph.
   *
   * @param g the graph to be displayed
   */
  public static void showDirectedGraph(Graph g) {
    System.out.println("有向图: ");
    g.displayEdges();
  }

  /**
   * Main method to execute the program.
   * <p>
   * The method reads words from a file, constructs a directed graph, creates an output
   * folder, and provides a menu for the user to choose various functionalities such as
   * displaying the graph, querying bridge words, generating new text, calculating the
   * shortest path, and performing a random walk.
   * </p>
   *
   * @param args command-line arguments
   */
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

    // 创建output文件夹用于保存输出结果
    MyUtils.checkAndCreateFolder("./output");

    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    boolean flag = true;
    while (flag) {
      System.out.println("**********************************************");
      System.out.println("请选择一个功能:");
      System.out.println("1. 展示有向图");
      System.out.println("2. 查询桥接词");
      System.out.println("3. 根据桥接词生成新的文本");
      System.out.println("4. 计算两个词的最短路径");
      System.out.println("5. 随机游走");
      System.out.println("6. 退出");
      System.out.print("输入你的选择: ");

      int choice = -1;
      try {
        choice = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("无效输入，请输入数字。");
        continue;
      }

      switch (choice) {
        case 1:
          System.out.println("**********************************************");
          System.out.println("展示有向图");
          showDirectedGraph(g);
          GraphRenderer graphRenderer = new GraphRenderer(g);
          graphRenderer.renderGraph();
          break;
        case 2:
          // 查询桥接词
          System.out.println("**********************************************");
          System.out.println("查询桥接词：");
          System.out.println("请输入word1: ");
          String word1 = scanner.nextLine();      // to
          System.out.println("请输入word2: ");
          String word2 = scanner.nextLine();      // strange
          String bridgeWords = g.queryBridgeWords(word1, word2);
          if (bridgeWords != null) {
            System.out.println("The bridge words from \""
                + word1 + "\" to \"" + word2 + "\" are: " + bridgeWords);
          }
          break;
        case 3:
          // 根据桥接词生成新的文本
          System.out.println("**********************************************");
          System.out.println("根据桥接词生成新的文本");
          System.out.println("请输入需要改造的文本: ");
          String inputText = scanner.nextLine();
          String newText = g.generateNewText(inputText);
          System.out.println("加入桥接词后的新文本: ");
          System.out.println(newText);
          break;
        case 4:
          // 计算最短路径
          System.out.println("**********************************************");
          System.out.println("计算两个词的最短路径：");
          System.out.println("请输入word1: ");
          String start = scanner.nextLine();
          System.out.println("请输入word2: ");
          String end = scanner.nextLine();
          String shortestPath = g.calcShortestPath(start, end);
          if (shortestPath != null) {
            System.out.println(shortestPath);
          }
          break;
        case 5:
          // 随机游走
          System.out.println("**********************************************");
          System.out.println("随机游走：");
          String walk = g.randomWalk();
          // write to file
          MyUtils.writeWalkToFile(walk, "./output/random_walk.txt");
          break;
        case 6:
          System.out.println("退出程序。");
          flag = false;
          break;
        default:
          System.out.println("无效选择，请重新输入。");
          break;
      }
    }
  }
}
