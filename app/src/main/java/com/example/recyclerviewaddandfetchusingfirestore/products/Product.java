package com.example.recyclerviewaddandfetchusingfirestore.products;

public class Product {
    private int price;
    private String name;
    private String category;

    //Getter and Setter Model for Product
    public Product() {}

    // Parameterized constructor
    public Product(int price, String name, String category) {
        this.price = price;
        this.name = name;
        this.category = category;
    }

    // Getter and Setter methods
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
