package com.flowerasny.cvrp.model;

public class Client extends City {
    public final int demand;

    public Client(String name, double longitude, double latitude, int demand) {
        super(name, longitude, latitude);
        this.demand = demand;
    }

}
