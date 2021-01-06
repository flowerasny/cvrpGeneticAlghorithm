package com.flowerasny.cvrp;

import com.flowerasny.cvrp.data.StaticData;
import com.flowerasny.cvrp.model.Client;
import com.flowerasny.cvrp.model.Individual;
import com.flowerasny.cvrp.model.Vehicle;
import com.flowerasny.cvrp.utils.PathsDrawer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Cvrp {

    private static int noChangeSince = 0;

    private static Individual bestSolution;

    private static PathsDrawer pathsDrawer = new PathsDrawer();

    public static void main(String[] args) {

        List<Individual> basePopulation = drawBasePopulation();

        while (!stopCriteriaMet()) {
            List<Individual> temporaryPopulation = reproduction(basePopulation);
            List<Individual> descendantPopulation = makeGeneticOperations(temporaryPopulation);

            basePopulation = succession(temporaryPopulation, descendantPopulation);

        }

        printBestSolution(bestSolution);
    }

    private static List<Individual> drawBasePopulation() {
        List<Individual> result = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            List<Client> clients = new ArrayList<>(StaticData.clients);

            clients.sort((client1, client2) -> client2.demand - client1.demand);
            List<Vehicle> vehicles = StaticData.getVehicles();

            while (!clients.isEmpty()) {
                Client client = clients.get(0);

                int vehicleNumber;

                Vehicle vehicle;

                do {
                    vehicleNumber = ThreadLocalRandom.current().nextInt(vehicles.size());
                    vehicle = vehicles.get(vehicleNumber);
                } while (!vehicle.canGoToClient(client));

                vehicle.goToClient(client);
                clients.remove(0);
            }

            for (Vehicle vehicle : vehicles) {
                vehicle.comeBack();
            }

            result.add(new Individual(vehicles));

        }

        return result;
    }

    private static List<Individual> reproduction(List<Individual> individuals) {
        individuals.sort(Comparator.comparingDouble(Individual::getDistance));
        return individuals.subList(0, 20);
    }

    private static List<Individual> makeGeneticOperations(List<Individual> temporaryPopulation) {
        return mutation(crossing(temporaryPopulation));
    }

    private static List<Individual> crossing(List<Individual> temporaryPopulation) {

        Individual individual1 = temporaryPopulation.get(0);
        Individual individual2 = temporaryPopulation.get(1);

        List<Individual> result = new ArrayList<>();
        int allClients = individual1.getAllClients().size();
        int bound = allClients - 1;
        int firstCrossingPoint = ThreadLocalRandom.current().nextInt(bound);
        int origin = firstCrossingPoint + 1;
        int secondCrossingPoint = ThreadLocalRandom.current().nextInt(origin, allClients);

        List<Individual> crossed = cross(individual1, individual2, firstCrossingPoint, secondCrossingPoint);

        for (int i = 0; i < 5; i++) {
            result.addAll(crossed);
        }

        return result;
    }

    private static List<Individual> cross(Individual individual1, Individual individual2, int start, int end) {

        Individual child1 = individual1.copy();
        Individual child2 = individual2.copy();

        for (int index = start; index < end; index++) {
            child1.replace(index, individual2.getAllClients().get(index));
            child2.replace(index, individual1.getAllClients().get(index));
        }

        return Arrays.asList(child1, child2);
    }

    private static List<Individual> mutation(List<Individual> temporaryPopulation) {
        return temporaryPopulation.stream()
                .map(Cvrp::mutate)
                .collect(Collectors.toList());
    }

    private static Individual mutate(Individual individual) {
        Individual newIndividual = individual.copy();
        int firstItemIndex;
        int secondItemIndex;
        int maxIterations = 10;
        do {
            maxIterations--;
            int allClients = newIndividual.getAllClients().size();
            int bound = allClients - 1;
            firstItemIndex = ThreadLocalRandom.current().nextInt(bound);
            int origin = firstItemIndex + 1;
            secondItemIndex = ThreadLocalRandom.current().nextInt(origin, allClients);
        } while (!individual.replace(firstItemIndex, secondItemIndex) && maxIterations > 0);

        return newIndividual;

    }

    private static List<Individual> succession(List<Individual> temporaryPopulation, List<Individual> descendantPopulation) {
        temporaryPopulation.sort(Comparator.comparingDouble(Individual::getDistance));
        descendantPopulation.sort(Comparator.comparingDouble(Individual::getDistance));

        List<Individual> newBasePopulation = new ArrayList<>();
        newBasePopulation.addAll(temporaryPopulation.subList(0, 10));
        newBasePopulation.addAll(descendantPopulation.subList(0, 10));

        newBasePopulation.sort(Comparator.comparingDouble(Individual::getDistance));

        Individual bestSolutionInIteration = newBasePopulation.get(0);

        if (bestSolution == null || bestSolutionInIteration.getDistance() < bestSolution.getDistance()) {
            bestSolution = bestSolutionInIteration;
            noChangeSince = 0;
        } else {
            noChangeSince++;
        }

        System.out.println("BEST SOLUTION    -->      " + bestSolution.getDistance() + "           " + noChangeSince);

        return newBasePopulation;
    }

    private static void printBestSolution(Individual bestSolution) {

        System.out.println("<--FINISHED WITH-->");
        System.out.println(bestSolution);

        for (Vehicle vehicle : bestSolution.vehicles) {
            Color color = Color.white;
            if (vehicle.name.equals("Star")) {
                color = Color.CYAN;
            }
            if (vehicle.name.equals("Mercedes")) {
                color = Color.green;
            }
            if (vehicle.name.equals("Volvo")) {
                color = Color.magenta;
            }
            if (vehicle.name.equals("Daf")) {
                color = Color.orange;
            }
            if (vehicle.name.equals("Iveco")) {
                color = Color.red;
            }
            pathsDrawer.drawPath(vehicle.path, color);

        }
        pathsDrawer.saveImage();
    }

    private static boolean stopCriteriaMet() {
        return noChangeSince >= 10000;
    }
}
