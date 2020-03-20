package org.juicecode.hlam.core.messaging;

import java.util.Date;

public class OutgoingMessage extends Message {
    private Status messageStatus;

    public OutgoingMessage(String text, Date timestamp) {
        super(text, timestamp);
    }

    public OutgoingMessage(String messageText) {
        super(messageText);
    }

    public Status getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Status status) {
        if (messageStatus == Status.READ && status == Status.SENT) {
            // TODO(all): create custom error class
            throw new Error("Cannot change status");
        }
        messageStatus = status;
    }

    public enum Status {
        SENT, READ, ERROR
    }
}
