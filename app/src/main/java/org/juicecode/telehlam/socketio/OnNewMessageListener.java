package org.juicecode.telehlam.socketio;

import org.juicecode.telehlam.database.messages.Message;

public interface OnNewMessageListener {
    void onNewMessage(Message message);
}
