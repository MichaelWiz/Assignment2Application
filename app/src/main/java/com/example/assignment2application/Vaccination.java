package com.example.assignment2application;

public class Vaccination {
    private String VaccinationID;
    private String VaccineType;
    private String Centre;
    private String VaccineBatch;
    private String AppointmentDate;
    private String Status;
    private String Remark;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getVaccinationID() {
        return VaccinationID;
    }

    public void setVaccinationID(String vaccinationID) {
        VaccinationID = vaccinationID;
    }

    public String getVaccineType() {
        return VaccineType;
    }

    public void setVaccineType(String vaccineType) {
        VaccineType = vaccineType;
    }

    public String getCentre() {
        return Centre;
    }

    public void setCentre(String centre) {
        Centre = centre;
    }

    public String getVaccineBatch() {
        return VaccineBatch;
    }

    public void setVaccineBatch(String vaccineBatch) {
        VaccineBatch = vaccineBatch;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }
}
