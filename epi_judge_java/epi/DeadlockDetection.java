package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;
public class DeadlockDetection {

  public static class GraphVertex {
    public enum Color {W, G, B};
    public Color color;
    public List<GraphVertex> edges;

    public GraphVertex() { 
      color = Color.W;
      edges = new ArrayList<>(); 
    }
  }
  private static boolean hasCycle(GraphVertex cur) {
    if (cur.color == GraphVertex.Color.G) {
      return true;
    }
    cur.color = GraphVertex.Color.G;
    for (GraphVertex vertex: cur.edges) {
      if (vertex.color != GraphVertex.Color.B && hasCycle(vertex)) {
        return true;
      }
    }
    cur.color = GraphVertex.Color.B;
    return false;
  }
  public static boolean isDeadlocked(List<GraphVertex> graph) {
    for (GraphVertex vertex: graph) {
      if (vertex.color == GraphVertex.Color.W && hasCycle(vertex)) {
        return true;
      }
    }
    return false;
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

  @EpiTest(testDataFile = "deadlock_detection.tsv")
  public static boolean isDeadlockedWrapper(TimedExecutor executor,
                                            int numNodes, List<Edge> edges)
      throws Exception {
    if (numNodes <= 0) {
      throw new RuntimeException("Invalid numNodes value");
    }
    List<GraphVertex> graph = new ArrayList<>();
    for (int i = 0; i < numNodes; i++) {
      graph.add(new GraphVertex());
    }
    for (Edge e : edges) {
      if (e.from < 0 || e.from >= numNodes || e.to < 0 || e.to >= numNodes) {
        throw new RuntimeException("Invalid vertex index");
      }
      graph.get(e.from).edges.add(graph.get(e.to));
    }

    return executor.run(() -> isDeadlocked(graph));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DeadlockDetection.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
