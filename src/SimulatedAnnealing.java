import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private static List<Integer> initialRoute;
    private static int[][] distances;
    private static double step = 0.99;
    private static double tmin = 0.000000000001;

    public static void init(List<Integer> route, int[][] dist) {
        initialRoute = route;
        distances = dist;
    }
    public static List<Integer> run(int tmax, int u, int q) {
        List<Integer> currentRoute = new ArrayList<>(initialRoute);
        int currentDistance = calculateRouteDistance(currentRoute);
        List<Integer> bestRoute = new ArrayList<>(currentRoute);
        int bestDistance = currentDistance;

        double t = tmax;
        int w = 0;
        int r = 0;

        Random random = new Random();
        while(r < u) {
            List<Integer> neighbor = generateNeighbor(currentRoute);
            int neighborDistance = calculateRouteDistance(neighbor);
            w++;
            r++;
            if (w == q)
            {
                t *= step;
                w = 0;
            }

            if (neighborDistance < currentDistance) {
                currentRoute = neighbor;
                currentDistance = neighborDistance;
                r = 0;
                if (currentDistance < bestDistance) {
                    bestRoute = currentRoute;
                    bestDistance = currentDistance;
                }
            } else {
                double prob = Math.exp((neighborDistance - currentDistance) / t);
                if (random.nextDouble() < prob) {
                    currentRoute = neighbor;
                    currentDistance = neighborDistance;
                    r = 0;
                }
            }
            if (t < tmin) {
                break;
            }
        }
        return bestRoute;
    }

    private static List<Integer> generateNeighbor(List<Integer> route) {
        Random random = new Random();
        List<Integer> neighbor = new ArrayList<>(route);

        int i = random.nextInt(route.size() - 1) + 1;
        int j = random.nextInt(route.size() - 1) + 1;
        Collections.swap(neighbor, i, j);

        return neighbor;
    }

    public static int calculateRouteDistance(List<Integer> route) {
        int totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += distances[route.get(i)][route.get(i + 1)];
        }
        return totalDistance;
    }
}
