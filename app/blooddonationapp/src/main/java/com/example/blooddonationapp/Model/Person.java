package com.example.blooddonationapp.Model;

public class Person {

    private int id;
    private String name;
    private String bloodGroup;
    private String phone;
    private String area;
    private String hospital;
    private String location;

    // 🔹 FULL constructor (BEST - use everywhere)
    public Person(int id, String name, String bloodGroup, String phone, String area, String hospital, String location) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.area = area;
        this.hospital = hospital;
        this.location = location;
    }

    // 🔹 Donor constructor
    public Person(int id, String name, String bloodGroup, String phone, String area) {
        this(id, name, bloodGroup, phone, area, "", "");
    }

    // 🔹 Request constructor
    public Person(int id, String name, String bloodGroup, String phone, String hospital, String location) {
        this(id, name, bloodGroup, phone, "", hospital, location);
    }

    // 🔹 Getters
    public int getId() { return id; }

    public String getName() { return name; }

    public String getBloodGroup() { return bloodGroup; }

    public String getPhone() { return phone; }

    public String getArea() { return area; }

    public String getHospital() { return hospital; }

    public String getLocation() { return location; }

    // 🔹 Setter
    public void setId(int id) { this.id = id; }
}