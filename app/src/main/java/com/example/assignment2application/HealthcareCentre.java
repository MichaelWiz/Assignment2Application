package com.example.assignment2application;

public class HealthcareCentre {
    private String centreName;
    private String address;

    public HealthcareCentre(String centerName, String address) {
        this.centreName = centerName;
        this.address = address;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centerName) {
        this.centreName = centerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return centreName;
    }
}
