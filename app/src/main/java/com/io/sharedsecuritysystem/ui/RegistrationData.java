package com.io.sharedsecuritysystem.ui;

public class RegistrationData {

    private String Name, Email, PhoneNum, SysID;
    private Boolean own, sysControl;

    public RegistrationData(String Name, String Email, String PhoneNum, String SysID, Boolean own, Boolean sysControl){
        this.Name=Name;
        this.Email=Email;
        this.PhoneNum=PhoneNum;
        this.SysID=SysID;
        this.own=own;
        this.sysControl=sysControl;
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

    public Boolean  getown(){return own;}

    public void setOwn(Boolean setOwn){own= own;}

    public Boolean getSysControl(){return sysControl;}

    public void setSysControl(Boolean sysControl){sysControl = sysControl;}
}
