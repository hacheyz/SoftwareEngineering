package lab1;

import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {

  @Test
  public void queryBridgeWords() {
    String[] words = MyUtils.readFile("./src/hello.txt");
    Graph g = new Graph(words);

    // Test Case 1
    String result1 = g.queryBridgeWords("hello", "you");
    assertNull(result1);

    // Test Case 2
    String result2 = g.queryBridgeWords("and", "gentle");
    assertNull(result2);

    // Test Case 3
    String result3 = g.queryBridgeWords("and", "of");
    assertTrue("Output should be either 'full think' or 'think full'",
        result3.matches("full think") || result3.matches("think full"));

    // Test Case 4
    String result4 = g.queryBridgeWords("loved", "beauty");
    assertEquals("your", result4);
  }
}