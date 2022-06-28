package com.io.sharedsecuritysystem.Response;

public class HistoryResponse {

    private int image;
    private String message;
    private String Date;
    private String Time;
    private String Note;
    private String data;

    public HistoryResponse() {

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAlert() {
        return message;
    }

    public void setAlert(String alert) {
        this.message = alert;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
