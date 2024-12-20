import java.io.IOException;
import java.util.List;
public class Main {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\matus\\IdeaProjects\\OSSP\\Matica_KE_(0460).txt"; // Specify the input file name

        try {
            TravelingSalesman.parseDistanceMatrix(fileName);
        } catch (IOException e) {
            System.err.println("Error reading the distance matrix: " + e.getMessage());
            return;
        }
        List<Integer> initialRoute = TravelingSalesman.constructRoute();
        System.out.println("Initial Route: " + initialRoute);
        System.out.println("Initial Distance: " + TravelingSalesman.calculateRouteDistance(initialRoute));

        SimulatedAnnealing.init(initialRoute, TravelingSalesman.distances);
        List<Integer> optimizedRoute = SimulatedAnnealing.run(10000, 40, 50);
        System.out.println("Optimized Route: " + optimizedRoute);
        System.out.println("Optimized Distance: " + SimulatedAnnealing.calculateRouteDistance(optimizedRoute));
    }
}
