package org.juicecode.telehlam.socketio;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.database.messages.Message;

public class MessageSender {

    AppSocket socket;

    public MessageSender(AppSocket socket) {
        this.socket = socket;
    }

    public void sendMessage(Message message) {
        socket.emit("message", toObject(message));
    }

    @Nullable
    private static JSONObject toObject(Message message) {
        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(message, Message.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

}
