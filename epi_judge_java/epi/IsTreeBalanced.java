package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeBalanced {
  static boolean result = true;
  private static int helper(BinaryTreeNode<Integer> tree) {
    if (tree == null) {
      return 0;
    }
    int left = helper(tree.left);
    int right = helper(tree.right);
    if (Math.abs(left-right)>1) {
      result = false;
    }
    return 1+Math.max(left, right);
  }

  @EpiTest(testDataFile = "is_tree_balanced.tsv")
  public static boolean isBalanced(BinaryTreeNode<Integer> tree) {
    result = true;
    helper(tree);
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeBalanced.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
