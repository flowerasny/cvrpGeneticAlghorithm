package com.flowerasny.cvrp.model;

import java.util.ArrayList;
import java.util.List;

import static com.flowerasny.cvrp.utils.DistanceCalculator.distFrom;

public class Vehicle {
    public final String name;
    private final int startOpacity;
    public final List<City> path = new ArrayList<>();

    public Vehicle(String name, int startOpacity, StartingPoint startingPoint) {
        this.name = name;
        this.startOpacity = startOpacity;
        path.add(startingPoint);
    }

    private Vehicle(String name, int startOpacity, List<City> path) {
        this.name = name;
        this.startOpacity = startOpacity;
        this.path.addAll(path);
    }

    boolean hasClient(Client client) {
        return path.contains(client);
    }

    public boolean canGoToClient(Client client) {
        return (startOpacity - getUsedOpacity() - client.demand) >= 0;
    }

    public void goToClient(Client client) {
        path.add(client);
    }

    public void comeBack() {
        path.add(path.get(0));
    }

    private int getUsedOpacity() {
        return path.stream()
                .filter(city -> city instanceof Client)
                .mapToInt(visitedClient -> ((Client) visitedClient).demand)
                .sum();

    }

    double getDistanceCoveredInKm() {
        double distanceCovered = 0;

        for (int currentCityIndex = 0, nextCityIndex = 1; nextCityIndex < path.size(); currentCityIndex++, nextCityIndex++) {
            City currentCity = path.get(currentCityIndex);
            City nextCity = path.get(nextCityIndex);
            distanceCovered += distFrom(currentCity.latitude, currentCity.longitude, nextCity.latitude, nextCity.longitude);
        }

        return distanceCovered;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", startOpacity=" + startOpacity +
                ", useOpacity=" + getUsedOpacity() +
                ", path=" + path +
                "}\n";
    }

    void reschedule(Client client1, Client client2) {
        int client1Index = path.indexOf(client1);
        int client2Index = path.indexOf(client2);

        path.set(client1Index, client2);
        path.set(client2Index, client1);
    }

    boolean canReplace(Client client1, Client client2) {
        return startOpacity - (getUsedOpacity() - client1.demand + client2.demand) >= 0;
    }

    void replace(Client client1, Client client2) {
        path.set(path.indexOf(client1), client2);
    }

    Vehicle copy() {
        return new Vehicle(name, startOpacity, path);
    }
}
