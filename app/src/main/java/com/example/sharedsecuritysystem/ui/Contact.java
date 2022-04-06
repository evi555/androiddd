package com.example.sharedsecuritysystem.ui;

import java.io.Serializable;

public class Contact implements Serializable {

    private String contactName, contactEmail, contactPhone;

    public Contact(){
    }

    public Contact(String contactName, String contactEmail, String contactPhone){
        this.contactName=contactName;
        this.contactEmail=contactEmail;
        this.contactPhone=contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
