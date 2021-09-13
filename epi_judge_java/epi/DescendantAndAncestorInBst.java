package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
public class DescendantAndAncestorInBst {
  private static boolean helper(BstNode<Integer> tree, BstNode<Integer> key) {
    if (tree == null) {
      return false;
    }
    if (tree == key) {
      return true;
    }
    if (tree.data > key.data) {
      return helper(tree.left, key);
    }
    return helper(tree.right, key);
  }
  public static boolean
  pairIncludesAncestorAndDescendantOfM(BstNode<Integer> possibleAncOrDesc0,
                                       BstNode<Integer> possibleAncOrDesc1,
                                       BstNode<Integer> middle) {
    boolean findDesc0 = helper(middle, possibleAncOrDesc0);
    boolean findDesc1 = helper(middle, possibleAncOrDesc1);
    if ((findDesc0 && findDesc1) || (!findDesc0 && !findDesc1)) {
      return false;
    }
    if (findDesc0) {
      return helper(possibleAncOrDesc1, middle);
    }
    return helper(possibleAncOrDesc0, middle);
  }
  @EpiTest(testDataFile = "descendant_and_ancestor_in_bst.tsv")
  public static boolean pairIncludesAncestorAndDescendantOfMWrapper(
      TimedExecutor executor, BstNode<Integer> tree, int possibleAncOrDesc0,
      int possibleAncOrDesc1, int middle) throws Exception {
    final BstNode<Integer> candidate0 =
        BinaryTreeUtils.mustFindNode(tree, possibleAncOrDesc0);
    final BstNode<Integer> candidate1 =
        BinaryTreeUtils.mustFindNode(tree, possibleAncOrDesc1);
    final BstNode<Integer> middleNode =
        BinaryTreeUtils.mustFindNode(tree, middle);

    return executor.run(()
                            -> pairIncludesAncestorAndDescendantOfM(
                                candidate0, candidate1, middleNode));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DescendantAndAncestorInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
