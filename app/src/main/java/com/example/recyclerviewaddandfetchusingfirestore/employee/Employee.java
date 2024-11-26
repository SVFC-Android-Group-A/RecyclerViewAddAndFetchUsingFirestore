package com.example.recyclerviewaddandfetchusingfirestore.employee;

public class Employee {
    private String name;
    private String department;
    private String email;

    //Getter and Setter Model for Employee
    public Employee(String name, String department, String email) {
        this.name = name;
        this.department = department;
        this.email = email;
    }

    // Default constructor for Firestore
    public Employee() {}

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
