package org.juicecode.hlam.core.messaging;

import java.util.Date;

public abstract class Message {
    public static final int MESSAGE_INCOMING = 0;
    public static final int MESSAGE_OUTGOING = 1;

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
