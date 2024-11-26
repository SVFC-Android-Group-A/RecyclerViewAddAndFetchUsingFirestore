package com.example.recyclerviewaddandfetchusingfirestore.university;

public class University {
    private String name;
    private String type;
    private String website;

    // Fixed the errors
    public University(String name, String type, String website) {
        this.name = name;
        this.type = type;
        this.website = website;
    }

    public University() {}

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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
