package com.example.assignment2application;

import java.io.Serializable;
public class Batch{
    public String batchNo;
    public String expiryDate;
    public long quantityAvailable;
    public long quantityAdministered;
    public String centreName;
    public String vaccineID;
    public Batch() {
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    public long getQuantityAdministered() {
        return quantityAdministered;
    }

    public void setQuantityAdministered(long quantityAdministered) {
        this.quantityAdministered = quantityAdministered;
    }

    public long getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
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

    public Batch(String batchNo, String expiryDate, long quantityAvailable, String vaccineID,String centreName) {
        this.batchNo = batchNo;
        this.expiryDate = expiryDate;
        this.quantityAvailable = quantityAvailable;
        setCentreName(centreName);
        setVaccineID(vaccineID);
    }

}