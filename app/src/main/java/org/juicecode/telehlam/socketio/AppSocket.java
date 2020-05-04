package org.juicecode.telehlam.socketio;

import android.app.Activity;
import android.content.Context;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.ui.registration.AuthorisationFragment;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.net.URISyntaxException;

public class AppSocket {
    private static Socket mSocket;
    public AppSocket() {

        {
            try {
                mSocket = IO.socket(Constant.baseUrl);
            } catch (URISyntaxException e) {

            }
        }
    }



    public static Socket getSocket() {
        return mSocket;
    }

}
