package org.juicecode.telehlam.socketio;

import androidx.lifecycle.LiveData;

public interface onMessageCallback {
    void savingIncomingMessage(LiveData<String> message);
}
