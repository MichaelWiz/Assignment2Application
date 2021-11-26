package com.example.assignment2application;

public class Patient {

    static String COLLECTION_NAME = "Patients";

    public String patientName;
    public String patientID;
    public String patientUsername;
    public String patientPassword;
    public String patientEmail;

    public Patient() {
    }

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public static void setCollectionName(String collectionName) {
        COLLECTION_NAME = collectionName;
    }

    public Patient(String patientName, String patientID, String patientUsername, String patientPassword, String patientEmail){
        this.patientName = patientName;
        this.patientID = patientID;
        this.patientUsername = patientUsername;
        this.patientPassword = patientPassword;
        this.patientEmail = patientEmail;

    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }


    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
