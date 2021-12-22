package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.List;
public class MaxTeamsInPhotograph {

  public static class GraphVertex {
    public List<GraphVertex> edges = new ArrayList<>();
    // Set maxDistance = 0 to indicate unvisited vertex.
    public int maxDistance = 0;
  }

  private static int dfs(GraphVertex vertex) {
    if (vertex.maxDistance!=0) {
      return vertex.maxDistance;
    }
    int max = 1;
    for (GraphVertex next: vertex.edges) {
      max = Math.max(max, 1+dfs(next));
    }
    vertex.maxDistance = max;
    return max;
  }
  public static int findLargestNumberTeams(List<GraphVertex> graph) {
    int result = 0;
    for (GraphVertex vertex: graph) {
      if (vertex.maxDistance == 0) {
        result = Math.max(result, dfs(vertex));
      }
    }
    return result;
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

  @EpiTest(testDataFile = "max_teams_in_photograph.tsv")
  public static int findLargestNumberTeamsWrapper(TimedExecutor executor, int k,
                                                  List<Edge> edges)
      throws Exception {
    if (k <= 0) {
      throw new RuntimeException("Invalid k value");
    }
    List<GraphVertex> graph = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      graph.add(new GraphVertex());
    }
    for (Edge e : edges) {
      if (e.from < 0 || e.from >= k || e.to < 0 || e.to >= k) {
        throw new RuntimeException("Invalid vertex index");
      }
      graph.get(e.from).edges.add(graph.get(e.to));
    }

    return executor.run(() -> findLargestNumberTeams(graph));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxTeamsInPhotograph.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
