package org.juicecode.hlam.ui.chat;

public class IncomingMessage {
    private String message;
    private String date;

    public IncomingMessage(String text) {
        this.message = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
