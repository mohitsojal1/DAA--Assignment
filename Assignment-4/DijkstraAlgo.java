import java.util.*;

public class DijkstraAlgo {

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Result {
        int distance;
        List<Integer> path;

        Result(int distance, List<Integer> path) {
            this.distance = distance;
            this.path = path;
        }
    }

    static class TrafficNetwork {
        private final int vertices;
        private final List<List<Edge>> graph;
        private final Set<Integer> hospitals = new HashSet<>();

        TrafficNetwork(int vertices) {
            this.vertices = vertices;
            graph = new ArrayList<>();

            for (int i = 0; i < vertices; i++) {
                graph.add(new ArrayList<>());
            }
        }

        // Add a bidirectional road
        void addEdge(int u, int v, int weight) {
            if (weight < 0) {
                throw new IllegalArgumentException(
                    "Road travel time cannot be negative."
                );
            }

            graph.get(u).add(new Edge(v, weight));
            graph.get(v).add(new Edge(u, weight));
        }

        // Register a hospital
        void addHospital(int node) {
            hospitals.add(node);
        }

        // Update the travel time of an existing road
        void updateEdgeWeight(int u, int v, int newWeight) {
            if (newWeight < 0) {
                throw new IllegalArgumentException(
                    "Road travel time cannot be negative."
                );
            }

            boolean updatedUV = false;
            boolean updatedVU = false;

            for (Edge edge : graph.get(u)) {
                if (edge.to == v) {
                    edge.weight = newWeight;
                    updatedUV = true;
                }
            }

            for (Edge edge : graph.get(v)) {
                if (edge.to == u) {
                    edge.weight = newWeight;
                    updatedVU = true;
                }
            }

            if (!updatedUV || !updatedVU) {
                throw new IllegalArgumentException(
                    "The specified road does not exist."
                );
            }
        }

        // Find the nearest reachable hospital
        Result shortestPathToNearestHospital(int source) {
            int[] dist = new int[vertices];
            int[] parent = new int[vertices];

            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(parent, -1);

            PriorityQueue<int[]> pq = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[0])
            );

            dist[source] = 0;
            pq.offer(new int[]{0, source});

            while (!pq.isEmpty()) {
                int[] current = pq.poll();

                int currentDist = current[0];
                int u = current[1];

                // Ignore outdated priority-queue entries
                if (currentDist != dist[u]) {
                    continue;
                }

                // The first hospital reached has minimum travel time
                if (hospitals.contains(u)) {
                    List<Integer> path = new ArrayList<>();

                    for (int node = u; node != -1;
                         node = parent[node]) {
                        path.add(node);
                    }

                    Collections.reverse(path);

                    return new Result(dist[u], path);
                }

                // Relax adjacent edges
                for (Edge edge : graph.get(u)) {
                    int v = edge.to;
                    int weight = edge.weight;

                    if (dist[u] != Integer.MAX_VALUE
                            && (long) dist[u] + weight < dist[v]) {

                        dist[v] = dist[u] + weight;
                        parent[v] = u;

                        pq.offer(new int[]{dist[v], v});
                    }
                }
            }

            // No hospital is reachable
            return new Result(-1, new ArrayList<>());
        }

        // Display the result
        void displayResult(String message, int source) {
            Result result = shortestPathToNearestHospital(source);

            System.out.println("\n" + message);

            if (result.distance == -1) {
                System.out.println("No hospital reachable.");
            } else {
                System.out.println(
                    "Shortest travel time: " + result.distance
                );

                System.out.print("Path: ");

                for (int i = 0; i < result.path.size(); i++) {
                    System.out.print(result.path.get(i));

                    if (i < result.path.size() - 1) {
                        System.out.print(" -> ");
                    }
                }

                System.out.println();
            }
        }

        // Check whether a vertex number is valid
        boolean isValidVertex(int node) {
            return node >= 0 && node < vertices;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print(
                "Enter number of vertices (intersections): "
            );
            int vertices = sc.nextInt();

            if (vertices <= 0) {
                System.out.println(
                    "Number of vertices must be positive."
                );
                return;
            }

            TrafficNetwork city = new TrafficNetwork(vertices);

            System.out.print("Enter number of edges (roads): ");
            int edges = sc.nextInt();

            if (edges < 0) {
                System.out.println(
                    "Number of edges cannot be negative."
                );
                return;
            }

            System.out.println(
                "Enter edges (source destination weight):"
            );

            for (int i = 0; i < edges; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int weight = sc.nextInt();

                if (!city.isValidVertex(u)
                        || !city.isValidVertex(v)) {
                    System.out.println(
                        "Error: Invalid vertex number."
                    );
                    return;
                }

                city.addEdge(u, v, weight);
            }

            System.out.print("Enter number of hospitals: ");
            int numberOfHospitals = sc.nextInt();

            if (numberOfHospitals < 0
                    || numberOfHospitals > vertices) {
                System.out.println(
                    "Invalid number of hospitals."
                );
                return;
            }

            System.out.println("Enter hospital locations:");

            for (int i = 0; i < numberOfHospitals; i++) {
                int hospital = sc.nextInt();

                if (!city.isValidVertex(hospital)) {
                    System.out.println(
                        "Error: Invalid hospital location."
                    );
                    return;
                }

                city.addHospital(hospital);
            }

            System.out.print(
                "Enter ambulance starting location: "
            );
            int source = sc.nextInt();

            if (!city.isValidVertex(source)) {
                System.out.println(
                    "Error: Invalid ambulance location."
                );
                return;
            }

            city.displayResult(
                "Initial shortest route:", source
            );

            System.out.println(
                "\nEnter edge to update (u v newWeight):"
            );

            int u = sc.nextInt();
            int v = sc.nextInt();
            int newWeight = sc.nextInt();

            if (!city.isValidVertex(u)
                    || !city.isValidVertex(v)) {
                System.out.println(
                    "Error: Invalid vertex number."
                );
                return;
            }

            city.updateEdgeWeight(u, v, newWeight);

            city.displayResult(
                "After traffic update:", source
            );

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
