package com.example.assignment2application;
import java.io.Serializable;

public class Vaccine{
    public String vaccineID;
    public String vaccineName;
    public String manufacturer;
    static int vaccineNum;

    public static int getVaccineNum() {
        return vaccineNum;
    }

    public static void setVaccineNum(int vaccineNum) {
        Vaccine.vaccineNum = vaccineNum;
    }

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
