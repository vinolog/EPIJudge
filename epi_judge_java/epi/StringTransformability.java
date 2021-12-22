package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;
public class StringTransformability {
  public enum Color { B,W,G; }
  public static class GraphVertex {
    public String word;
    public List<GraphVertex> edges;
    public Color color;
    public GraphVertex(String word) {
      this.word = word;
      this.color = Color.G;
      this.edges = new ArrayList<>();
    }
  }
  private static boolean isOneDistance(String first, String second) {
    int count = 0;
    if (first.length() != second.length()) {
      return false;
    }
    for (int i=0; i<first.length(); ++i) {
      if (first.charAt(i) != second.charAt(i)) {
        if (count == 1) {
          return false;
        }
        count++;
      }
    }
    return true;
  }
  private static void buildGraph(Set<String> D, Map<String, GraphVertex> map) {
    for (String cur: D) {
      map.put(cur, new GraphVertex(cur));
    }
    for (String cur: D) {
      for (String next: D) {
        if (cur.compareTo(next)!=0 && isOneDistance(cur, next)) {
          map.get(cur).edges.add(map.get(next));
        }
      }
    }
  }

  @EpiTest(testDataFile = "string_transformability.tsv")
  public static int transformString(Set<String> D, String s, String t) {
    Map<String, GraphVertex> map = new HashMap<>();
    Queue<GraphVertex> queue = new LinkedList<>();
    boolean found = false;
    int distance = 0;

    buildGraph(D, map);
    queue.offer(map.get(s));
    
    while(!queue.isEmpty()) {
      distance++;
      Queue<GraphVertex> temp = new LinkedList<>();
      while(!queue.isEmpty()) {
        GraphVertex cur = queue.poll();
        cur.color = Color.W;
        for (GraphVertex next: cur.edges) {
          if (next.word.compareTo(t) == 0) {
            return distance;
          }
          if (next.color == Color.G) {
            temp.offer(next);
          }
        }
      }
      queue = temp;
    }
    return -1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringTransformability.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
