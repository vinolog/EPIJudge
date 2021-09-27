package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SumRootToLeaf {
  static int result = 0;
  private static void helper(BinaryTreeNode<Integer> tree, int cur) {
    if (tree == null) {
      return;
    }
    if (tree.left == null && tree.right == null) {
      result += tree.data + (2*cur);
      return;
    }
    helper(tree.left, tree.data + (2*cur));
    helper(tree.right, tree.data + (2*cur));
  }
  @EpiTest(testDataFile = "sum_root_to_leaf.tsv")
  public static int sumRootToLeaf(BinaryTreeNode<Integer> tree) {
    result = 0;
    helper(tree, 0);
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SumRootToLeaf.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
