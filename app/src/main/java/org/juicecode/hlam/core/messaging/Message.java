package org.juicecode.hlam.core.messaging;

import java.util.Date;

public abstract class Message {
    private final String text;
    private final Date timestamp;

    public Message(String text, Date timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
