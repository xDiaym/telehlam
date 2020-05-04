package org.juicecode.telehlam.socketio;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.ui.registration.AuthorisationFragment;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

public class SocketIOMethods {
    private Emitter.Listener login;
    private MainActivity mainActivity;
    private FragmentManagerSimplifier fragmentManagerSimplifier;
    private Emitter.Listener onMessage;
    private onMessageCallback onMessageCallback;
    public Emitter.Listener getOnMessage() {
        return onMessage;
    }

    public Emitter.Listener getLogin() {
        return login;
    }

    public SocketIOMethods(final MainActivity mainActivity, final onMessageCallback callback) {
        this.mainActivity = mainActivity;
        this.onMessageCallback = callback;
        //login , emiting that user is online and get's error if token is wrong
        login = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                //running in UI as in docs
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // opening authorisation if user's token is wrong
                        JSONObject json =(JSONObject) args[0];
                        fragmentManagerSimplifier.addFragment(new AuthorisationFragment(), "authorisation");
                    }
                });
            }
        };
        //onMessage event
        onMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject)args[0];
                        //getting message from json
                        try {
                            String message = data.getString("message");
                            //executing callback of saving message in room database;
                            callback.savingMessage(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };


    }


}
