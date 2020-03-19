package org.juicecode.hlam.core.messaging;

import org.juicecode.hlam.core.messaging.Message;

import java.util.Date;

public class OutgoingMessage extends Message {
    public enum Status {
        SENT, READ, ERROR
    }

    private Status MessageStatus;

    public OutgoingMessage(String text, Date timestamp) {
        super(text, timestamp);
    }

    public Status getMessageStatus() {
        return MessageStatus;
    }

    public void setMessageStatus(Status messageStatus) {
        MessageStatus = messageStatus;
    }
}
