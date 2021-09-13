package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeABst {
  

  private static boolean helper(BinaryTreeNode<Integer> tree, int minVal, int maxVal) {
    if (tree == null) {
      return true;
    }
    if (tree.data > maxVal || tree.data < minVal) {
      return false;
    }
    return helper(tree.left, minVal, tree.data) && helper(tree.right, tree.data, maxVal); 
  }

  @EpiTest(testDataFile = "is_tree_a_bst.tsv")
  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    return helper(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeABst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
