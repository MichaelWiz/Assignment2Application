package com.example.assignment2application;

public class Admin {
    private String adminID;
    private String adminName;
    private String adminCentre;
    private String adminUsername;
    private String adminPassword;
    private String adminEmail;
    static int staffID =1;
    static String COLLECTION_NAME = "Admins";
    public Admin(String adminName, String adminCentre, String adminUsername, String adminPassword, String adminEmail){
        this.adminID = String.format("A%04d",  staffID++);;
        this.adminName = adminName;
        this.adminCentre = adminCentre ;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.adminEmail= adminEmail;
    }
    public String getAdminID() {
        return adminID;
    }
    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
    public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public String getAdminCentre() {
        return adminCentre;
    }
    public void setAdminCentre(String adminCentre) {
        this.adminCentre = adminCentre;
    }
    public String getAdminUsername() {
        return adminUsername;
    }
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    public String getAdminEmail() {
        return adminEmail;
    }
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
}
