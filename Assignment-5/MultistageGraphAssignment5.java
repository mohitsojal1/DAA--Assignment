import java.util.*;

public class MultistageGraphAssignment5 {

    static class Edge {
        int destination;
        int cost;

        Edge(int destination, int cost) {
            this.destination = destination;
            this.cost = cost;
        }
    }

    static class Result {
        int minimumCost;
        List<Integer> path;

        Result(int minimumCost, List<Integer> path) {
            this.minimumCost = minimumCost;
            this.path = path;
        }
    }

    static class MultistageGraph {
        private final int vertices;
        private final int stages;
        private final int[] stage;
        private final List<List<Edge>> graph;

        MultistageGraph(int vertices, int stages) {
            this.vertices = vertices;
            this.stages = stages;
            this.stage = new int[vertices];
            this.graph = new ArrayList<>();

            for (int i = 0; i < vertices; i++) {
                graph.add(new ArrayList<>());
            }
        }

        void setStage(int vertex, int stageNumber) {
            if (vertex < 0 || vertex >= vertices
                    || stageNumber < 1 || stageNumber > stages) {
                throw new IllegalArgumentException(
                    "Invalid vertex or stage number."
                );
            }

            stage[vertex] = stageNumber;
        }

        void addEdge(int source, int destination, int cost) {
            validateVertex(source);
            validateVertex(destination);

            if (cost < 0) {
                throw new IllegalArgumentException(
                    "Edge cost cannot be negative."
                );
            }

            if (stage[destination] != stage[source] + 1) {
                throw new IllegalArgumentException(
                    "Edges must connect consecutive stages."
                );
            }

            graph.get(source).add(new Edge(destination, cost));
        }

        void updateEdge(int source, int destination, int newCost) {
            validateVertex(source);
            validateVertex(destination);

            if (newCost < 0) {
                throw new IllegalArgumentException(
                    "Edge cost cannot be negative."
                );
            }

            for (Edge edge : graph.get(source)) {
                if (edge.destination == destination) {
                    edge.cost = newCost;
                    return;
                }
            }

            throw new IllegalArgumentException(
                "The specified directed edge does not exist."
            );
        }

        Result shortestPath(int source, int destination) {
            validateVertex(source);
            validateVertex(destination);

            if (stage[source] != 1
                    || stage[destination] != stages) {
                throw new IllegalArgumentException(
                    "Source must be in stage 1 and destination "
                    + "must be in the final stage."
                );
            }

            int[] cost = new int[vertices];
            int[] next = new int[vertices];

            Arrays.fill(cost, Integer.MAX_VALUE);
            Arrays.fill(next, -1);

            cost[destination] = 0;

            // Backward Dynamic Programming
            for (int currentStage = stages - 1;
                    currentStage >= 1; currentStage--) {

                for (int u = 0; u < vertices; u++) {
                    if (stage[u] != currentStage) {
                        continue;
                    }

                    for (Edge edge : graph.get(u)) {
                        int v = edge.destination;

                        if (cost[v] == Integer.MAX_VALUE) {
                            continue;
                        }

                        long newCost = (long) edge.cost + cost[v];

                        if (newCost < cost[u]) {
                            cost[u] = (int) newCost;
                            next[u] = v;
                        }
                    }
                }
            }

            if (cost[source] == Integer.MAX_VALUE) {
                return new Result(-1, new ArrayList<>());
            }

            List<Integer> path = new ArrayList<>();
            int current = source;

            while (current != -1) {
                path.add(current);

                if (current == destination) {
                    break;
                }

                current = next[current];
            }

            if (path.get(path.size() - 1) != destination) {
                return new Result(-1, new ArrayList<>());
            }

            return new Result(cost[source], path);
        }

        void displayResult(int source, int destination) {
            Result result = shortestPath(source, destination);

            if (result.minimumCost == -1) {
                System.out.println("No valid path exists.");
                return;
            }

            System.out.println(
                "Minimum delivery cost: " + result.minimumCost
            );

            System.out.print("Optimal path: ");

            for (int i = 0; i < result.path.size(); i++) {
                System.out.print(result.path.get(i));

                if (i < result.path.size() - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
        }

        void processBatch(List<Integer> sources, int destination) {
            System.out.println("\nBatch Delivery Results:");

            for (int i = 0; i < sources.size(); i++) {
                System.out.println(
                    "\nDelivery Request " + (i + 1) + ":"
                );

                displayResult(sources.get(i), destination);
            }
        }

        private void validateVertex(int vertex) {
            if (vertex < 0 || vertex >= vertices) {
                throw new IllegalArgumentException(
                    "Invalid vertex: " + vertex
                );
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter number of vertices: ");
            int vertices = sc.nextInt();

            System.out.print("Enter number of stages: ");
            int stages = sc.nextInt();

            if (vertices <= 0 || stages < 2 || stages > vertices) {
                System.out.println("Invalid graph configuration.");
                return;
            }

            MultistageGraph graph =
                new MultistageGraph(vertices, stages);

            System.out.println(
                "Enter the stage number for each vertex (0 to "
                + (vertices - 1) + "):"
            );

            for (int i = 0; i < vertices; i++) {
                System.out.print("Stage of vertex " + i + ": ");
                graph.setStage(i, sc.nextInt());
            }

            System.out.print("Enter number of directed edges: ");
            int edges = sc.nextInt();

            if (edges < 0) {
                System.out.println("Number of edges cannot be negative.");
                return;
            }

            System.out.println(
                "Enter edges (source destination cost):"
            );

            for (int i = 0; i < edges; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int cost = sc.nextInt();

                graph.addEdge(u, v, cost);
            }

            System.out.print("Enter source vertex: ");
            int source = sc.nextInt();

            System.out.print("Enter destination vertex: ");
            int destination = sc.nextInt();

            System.out.println("\nInitial Optimal Delivery Route:");
            graph.displayResult(source, destination);

            System.out.print(
                "\nEnter edge to update (source destination newCost): "
            );

            int u = sc.nextInt();
            int v = sc.nextInt();
            int newCost = sc.nextInt();

            graph.updateEdge(u, v, newCost);

            System.out.println("\nAfter Cost Update:");
            graph.displayResult(source, destination);

            System.out.print(
                "\nEnter number of additional delivery requests: "
            );

            int requests = sc.nextInt();

            if (requests < 0) {
                System.out.println(
                    "Number of requests cannot be negative."
                );
                return;
            }

            List<Integer> sources = new ArrayList<>();

            for (int i = 0; i < requests; i++) {
                System.out.print(
                    "Enter source for request " + (i + 1) + ": "
                );

                sources.add(sc.nextInt());
            }

            graph.processBatch(sources, destination);

        } catch (InputMismatchException e) {
            System.out.println(
                "Error: Please enter valid integer values."
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
