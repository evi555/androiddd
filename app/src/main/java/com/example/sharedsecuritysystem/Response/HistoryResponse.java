package com.example.sharedsecuritysystem.Response;

public class HistoryResponse {

    private int image;
    private String alert;
    private String Date;
    private String Time;
    private String Note;

    public HistoryResponse(int image, String alert, String date, String time, String note) {
        this.image = image;
        this.alert = alert;
        Date = date;
        Time = time;
        Note = note;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
