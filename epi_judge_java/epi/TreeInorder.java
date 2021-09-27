package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;
public class TreeInorder {

  private static class NodeAndState {
    public BinaryTreeNode<Integer> node;
    public Boolean leftSubtreeTraversed;

    public NodeAndState(BinaryTreeNode<Integer> node,
                        Boolean leftSubtreeTraversed) {
      this.node = node;
      this.leftSubtreeTraversed = leftSubtreeTraversed;
    }
  }

  @EpiTest(testDataFile = "tree_inorder.tsv")
  public static List<Integer> inorderTraversal(BinaryTreeNode<Integer> tree) {
    Stack<BinaryTreeNode<Integer>> stack = new Stack<>();
    List<Integer> result = new ArrayList<>();
    BinaryTreeNode<Integer> cur = tree;
    while(cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.push(cur);
        cur = cur.left;
      } else {
        cur = stack.pop();
        result.add(cur.data);  
        cur = cur.right;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
