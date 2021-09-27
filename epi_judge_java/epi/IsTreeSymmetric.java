package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeSymmetric {
  private static boolean helper(BinaryTreeNode<Integer> tree1, BinaryTreeNode<Integer> tree2) {
    if (tree1 == null && tree2 == null) {
      return true;
    }
    if (tree1 == null || tree2 == null) {
      return false;
    }
    if (tree1.data != tree2.data) {
      return false;
    }
    return helper(tree2.left, tree1.right) && helper(tree1.left, tree2.right);
  }
  @EpiTest(testDataFile = "is_tree_symmetric.tsv")
  public static boolean isSymmetric(BinaryTreeNode<Integer> tree) {
    return helper(tree, tree);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeSymmetric.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
