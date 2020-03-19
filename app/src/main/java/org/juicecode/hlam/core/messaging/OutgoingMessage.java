package org.juicecode.hlam.core.messaging;

import java.util.Date;

public class OutgoingMessage extends Message {
    public enum Status {
        SENT, READ, ERROR
    }

    private Status messageStatus;

    public OutgoingMessage(String text, Date timestamp) {
        super(text, timestamp);
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
}
