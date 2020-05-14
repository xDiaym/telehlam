package org.juicecode.telehlam.socketio;

import android.util.Log;


import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class AppSocket {
    private static final String TAG = AppSocket.class.getName();

    private static AppSocket instance;
    private Socket socket;

    private AppSocket(String url) {
        try {
            socket = IO.socket(url);
        } catch (URISyntaxException e) {
            Log.e(TAG, String.valueOf(e));
        }
    }

    public static AppSocket getInstance(String url) {
        if (instance == null) {
            instance = new AppSocket(url);
        }
        return instance;
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    public void emit(String event, Object... args) {
        socket.emit(event, args);
    }

    public void addListener(String event, Emitter.Listener listener) {
        socket.on(event, listener);
    }
    public void off(String event) {
        socket.off(event);
    }

}
