package org.juicecode.hlam.ui.chat;

public class Message {
    private String message;
    private String date;

    public String getMessageString() {
        return message;
    }

    public void setMessageString(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Message(String m){
        this.message = m;
    }
}
