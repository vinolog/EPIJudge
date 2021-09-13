package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class BstFromPreorder {
  private static BstNode<Integer> helper(List<Integer> preorderSequence, int start, int end) {
    if (start>end || start<0 || end<0) {
      return null;
    }
    if (start == end) {
      return new BstNode<Integer>(preorderSequence.get(start));
    }
    BstNode<Integer> tree = new BstNode<Integer>(preorderSequence.get(start));
    int split = start+1;
    for (int i=start+1; i<=end; ++i) {
      if (preorderSequence.get(i)<preorderSequence.get(start)) {
        split++;
      }
    }    
    tree.left = helper(preorderSequence, start+1, split-1);
    tree.right = helper(preorderSequence, split, end);
    return tree;
  } 

  @EpiTest(testDataFile = "bst_from_preorder.tsv")
  public static BstNode<Integer>
  rebuildBSTFromPreorder(List<Integer> preorderSequence) {
    return helper(preorderSequence, 0, preorderSequence.size()-1);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BstFromPreorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
