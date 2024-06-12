package lab1;

import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {

  @Test
  public void queryBridgeWords1() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 1
    String result1 = g.queryBridgeWords("hello", "you");
    assertNull(result1);
  }

  @Test
  public void queryBridgeWords2() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 2
    String result2 = g.queryBridgeWords("you", "hello");
    assertNull(result2);
  }

  @Test
  public void queryBridgeWords3() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 3
    String result3 = g.queryBridgeWords("stars", "your");
    assertNull(result3);
  }

  @Test
  public void queryBridgeWords4() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 4
    String result4 = g.queryBridgeWords("of", "deep");
    assertNull(result4);
  }

  @Test
  public void queryBridgeWords5() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 5
    String result5 = g.queryBridgeWords("and", "of");
    assertTrue("Output should be either 'full think' or 'think full'",
        result5.matches("full think") || result5.matches("think full"));
  }

  @Test
  public void queryBridgeWords6() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 6
    String result6 = g.queryBridgeWords("and", "gentle");
    assertNull(result6);
  }

  @Test
  public void testGenerateNewText1() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 1: Input is empty string
    assertEquals("", g.generateNewText(""));
  }

  @Test
  public void testGenerateNewText2() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 2: Input is a single word "and"
    assertEquals("and", g.generateNewText("and"));
  }

  @Test
  public void testGenerateNewText3() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 3: Input with multiple words, some with bridge words
    assertEquals("Your love fled and nodding by the fire",
        g.generateNewText("Your love and by the fire"));
  }

  @Test
  public void testGenerateNewText4() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 4: Input with punctuation and multiple words
    assertEquals("Your love, and nodding by the fire",
        g.generateNewText("Your love, and by the fire"));
  }

  @Test
  public void testGenerateNewText5() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);
    // Test Case 5: Input with multiple words and multiple possible outputs
    String result5 = g.generateNewText("When you are old and grey and of sleep");
    assertTrue(result5.equals("When you are old and grey and full of sleep") ||
        result5.equals("When you are old and grey and think of sleep"));
  }
}