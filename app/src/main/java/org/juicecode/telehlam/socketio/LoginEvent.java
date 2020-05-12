package org.juicecode.telehlam.socketio;

import androidx.annotation.Nullable;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.rest.user.AuthInfo;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

public class LoginEvent {

    private AppSocket socket;

    public LoginEvent(AppSocket socket) {
        this.socket = socket;
    }

    @Nullable
    private static JSONObject toJson(AuthInfo info) {
        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(info, AuthInfo.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void login(AuthInfo info) {
        socket.emit("login", toJson(info));
    }

    public abstract static class LoginListener implements Emitter.Listener {
        private MainActivity mainActivity;
        private FragmentManagerSimplifier fragmentManagerSimplifier;

        public LoginListener(MainActivity mainAcivity, FragmentManagerSimplifier fragmentManagerSimplifier) {
            this.mainActivity = mainAcivity;
            this.fragmentManagerSimplifier = fragmentManagerSimplifier;
        }

        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO: Opening authorisation if user's token is wrong
                    JSONObject json = (JSONObject) args[0];
                    fragmentManagerSimplifier.addFragment(R.id.authorisationFragment);
                }
            });
        }
    }

}
