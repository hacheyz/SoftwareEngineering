package lab1;

/**
 * Represents a node with a name attribute.
 * <p>
 * This class provides methods to get and set the name of the node.
 * </p>
 *
 * @author fyx
 */
public class Node {
  private String name;

  /**
   * Constructs a new Node with the specified name.
   *
   * @param name the name of the node
   */
  public Node(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the node.
   *
   * @return the name of the node
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the node.
   *
   * @param name the new name of the node
   */
  public void setName(String name) {
    this.name = name;
  }
}
