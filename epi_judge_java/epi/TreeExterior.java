package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.*;
public class TreeExterior {
  private static void addLeaves(BinaryTreeNode<Integer> root, List<BinaryTreeNode<Integer>> result) {
    if (root == null) {
      return;
    }
    if (root.left == null  && root.right == null) {
      result.add(root);
      return;
    }
    addLeaves(root.left, result);
    addLeaves(root.right, result);
  }
  public static List<BinaryTreeNode<Integer>>
  exteriorBinaryTree(BinaryTreeNode<Integer> tree) {
    List<BinaryTreeNode<Integer>> result = new ArrayList<>();
    Stack<BinaryTreeNode<Integer>> stack = new Stack<>();
    BinaryTreeNode<Integer> root = tree;    
    if (root == null) {
      return result;
    }
    result.add(root);
    root = root.left;
    while(root != null) {    
      if (root.left == null && root.right == null) {
        break;
      }
      result.add(root);
      if (root.left != null) {
        root = root.left;
      } else {
        root = root.right;
      }
    }  
    addLeaves(tree.left, result);
    addLeaves(tree.right, result);
    root = tree.right;
    while(root != null) {
      if (root.left == null && root.right == null) {
        break;
      }
      stack.push(root);
      if (root.right == null) {
        root = root.left;
      } else {
        root = root.right;
      }
    }
    
    while(!stack.isEmpty()) {
      result.add(stack.pop());
    }
    return result;
  }
  private static List<Integer> createOutputList(List<BinaryTreeNode<Integer>> L)
      throws TestFailure {
    if (L.contains(null)) {
      throw new TestFailure("Resulting list contains null");
    }
    List<Integer> output = new ArrayList<>();
    for (BinaryTreeNode<Integer> l : L) {
      output.add(l.data);
    }
    return output;
  }

  @EpiTest(testDataFile = "tree_exterior.tsv")
  public static List<Integer>
  exteriorBinaryTreeWrapper(TimedExecutor executor,
                            BinaryTreeNode<Integer> tree) throws Exception {
    List<BinaryTreeNode<Integer>> result =
        executor.run(() -> exteriorBinaryTree(tree));

    return createOutputList(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeExterior.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
