package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;
public class TreeFromPreorderInorder {
  private static BinaryTreeNode<Integer> helper(List<Integer> preorder, 
                                                List<Integer> inorder,
                                                Map<Integer, Integer> nodeMap,
                                                int rootPos,
                                                int inStart,
                                                int inEnd) {
    
    if (inStart > inEnd || inStart < 0 || inEnd >= inorder.size() || rootPos >= preorder.size() || rootPos < 0) {
      return null;
    }
    if (inStart == inEnd) {
      return new BinaryTreeNode<Integer>(inorder.get(inStart));
    }
    BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(preorder.get(rootPos));
    root.left = helper(preorder, inorder, nodeMap, rootPos+1, inStart, nodeMap.get(root.data)-1);
    root.right = helper(preorder, inorder, nodeMap, rootPos+nodeMap.get(root.data)-inStart+1, nodeMap.get(root.data)+1, inEnd);
    return root;
  }
  @EpiTest(testDataFile = "tree_from_preorder_inorder.tsv")
  public static BinaryTreeNode<Integer>
  binaryTreeFromPreorderInorder(List<Integer> preorder, List<Integer> inorder) {
    Map<Integer, Integer> nodeMap = new HashMap<>();
    for (int i=0; i<inorder.size(); ++i) {
      nodeMap.put(inorder.get(i), i);
    }
    return helper(preorder, inorder, nodeMap, 0, 0, inorder.size()-1);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeFromPreorderInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
