package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;
public class MatrixEnclosedRegions {
  static class Cordinate {
    int x,y;
    public Cordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public String toString() {
      return x+","+y;
    }
  }
  private static void dfs(List<List<Character>> board, Cordinate start, HashSet<String> visited, int[][] dirs) {
    if (start.x<0 || start.x>=board.size() || start.y<0 || start.y>=board.get(start.x).size() || visited.contains(start.toString()) || board.get(start.x).get(start.y) == 'B') {
      return;
    }
    visited.add(start.toString());
    for (int[] dir: dirs) {
      dfs(board, new Cordinate(start.x+dir[0], start.y+dir[1]), visited, dirs);
    }
  }
  public static void fillSurroundedRegions(List<List<Character>> board) {
    HashSet<String> visited = new HashSet<>();
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    for (int i=0; i<board.size(); i++) {
      dfs(board, new Cordinate(i,0), visited, dirs);  
      dfs(board, new Cordinate(i,board.get(i).size()-1), visited, dirs);  
    }
    for (int j=0; j<board.get(0).size(); j++) {
      dfs(board, new Cordinate(0,j), visited, dirs);  
      dfs(board, new Cordinate(board.size()-1, j), visited, dirs);  
    }
    for (int i=0; i<board.size(); ++i) {
      for (int j=0; j<board.get(i).size(); ++j) {
        if (!visited.contains(i+","+j)) {
          board.get(i).set(j, 'B');
        }
      }
    }
    return;
  }
  @EpiTest(testDataFile = "matrix_enclosed_regions.tsv")
  public static List<List<Character>>
  fillSurroundedRegionsWrapper(List<List<Character>> board) {
    fillSurroundedRegions(board);
    return board;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixEnclosedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
