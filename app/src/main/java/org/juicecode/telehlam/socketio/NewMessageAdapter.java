package org.juicecode.telehlam.socketio;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.database.messages.Message;

public class NewMessageAdapter implements Emitter.Listener {
    private MainActivity mainActivity;
    private OnNewMessageListener callback;

    public NewMessageAdapter(final MainActivity mainActivity, final OnNewMessageListener callback) {
        this.mainActivity = mainActivity;
        this.callback = callback;
    }

    @Override
    public void call(final Object... args) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("TAG", ((JSONObject) args[0]).getString("text"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // TODO: opening authorisation if user's token is wrong
                for (Object object : args) {
                    callback.onNewMessage(buildMessage((JSONObject) object));
                }
            }
        });
    }

    @Nullable
    private Message buildMessage(JSONObject object) {
        Message message = null;
        try {
            message = new Message(
                    Message.MESSAGE_INCOMING,
                    object.getString("text"),
                    object.getLong("authorId"),
                    object.getLong("receiverId"),
                    object.getLong("timestamp")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

}

