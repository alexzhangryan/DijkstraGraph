
public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {
  protected BinaryTreeNode<T> root;

  public BSTRotation() {
    root = super.root;
  }

  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a left child of the provided parent, this method will perform a right rotation. When the
   * provided child is a right child of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will either
   * throw a NullPointerException: when either reference is null, or otherwise will throw an
   * IllegalArgumentException.
   *
   * @param child  is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   * @throws NullPointerException     when either passed argument is null
   * @throws IllegalArgumentException when the provided child and parent nodes are not initially
   *                                  (pre-rotation) related that way
   */
  protected void rotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent)
      throws NullPointerException, IllegalArgumentException {
    Boolean related = false;
    // throw error if child and parent are null
    if (child == null || parent == null) {
      throw new NullPointerException("passed argument is null");
    }
    // checks to see if parent and child are related
    if ((parent.childLeft().equals(child) || parent.childRight().equals(child))) {
      related = true;
    }
    if (!related) {
      throw new IllegalArgumentException("parent and child are not related");
    }
    // calls right rotation method if it is a left child
    if (child.equals(parent.childLeft())) {
      rightRotation(child, parent);
    }
    // calls left rotation method if it is a right child
    if (child.equals(parent.childRight())) {
      leftRotation(child, parent);
    }

  }

  /**
   * performs a right rotation by swapping the child for the parent and accounting for extra
   * children the parents have
   * 
   * @param child  child node
   * @param parent parent node
   */
  private void rightRotation(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {
    // updates root if necessary
    if (parent.equals(this.root)) {
      root = child;
      // updates child's parent depending on whether it is right or left child
    } else if (parent.parent().childRight().equals(parent)) {
      parent.parent().setChildRight(child);;
    } else {
      parent.parent().setChildLeft(child);;
    }
    // switches parent's and child's child
    parent.setChildLeft(child.childRight());
    // makes the parent the child of the child
    child.setChildRight(parent);
  }

  /**
   * performs a right rotation by swapping the child for the parent and accounting for extra
   * children the parents have
   * 
   * @param child  child node
   * @param parent parent node
   */
  private void leftRotation(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {
    // updates root if necessary
    if (parent == root) {
      root = child;
      // updates child's parent depending on whether it is right or left child
    } else if (parent.parent().childLeft().equals(parent)) {
      parent.parent().setChildLeft(child);

    } else {
      parent.parent().setChildRight(child);
    }
    // switches parent's and child's child
    parent.setChildRight(child.childLeft());
    // makes the parent the child of the child
    child.setChildLeft(parent);
  }

  // test method for right rotation w zero shared children
  public Boolean test1() {
    Boolean expected = true;
    BSTRotation<Integer> tree = new BSTRotation<Integer>();
    tree.insert(5);
    // System.out.println(root == null);
    tree.insert(7);
    tree.insert(6);
    tree.insert(3);
    tree.insert(4);
    System.out.println(tree.root.childRight());
    tree.rotate(tree.root.childRight().childLeft(), tree.root.childRight());
    if (!tree.root.childRight().getData().equals(6)) {
      System.out.println(root.childRight().getData());
      expected = false;
    }
    if (!tree.root.childRight().childRight().getData().equals(7)) {
      System.out.println(root.childRight().childRight());
      expected = false;
    }

    return expected;

  }

  // test method for right rotation w 1 shared child and root
  public Boolean test2() {
    Boolean expected = true;
    BSTRotation<Integer> tree2 = new BSTRotation<Integer>();
    tree2.insert(5);
    tree2.insert(6);
    tree2.insert(10);
    tree2.insert(3);
    tree2.insert(4);
    tree2.rotate(tree2.root.childRight(), tree2.root);
    if (!tree2.root.getData().equals(6)) {
      expected = false;
    }
    if (!tree2.root.childLeft().getData().equals(5)) {
      expected = false;
    }
    return expected;
  }

  // test method for left rotation w 2 shared children
  public Boolean test3() {
    Boolean expected = true;
    BSTRotation<Integer> tree = new BSTRotation<Integer>();
    tree.insert(5);
    // System.out.println(root == null);
    tree.insert(7);
    tree.insert(6);
    tree.insert(9);
    tree.insert(10);
    tree.insert(3);
    tree.insert(4);
    tree.insert(2);
    System.out.println(tree.root.childRight());
    tree.rotate(tree.root.childLeft().childLeft(), tree.root.childLeft());
    // TODO change this so its right
    if (!tree.root.childLeft().getData().equals(2)) {
      expected = false;
    }
    if (!tree.root.childLeft().childRight().getData().equals(3)) {
      System.out.println(root.childLeft().childRight());
      expected = false;
    }

    return expected;

  }

  // test method for right rotation w 3 shared children
  public Boolean test4() {
    Boolean expected = true;
    BSTRotation<Integer> tree = new BSTRotation<Integer>();
    tree.insert(5);
    // System.out.println(root == null);
    tree.insert(7);
    tree.insert(6);
    tree.insert(9);
    tree.insert(10);
    tree.insert(11);
    tree.insert(3);
    tree.insert(4);
    System.out.println(tree.root.childRight());
    tree.rotate(tree.root.childRight().childLeft(), tree.root.childRight());
    if (!tree.root.childRight().getData().equals(6)) {
      System.out.println(root.childRight().getData());
      expected = false;
    }
    if (!tree.root.childRight().childRight().getData().equals(7)) {
      System.out.println(root.childRight().childRight());
      expected = false;
    }

    return expected;
  }

  public static void main(String[] args) {
    BSTRotation<Integer> bstRotation = new BSTRotation<Integer>();
    System.out.println(
        bstRotation.test1() && bstRotation.test2() && bstRotation.test3() && bstRotation.test4());

  }
}
