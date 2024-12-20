import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TravelingSalesman {
    public static int[][] distances;
    private static List<Integer> route;
    private static Set<Integer> unvisited;

    public static void parseDistanceMatrix(String fileName) throws IOException {
        List<int[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] tokens = line.trim().split("\\s+");
                int[] row = Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray();
                rows.add(row);
            }
        }
        int size = rows.size();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i] = rows.get(i);
        }
        distances = matrix;
    }

    // Construct the route based on the algorithm
    public static List<Integer> constructRoute() {
        route = new ArrayList<>();
        int n = distances.length;
        unvisited = new HashSet<>();
        for (int i = 0; i < n; i++) {
            unvisited.add(i);
        }

        int i1 = 0;
        unvisited.remove(i1);
        int i2 = findFarthestNode(i1);
        unvisited.remove(i2);
        int i3 = findFarthestNode(i2);
        unvisited.remove(i3);

        route.add(i1);
        route.add(i2);
        route.add(i3);
        route.add(i1);

        while (!unvisited.isEmpty()) {
            int nextNode = findClosestNode();
            unvisited.remove(nextNode);

            int bestPosition = -1;
            int minIncrease = Integer.MAX_VALUE;

            for (int i = 0; i < route.size() - 1; i++) {
                int increase = distances[route.get(i)][nextNode] + distances[nextNode][route.get(i + 1)] - distances[route.get(i)][route.get(i + 1)];
                if (increase < minIncrease) {
                    minIncrease = increase;
                    bestPosition = i;
                }
            }
            route.add(bestPosition + 1, nextNode);
        }

        return route;
    }

    private static int findFarthestNode(int node) {
        int farthestNode = -1;
        int maxDistance = -1;
        for (int candidate : unvisited) {
            if (distances[node][candidate] > maxDistance) {
                maxDistance = distances[node][candidate];
                farthestNode = candidate;
            }
        }
        return farthestNode;
    }

    private static int findClosestNode() {
        int closestNode = -1;
        int minDistance = Integer.MAX_VALUE;
        for (int candidate : unvisited) {
            int sumDistance = 0;
            for (int visited : route) {
                sumDistance += distances[candidate][visited];
            }
            if (sumDistance < minDistance) {
                minDistance = sumDistance;
                closestNode = candidate;
            }
        }
        return closestNode;
    }

    public static int calculateRouteDistance(List<Integer> route) {
        int totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += distances[route.get(i)][route.get(i + 1)];
        }
        return totalDistance;
    }
}
