import java.util.*;

public class TSPBranchAndBoundAssignment8 {
    private static final int N = 4;
    private static final int INF = 1_000_000_000;
    private static final String[] CITY = {"A", "B", "C", "D"};
    private static final int[][] COST = {
        {INF, 10, 15, 20},
        {10, INF, 35, 25},
        {15, 35, INF, 30},
        {20, 25, 30, INF}
    };

    private static class Node {
        int[][] matrix;
        int bound;
        int level;
        int current;
        int pathCost;
        List<Integer> path;

        Node(int[][] matrix, int bound, int level, int current, int pathCost, List<Integer> path) {
            this.matrix = matrix;
            this.bound = bound;
            this.level = level;
            this.current = current;
            this.pathCost = pathCost;
            this.path = path;
        }
    }

    private static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }

    // Reduces every row and column; returns the added lower-bound cost.
    private static int reduceMatrix(int[][] matrix) {
        int reduction = 0;

        for (int i = 0; i < N; i++) {
            int min = INF;
            for (int j = 0; j < N; j++) min = Math.min(min, matrix[i][j]);
            if (min != INF && min > 0) {
                reduction += min;
                for (int j = 0; j < N; j++) {
                    if (matrix[i][j] != INF) matrix[i][j] -= min;
                }
            }
        }

        for (int j = 0; j < N; j++) {
            int min = INF;
            for (int i = 0; i < N; i++) min = Math.min(min, matrix[i][j]);
            if (min != INF && min > 0) {
                reduction += min;
                for (int i = 0; i < N; i++) {
                    if (matrix[i][j] != INF) matrix[i][j] -= min;
                }
            }
        }
      return reduction;
    }

    private static boolean contains(List<Integer> path, int city) {
        return path.contains(city);
    }

    private static String pathKey(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int city : path) sb.append(city);
        return sb.toString();
    }

    private static String formatPath(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(CITY[path.get(i)]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("ASSIGNMENT NO. 8: TSP USING LC BRANCH AND BOUND");
        System.out.println("------------------------------------------------");
        System.out.println("Cities: A, B, C, D");
        System.out.println("Cost matrix (INF on diagonal):");
        System.out.println("       A    B    C    D");
        for (int i = 0; i < N; i++) {
            System.out.printf("%s  ", CITY[i]);
            for (int j = 0; j < N; j++) {
                if (COST[i][j] == INF) System.out.printf("%4s ", "INF");
                else System.out.printf("%4d ", COST[i][j]);
            }
            System.out.println();
        }

        int[][] rootMatrix = copyMatrix(COST);
        int rootBound = reduceMatrix(rootMatrix);
        List<Integer> rootPath = new ArrayList<>();
        rootPath.add(0);
        Node root = new Node(rootMatrix, rootBound, 0, 0, 0, rootPath);

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
            int cmp = Integer.compare(a.bound, b.bound);
            if (cmp != 0) return cmp;
            return pathKey(a.path).compareTo(pathKey(b.path));
        });
        pq.add(root);

        int bestCost = INF;
        List<Integer> bestPath = null;
        int expanded = 0;
        int pruned = 0;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.bound >= bestCost) {
                pruned++;
                continue;
            }
            expanded++;

            if (node.level == N - 1) {
                int completedCost = node.pathCost + COST[node.current][0];
                if (completedCost < bestCost) {
                    bestCost = completedCost;
                    bestPath = new ArrayList<>(node.path);
                    bestPath.add(0);
                }
                continue;
            }

            int u = node.current;
            for (int v = 0; v < N; v++) {
                if (contains(node.path, v) || COST[u][v] == INF) continue;

                int[][] childMatrix = copyMatrix(node.matrix);
                for (int k = 0; k < N; k++) {
                    childMatrix[u][k] = INF; // no more departures from u
                    childMatrix[k][v] = INF; // v has its incoming edge
                }
                // Prevent a premature return to the start. Keep the return edge
                // available when v is the final city so its cost enters the bound.
                if (node.level + 1 < N - 1) childMatrix[v][0] = INF;

                int reductionCost = reduceMatrix(childMatrix);
                int childBound = node.bound + node.matrix[u][v] + reductionCost;
                if (childBound >= bestCost) {
                    pruned++;
                    continue;
                }

                List<Integer> childPath = new ArrayList<>(node.path);
                childPath.add(v);
                Node child = new Node(childMatrix, childBound, node.level + 1, v, node.pathCost + COST[u][v], childPath);
                pq.add(child);
            }
        }

        System.out.println();
        System.out.println("Optimal tour : " + (bestPath == null ? "No tour found" : formatPath(bestPath)));
        System.out.println("Minimum cost : " + (bestPath == null ? "N/A" : bestCost));
        System.out.println("Expanded nodes: " + expanded);
        System.out.println("Pruned nodes  : " + pruned);
        System.out.println("Result        : Optimal tour found using LC Branch and Bound.");
    }
}
