package com.example.letsgo.ContactUs;

public class Message {
    private String message,from,to;
    public Message (){}
    public Message (String message , String from ,String to){

        this.message=message;
        this.from = from;
        this.to=to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }
}
