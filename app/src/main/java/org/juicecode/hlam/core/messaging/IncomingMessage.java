package org.juicecode.hlam.core.messaging;

import org.juicecode.hlam.core.messaging.Message;

import java.util.Date;

public class IncomingMessage extends Message {

    public IncomingMessage(String text, Date timestamp) {
        super(text, timestamp);
    }
}
