package org.juicecode.hlam.core.messaging;

import java.util.Date;

public abstract class Message {
    private final String message;
    private final Date timestamp;

    public Message(String text, Date timestamp) {
        this.message = text;
        this.timestamp = timestamp;
    }


    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
