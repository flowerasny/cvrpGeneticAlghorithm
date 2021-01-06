package com.flowerasny.cvrp.model;

abstract public class City {
    public final String name;
    public final double longitude;
    public final double latitude;

    City(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return name;
    }
}