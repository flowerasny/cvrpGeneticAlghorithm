package com.flowerasny.cvrp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Individual {
    public final List<Vehicle> vehicles;

    public Individual(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public double getDistance() {
        return vehicles.stream()
                .mapToDouble(Vehicle::getDistanceCoveredInKm)
                .sum();
    }

    @Override
    public String toString() {
        return "Vehicles = " + vehicles + " Distance = " + getDistance() + "\n";
    }

    private Client getClientWithIndex(int index) {
        return getAllClients().get(index);
    }

    public List<Client> getAllClients() {
        return getAllCities().stream()
                .filter(city -> city instanceof Client)
                .map(city -> (Client) city)
                .collect(Collectors.toList());
    }

    private List<City> getAllCities() {
        return vehicles.stream()
                .map(vehicle -> vehicle.path)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public boolean replace(int client1Index, int client2Index) {

        Client client2 = getClientWithIndex(client2Index);
        return replace(client1Index, client2);
    }

    public boolean replace(int clientIndex, Client client2) {

        Client client1 = getClientWithIndex(clientIndex);

        Vehicle vehicleWithClient1 = vehicles.stream()
                .filter(vehicle -> vehicle.hasClient(client1))
                .findFirst()
                .get();
        Vehicle vehicleWithClient2 = vehicles.stream()
                .filter(vehicle -> vehicle.hasClient(client2))
                .findFirst()
                .get();

        if (vehicleWithClient1 == vehicleWithClient2) {
            vehicleWithClient1.reschedule(client1, client2);
            return true;
        } else {
            boolean isAbleToPutClient1 = vehicleWithClient1.canReplace(client1, client2);
            boolean isAbleToPutClient2 = vehicleWithClient2.canReplace(client2, client1);

            if (isAbleToPutClient1 && isAbleToPutClient2) {
                vehicleWithClient1.replace(client1, client2);
                vehicleWithClient2.replace(client2, client1);
                return true;
            } else {
                return false;
            }
        }
    }

    public Individual copy() {
        List<Vehicle> vehiclesCopied = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            vehiclesCopied.add(vehicle.copy());
        }

        return new Individual(new ArrayList<>(vehiclesCopied));
    }
}
