package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
public class SuccessorInTree {

  public static BinaryTree<Integer> findSuccessor(BinaryTree<Integer> node) {
    if (node.right != null) {
      BinaryTree<Integer> cur = node.right;
      while(cur.left != null) {
        cur = cur.left;
      }
      return cur;
    } 
    BinaryTree<Integer> cur = node;  
    while(cur.parent != null && cur.parent.right == cur) {
      cur = cur.parent;
    }
    return cur.parent;
    
  }
  @EpiTest(testDataFile = "successor_in_tree.tsv")
  public static int findSuccessorWrapper(TimedExecutor executor,
                                         BinaryTree<Integer> tree, int nodeIdx)
      throws Exception {
    BinaryTree<Integer> n = BinaryTreeUtils.mustFindNode(tree, nodeIdx);

    BinaryTree<Integer> result = executor.run(() -> findSuccessor(n));

    return result == null ? -1 : result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SuccessorInTree.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
