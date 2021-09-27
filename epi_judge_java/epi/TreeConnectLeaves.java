package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class TreeConnectLeaves {
  private static void helper(BinaryTreeNode<Integer> root, List<BinaryTreeNode<Integer>> result) {
    if (root == null) {
      return;
    }
    if (root.left == null && root.right == null) {
      result.add(root);
      return;
    }
    helper(root.left, result);
    helper(root.right, result);
  }
  public static List<BinaryTreeNode<Integer>>
  createListOfLeaves(BinaryTreeNode<Integer> tree) {
    List<BinaryTreeNode<Integer>> result = new ArrayList<>();
    helper(tree, result);
    return result;
  }
  @EpiTest(testDataFile = "tree_connect_leaves.tsv")
  public static List<Integer>
  createListOfLeavesWrapper(TimedExecutor executor,
                            BinaryTreeNode<Integer> tree) throws Exception {
    List<BinaryTreeNode<Integer>> result =
        executor.run(() -> createListOfLeaves(tree));

    if (result.stream().anyMatch(x -> x == null || x.data == null)) {
      throw new TestFailure("Result can't contain null");
    }

    List<Integer> extractedRes = new ArrayList<>();
    for (BinaryTreeNode<Integer> x : result) {
      extractedRes.add(x.data);
    }
    return extractedRes;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeConnectLeaves.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
