package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.List;
public class AddingCredits {
  public static class TreeNode {
    String val;
    int data;
    TreeNode left, right;
    boolean isDeleted;
    public TreeNode(String val, int data) {
      this.val = val;
      this.data = data;
      this.left = null;
      this.right = null;
      this.isDeleted = false;
    }
    public TreeNode(String val, int data, TreeNode left, TreeNode right) {
      this.val = val;
      this.data = data;
      this.left = left;
      this.right = right;
      this.isDeleted = false;
    }
  }
  public static class ClientsCreditsInfo {
    TreeNode root = new TreeNode("", Integer.MIN_VALUE);
    Map<String, TreeNode> map = new HashMap<>();
    int credits = 0;
    private TreeNode find(TreeNode root, int data) {
      if (root == null) {
        return null;
      }
      if (root.data == data) {
        return root;
      }
      return root.data<data ? find(root.right, data) : find(root.left, data);
    }
    private TreeNode find(String val) {
      return map.getOrDefault(val, null);
    }
    
    public void insert(String clientID, int c) {
      TreeNode cur = root;
      TreeNode node = new TreeNode(clientID, c-credits);
      map.put(clientID, node);
      
      return;
    }
    public boolean remove(String clientID) {
      TreeNode result = find(clientID);
      if (result == null) {
        return true;
      }
      result.isDeleted = true;
      return true;
    }
    public int lookup(String clientID) {
      TreeNode result = find(clientID);
      if (result == null) {
        return 0;
      }
      if (result.isDeleted) {
        return 0;
      }
      return result.val + credits;
    }
    public void addAll(int C) {
      credits += C;
      return;
    }
    public String max() {
      TreeNode cur = root.right;
      if (cur == null) {
        return "";
      }
      while(cur.right!=null) {
        cur = cur.right;
      }
      return cur.val;
    }
    @Override
    public String toString() {
      // TODO - you fill in here.
      return super.toString();
    }
  }
  @EpiUserType(ctorParams = {String.class, String.class, int.class})
  public static class Operation {
    public String op;
    public String sArg;
    public int iArg;

    public Operation(String op, String sArg, int iArg) {
      this.op = op;
      this.sArg = sArg;
      this.iArg = iArg;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Operation operation = (Operation)o;

      if (iArg != operation.iArg) {
        return false;
      }
      if (!op.equals(operation.op)) {
        return false;
      }
      return sArg.equals(operation.sArg);
    }

    @Override
    public int hashCode() {
      int result = op.hashCode();
      result = 31 * result + sArg.hashCode();
      result = 31 * result + iArg;
      return result;
    }

    @Override
    public String toString() {
      return String.format("%s(%s, %d)", op, sArg, iArg);
    }
  }

  @EpiTest(testDataFile = "adding_credits.tsv")
  public static void ClientsCreditsInfoTester(List<Operation> ops)
      throws TestFailure {
    ClientsCreditsInfo cr = new ClientsCreditsInfo();
    int opIdx = 0;
    for (Operation x : ops) {
      String sArg = x.sArg;
      int iArg = x.iArg;
      int result;
      switch (x.op) {
      case "ClientsCreditsInfo":
        break;
      case "remove":
        result = cr.remove(sArg) ? 1 : 0;
        if (result != iArg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, cr)
              .withProperty(TestFailure.PropertyName.COMMAND, x)
              .withMismatchInfo(opIdx, iArg, result);
        }
        break;
      case "insert":
        cr.insert(sArg, iArg);
        break;
      case "add_all":
        cr.addAll(iArg);
        break;
      case "lookup":
        result = cr.lookup(sArg);
        if (result != iArg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, cr)
              .withProperty(TestFailure.PropertyName.COMMAND, x)
              .withMismatchInfo(opIdx, iArg, result);
        }
      }
      opIdx++;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "AddingCredits.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
