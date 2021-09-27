package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.*;
public class LowestCommonAncestorWithParent {

  public static BinaryTree<Integer> lca(BinaryTree<Integer> node0,
                                        BinaryTree<Integer> node1) {
    Map<Integer,BinaryTree<Integer>> map = new HashMap<>();
    while(node0!=null) {
      map.put(node0.data, node0);
      node0 = node0.parent;
    }
    while(node1!=null) {
      if (map.containsKey(node1.data)) {
        return node1;
      }
      node1 = node1.parent;
    }
    return null;
  }
  @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TimedExecutor executor, BinaryTree<Integer> tree,
                               Integer key0, Integer key1) throws Exception {
    BinaryTree<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BinaryTree<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BinaryTree<Integer> result = executor.run(() -> lca(node0, node1));

    if (result == null) {
      throw new TestFailure("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LowestCommonAncestorWithParent.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
