package com.example.assignment2application;

<<<<<<< HEAD
public class Vaccine {
    private String vaccineID;
    private String vaccineName;
    private String manufacturer;

    public Vaccine(String vaccineID, String vaccineName, String manufacturer) {
        this.vaccineID = vaccineID;
        this.vaccineName = vaccineName;
        this.manufacturer = manufacturer;
=======
import java.io.Serializable;

public class Vaccine{
    private String vaccineID;
    private String vaccineName;
    private String manufacturer;
    static int vaccineNum;

    public Vaccine(){

    }
    public Vaccine (String vaccineID,String vaccineName,String manufacturer){
        setVaccineID(vaccineID);
        setVaccineName(vaccineName);
        setManufacturer(manufacturer);
    }

    public Vaccine(String vaccineName, String manufacturer) {
        setVaccineID(String.format("v"+(++vaccineNum)));
        setVaccineName(vaccineName);
        setManufacturer(manufacturer);
>>>>>>> origin/weeyuen
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
