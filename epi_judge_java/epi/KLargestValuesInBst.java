package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiPredicate;
public class KLargestValuesInBst {
  private static void helper(BstNode<Integer> tree, int k, List<Integer> result) {
    if (tree == null) {
      return;
    }
    helper(tree.right, k, result);
    if (k != result.size()) {
      result.add(tree.data);
      helper(tree.left, k, result);
    }
  }

  @EpiTest(testDataFile = "k_largest_values_in_bst.tsv")
  public static List<Integer> findKLargestInBst(BstNode<Integer> tree, int k) {
    List<Integer> result = new ArrayList<>();
    helper(tree, k, result);
    return result;
  }
  @EpiTestComparator
  public static boolean comp(List<Integer> expected, List<Integer> result) {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KLargestValuesInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
