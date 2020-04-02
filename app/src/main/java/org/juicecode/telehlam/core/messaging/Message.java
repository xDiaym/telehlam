package org.juicecode.telehlam.core.messaging;

import java.util.Date;

public class Message {
    public enum Status {
        NONE, SENT, READ, ERROR;
    }

    public static final int MESSAGE_INCOMING = 0;
    public static final int MESSAGE_OUTGOING = 1;

    private final int type;
    private final String text;
    private final Date timestamp;
    private Status status;

    public Message(int type, String text, Date timestamp) {
        this.type = type;
        this.text = text;
        this.timestamp = timestamp;
        this.status = Status.NONE;
    }

    public Message(int type, String text) {
        this.type = type;
        this.text = text;
        this.timestamp = new Date();
        this.status = Status.NONE;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == Status.READ && status == Status.SENT) {
            // TODO(all): create custom error class
            throw new Error("Cannot change status");
        }
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
