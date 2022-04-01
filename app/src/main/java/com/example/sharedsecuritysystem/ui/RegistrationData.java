package com.example.sharedsecuritysystem.ui;

public class RegistrationData {

    private String Name, Email, PhoneNum;

    public RegistrationData(){
    }

    public RegistrationData(String Name, String Email, String PhoneNum){
        this.Name=Name;
        this.Email=Email;
        this.PhoneNum=PhoneNum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }
}
