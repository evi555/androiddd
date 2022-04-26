package com.example.sharedsecuritysystem.Response;

import java.io.Serializable;

public class ContactResponse implements Serializable {
    private String name;
    private String contactEmail;
    private String phone;
    private String uid;

    /*public ContactResponse(String name, String email, String phone,String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.uid = uid;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return contactEmail;
    }

    public void setEmail(String email) {
        this.contactEmail = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
