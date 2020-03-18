package org.juicecode.hlam.core.messaging;

import java.util.Date;

public class Message {

    private final MessageType type;
    private final String message;
    private final Date timestamp;

    public Message(String text, MessageType type, Date timestamp){
        this.message = text;
        this.type = type;
        this.timestamp = timestamp;
    }


    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public MessageType getMessageType() {
        return type;
    }

}
