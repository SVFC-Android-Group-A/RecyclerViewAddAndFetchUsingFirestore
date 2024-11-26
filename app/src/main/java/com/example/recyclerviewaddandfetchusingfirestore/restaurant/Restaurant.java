package com.example.recyclerviewaddandfetchusingfirestore.restaurant;

public class Restaurant {  // Renamed from 'Restaurants' to 'Restaurant'
    private String name;
    private String type;
    private String location;

    //Getter and Setter Model for Restaurant
    public Restaurant(String name, String type, String location) {  // Constructor accepts name, type, and location
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public Restaurant() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
