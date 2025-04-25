
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
  protected BinaryTreeNode root;
  protected int size;

  /**
   * binary search tree class
   */
  public BinarySearchTree() {
    this.root = new BinaryTreeNode(null);
    size = 0;
  }

  @Override
  /**
   * inserts a node into the binary search tree
   * 
   * @param data to be inserted
   * @throws NullPointerException
   */
  public void insert(T data) throws NullPointerException {
    if (data == null) {
      throw new NullPointerException("data is null");
    }
    // sets the root node if tree is empty
    if (isEmpty()) {
      root.setData(data);
      size++;
    } else {
      insertHelper(root, new BinaryTreeNode<T>(data));
    }
  }

  /**
   * insertHelper recursive helper method to assist with adding the new subtree
   * 
   * @param newNode
   * @param subtree
   */
  /**
   * Performs the naive binary search tree insert algorithm to recursively insert the provided
   * newNode (which has already been initialized with a data value) into the provided tree/subtree.
   * When the provided subtree is null, this method does nothing.
   */
  protected void insertHelper(BinaryTreeNode<T> newNode, BinaryTreeNode<T> subtree) {
    // if data is smaller set left subtree
    if (newNode.getData().compareTo(subtree.getData()) >= 0) {
      if (newNode.childLeft() == null) {
        newNode.setChildLeft(subtree);
        subtree.setParent(newNode);
        // System.out.println(subtree.parent());
        size++;
        // if node possesses left child call method again recursively
      } else {
        insertHelper(newNode.childLeft(), subtree);
      }
      // if data is bigger sets right subtree
    } else if (newNode.getData().compareTo(subtree.getData()) < 0) {
      if (newNode.childRight() == null) {
        newNode.setChildRight(subtree);
        subtree.setParent(newNode);
        // System.out.println(subtree.parent());
        // System.out.println(newNode.childRight().parent());
        size++;
      } else {
        insertHelper(newNode.childRight(), subtree);
      }
    }
  }

  /**
   * checks if it contains a value
   */
  @Override
  public boolean contains(Comparable<T> data) {
    return containsHelper(root, data);
  }

  /**
   * recursive helper method for contains method
   * 
   * @param node current node
   * @param data data youre looking for
   * @return true if it contains
   */
  private boolean containsHelper(BinaryTreeNode<T> node, Comparable<T> data) {
    if (node == null) {
      return false;
    } else if (node.getData().compareTo((T) data) == 0) {
      return true;
    } else if (node.getData().compareTo((T) data) > 0) {
      return containsHelper(node.childLeft(), data);
    } else {
      return containsHelper(node.childRight(), data);
    }
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return size;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return (size == 0);
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    root = null;
    size = 0;
  }

  /**
   * private helper method to make debugging easier by printing out the whole tree
   * 
   * @param node
   */
  private void print(BinaryTreeNode<T> node) {
    if (node == null)
      return;
    print(node.left);
    print(node.right);
  }

  public Boolean test1() {
    boolean expected = true;

    BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
    tree.insert(5);
    tree.insert(6);
    tree.insert(10);
    tree.insert(3);
    tree.insert(4);
    tree.print(tree.root);
    if (!tree.root.getData().equals(5)) {
      expected = false;
    }
    if (!tree.root.childLeft().getData().equals(3)) {
      expected = false;
    }
    if (!tree.root.childRight().getData().equals(6)) {
      expected = false;
    }
    if (!tree.root.childRight().childRight().getData().equals(10)) {
      expected = false;
    }
    if (!tree.root.childLeft().childRight().getData().equals(4)) {
      expected = false;
    }
    return expected;
  }

  public Boolean test2() {
    boolean expected = true;
    BinarySearchTree<String> tree = new BinarySearchTree<String>();
    tree.insert("b");
    tree.insert("d");
    tree.insert("c");
    tree.insert("e");
    tree.insert("a");
    // tree.print(tree.root);
    if (!tree.contains("b")) {
      expected = false;
    }
    if (!tree.contains("a")) {
      expected = false;
    }
    if (!tree.contains("c")) {
      expected = false;
    }
    if (!tree.contains("d")) {
      expected = false;
    }
    if (!tree.contains("e")) {
      expected = false;
    }
    return expected;
  }

  public Boolean test3() {
    boolean expected = true;
    BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
    if (tree.size != 0) {
      expected = false;
    }
    tree.insert(3);
    tree.insert(8);
    tree.insert(33);
    tree.insert(1);
    tree.insert(16224);
    if (tree.size != 5) {
      expected = false;
    }
    tree.clear();
    if (tree.size != 0) {
      expected = false;
    }
    if (tree.root != null) {
      expected = false;
    }
    return expected;
  }


  public static void main(String[] args) {
    BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
    BinarySearchTree<Integer> tree2 = new BinarySearchTree<Integer>();
    tree2.insert(5);
    tree2.insert(6);
    System.out.println(tree2.root.childRight());
    System.out.println(tree2.root.childRight().parent());
    System.out.println(tree.test1());
    System.out.println(tree.test2());
    System.out.println(tree.test3());
  }
}

