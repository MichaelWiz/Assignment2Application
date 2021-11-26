package com.example.assignment2application;

public class Vaccination {
    String vaccinationID, appointmentDate, status, remarks, batchNo, username, centre, vaccineType;
    public Vaccination(){

    }

    public Vaccination(String vaccinationID, String appointmentDate, String status, String remarks, String batchNo, String username, String centre, String vaccineType) {
        this.vaccinationID = vaccinationID;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.remarks = remarks;
        this.batchNo = batchNo;
        this.username = username;
        this.centre = centre;
        this.vaccineType = vaccineType;
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public String getVaccinationID() {
        return vaccinationID;
    }

    public void setVaccinationID(String vaccinationID) {
        this.vaccinationID = vaccinationID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
