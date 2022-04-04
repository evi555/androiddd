package com.example.sharedsecuritysystem.ui;

public class RegistrationData {

    private String Name, Email, PhoneNum;
    private String SysID, own;

    public RegistrationData(){
    }

    public RegistrationData(String Name, String Email, String PhoneNum, String SysID, String own){
        this.Name=Name;
        this.Email=Email;
        this.PhoneNum=PhoneNum;
        this.SysID=SysID;
        this.own=own;
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

    public String getSysID(){return SysID;}

    public void setSysID(String sysID){SysID = sysID;}

    public String  getown(){return own;}

    public void setOwn(String setOwn){own= own;}
}
