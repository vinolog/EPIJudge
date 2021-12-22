package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;
public class MatrixConnectedRegions {
  static class Cordinate {
    int x,y;
    public Cordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  public static void flipColor(int x, int y, List<List<Boolean>> image) {
    HashSet<String> visited = new HashSet<>();
    Queue<Cordinate> queue = new LinkedList<>();
    boolean start = image.get(x).get(y);
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    queue.offer(new Cordinate(x,y));
    while(!queue.isEmpty()) {
      Queue<Cordinate> temp = new LinkedList<>();
      while(!queue.isEmpty()) {
        Cordinate cur = queue.poll();
        visited.add(cur.x + "," + cur.y);
        image.get(cur.x).set(cur.y, !start);
        for (int[] dir: dirs) {
          Cordinate next = new Cordinate(cur.x + dir[0], cur.y + dir[1]);
          if (next.x<0 || next.x>=image.size() || next.y<0 || next.y>=image.get(next.x).size() || visited.contains(next.x + "," + next.y) || image.get(next.x).get(next.y) != start) {
            continue;
          }
          temp.offer(next);
        }
      }
      queue = temp;
    }
    return;
  }
  @EpiTest(testDataFile = "painting.tsv")
  public static List<List<Integer>> flipColorWrapper(TimedExecutor executor,
                                                     int x, int y,
                                                     List<List<Integer>> image)
      throws Exception {
    List<List<Boolean>> B = new ArrayList<>();
    for (int i = 0; i < image.size(); i++) {
      B.add(new ArrayList<>());
      for (int j = 0; j < image.get(i).size(); j++) {
        B.get(i).add(image.get(i).get(j) == 1);
      }
    }

    executor.run(() -> flipColor(x, y, B));

    image = new ArrayList<>();
    for (int i = 0; i < B.size(); i++) {
      image.add(new ArrayList<>());
      for (int j = 0; j < B.get(i).size(); j++) {
        image.get(i).add(B.get(i).get(j) ? 1 : 0);
      }
    }

    return image;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixConnectedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
