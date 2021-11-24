package com.example.assignment2application;

import java.io.Serializable;

public class Vaccine{
    static int genVaccineID = 1;
    private String vaccineID;
    private String vaccineName;
    private String manufacturer;

    public Vaccine(String vaccineID, String vaccineName, String manufacturer) {
        this.vaccineID = vaccineID;
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
