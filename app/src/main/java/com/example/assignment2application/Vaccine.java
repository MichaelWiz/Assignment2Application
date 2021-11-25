package com.example.assignment2application;

import java.io.Serializable;

public class Vaccine{
    private String vaccineID;
    private String vaccineName;
    private String manufacturer;
    static int vaccineNum;

    public Vaccine(String vaccineName, String manufacturer) {
        setVaccineID(String.format("v"+(++vaccineNum)));
        this.vaccineName = vaccineName;
        this.manufacturer = manufacturer;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
