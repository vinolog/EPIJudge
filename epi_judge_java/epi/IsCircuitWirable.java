package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;
public class IsCircuitWirable {
  public enum Color {
      B,W,G;
    }
  public static class GraphVertex {
    public int d = -1;
     
    Color color = Color.G;
    public List<GraphVertex> edges = new ArrayList<>();
  }

  private static boolean bfs(GraphVertex vertex) {
    Queue<GraphVertex> queue = new LinkedList<>();
    Color val = Color.W;
    queue.offer(vertex);
    vertex.color = val;
    while(!queue.isEmpty()) {
      val = (val==Color.W)?Color.B:Color.W;
      Queue<GraphVertex> temp = new LinkedList<>();
      while(!queue.isEmpty()) {
        GraphVertex cur = queue.poll();
        for (GraphVertex v: cur.edges) {
          if (v.color == cur.color) {
            return false;
          }
          if (v.color == Color.G) {
            v.color = val;
            temp.offer(v);  
          }
        }
      }
      queue = temp;
    }
    return true;
  }

  public static boolean isAnyPlacementFeasible(List<GraphVertex> graph) {
    for (GraphVertex vertex: graph) {
      if (vertex.color == Color.G) {
        if (!bfs(vertex)) {
          return false;
        }
      }
    }
    return true;
  }
  @EpiUserType(ctorParams = {int.class, int.class})
  public static class Edge {
    public int from;
    public int to;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

  @EpiTest(testDataFile = "is_circuit_wirable.tsv")
  public static boolean isAnyPlacementFeasibleWrapper(TimedExecutor executor,
                                                      int k, List<Edge> edges)
      throws Exception {
    if (k <= 0)
      throw new RuntimeException("Invalid k value");
    List<GraphVertex> graph = new ArrayList<>();
    for (int i = 0; i < k; i++)
      graph.add(new GraphVertex());
    for (Edge e : edges) {
      if (e.from < 0 || e.from >= k || e.to < 0 || e.to >= k)
        throw new RuntimeException("Invalid vertex index");
      graph.get(e.from).edges.add(graph.get(e.to));
    }

    return executor.run(() -> isAnyPlacementFeasible(graph));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsCircuitWirable.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
