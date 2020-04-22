package org.juicecode.telehlam;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.utils.Constant;

import java.net.URISyntaxException;

public class AppSocket {
    private static Socket mSocket;
    private Emitter.Listener login;

    public AppSocket() {
        {
            try {
                mSocket = IO.socket(Constant.getBaseURL());
            } catch (URISyntaxException e) {

            }
        }
        login = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                try {
                    String token = jsonObject.getString("token");
                } catch (JSONException exception) {
                    
                }
            }
        };
    }

    public static Socket getSocket() {
        return mSocket;
    }

}
