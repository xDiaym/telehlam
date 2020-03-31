package org.juicecode.telehlam.core.messaging;

import java.util.Date;

public class IncomingMessage extends Message {

    public IncomingMessage(String text, Date timestamp) {
        super(text, timestamp);
    }

    public IncomingMessage(String messageText) {
        super(messageText);
    }
}
