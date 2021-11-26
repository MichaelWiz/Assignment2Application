package com.example.assignment2application;

import java.io.Serializable;

public class Batch{
    private String batchNo;
    private String expiryDate;
    private long quantityAvailable;
    private long quantityAdministered;
    private String centreName;

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    private String vaccineID;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public long getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public long getQuantityAdministered() {
        return quantityAdministered;
    }

    public void setQuantityAdministered(long quantityAdministered) {
        this.quantityAdministered = quantityAdministered;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    public Batch(){

    }

    public Batch(String batchNo,String centreName,String expiryDate,String vaccineID
    ,long quantityAdministered,long quantityAvailable){
        setBatchNo(batchNo);
        setExpiryDate(expiryDate);
        setCentreName(centreName);
        setQuantityAdministered(quantityAdministered);
        this.quantityAvailable = quantityAvailable;
        setVaccineID(vaccineID);

    }

    public Batch(String batchNo, String expiryDate, int quantityAvailable, String vaccineID,String centreName) {
        this.batchNo = batchNo;
        this.expiryDate = expiryDate;
        this.quantityAvailable = quantityAvailable;
        setCentreName(centreName);
        setVaccineID(vaccineID);
    }

}
