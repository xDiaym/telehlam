package org.juicecode.telehlam.socketio;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.database.messages.Message;

public class MessageEvent {
    private AppSocket socket;

    public MessageEvent(AppSocket socket) {
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

    public abstract static class MessageListener implements Emitter.Listener {
        private Activity activity;

        protected MessageListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (Object object : args) {
                        onNewMessage(buildMessage((JSONObject) object));
                    }
                }
            });
        }

        public abstract void onNewMessage(Message message);

        @Nullable
        private Message buildMessage(JSONObject object) {
            Message message = null;
            try {
                message = new Message(
                        Message.MESSAGE_INCOMING,
                        object.getString("text"),
                        object.getLong("authorId"),
                        object.getLong("receiverId"),
                        object.getLong("timestamp"),
                                false
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return message;
        }
    }

}
