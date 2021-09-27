package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
public class LowestCommonAncestor {
  static public class Pair {
    int count;
    BinaryTreeNode<Integer> tree;
    Pair(int count, BinaryTreeNode<Integer> tree) {
      this.count = count;
      this.tree = tree;
    }
  }
  private static Pair helper(BinaryTreeNode<Integer> tree,
                             BinaryTreeNode<Integer> node0,
                             BinaryTreeNode<Integer> node1) {
    if (tree == null) {
      return new Pair(0, null);
    }
    Pair left = helper(tree.left, node0, node1);
    if (left.count == 2) {
      return left;
    }
    Pair right = helper(tree.right, node0, node1);
    if (right.count == 2) {
      return right;
    }
    Pair res = new Pair(0, null);
    if (tree == node0) {
      res.count++;
    }
    if (tree == node1) {
      res.count++;
    }
    res.count += left.count + right.count;
    if (res.count == 2) {
      res.tree = tree;
    }
    return res;
  }
  public static BinaryTreeNode<Integer> lca(BinaryTreeNode<Integer> tree,
                                            BinaryTreeNode<Integer> node0,
                                            BinaryTreeNode<Integer> node1) {
    if (tree == null) {
      return null;
    }
    Pair result = helper(tree, node0, node1);
    return result.tree;
  }
  @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TimedExecutor executor,
                               BinaryTreeNode<Integer> tree, Integer key0,
                               Integer key1) throws Exception {
    BinaryTreeNode<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BinaryTreeNode<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BinaryTreeNode<Integer> result =
        executor.run(() -> lca(tree, node0, node1));

    if (result == null) {
      throw new TestFailure("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LowestCommonAncestor.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
