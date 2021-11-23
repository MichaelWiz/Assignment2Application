package com.example.assignment2application;

public class HealthcareCentre {
    private String centerName;
    private String address;

    public HealthcareCentre(String centerName, String address) {
        this.centerName = centerName;
        this.address = address;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return centerName;
    }
}
