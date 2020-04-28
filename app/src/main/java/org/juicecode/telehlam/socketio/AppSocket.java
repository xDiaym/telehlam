package org.juicecode.telehlam.socketio;

import androidx.fragment.app.FragmentManager;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;
import org.juicecode.telehlam.ui.registration.AuthorisationFragment;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.net.URISyntaxException;

public class AppSocket {
    private static Socket mSocket;
    private Emitter.Listener THEError;
    private FragmentManagerSimplifier fragmentManagerSimplifier;

    public AppSocket(final FragmentManagerSimplifier fragmentManagerSimplifier) {
        {
            try {
                mSocket = IO.socket(Constant.getBaseURL());
            } catch (URISyntaxException e) {

            }
        }
        this.fragmentManagerSimplifier =fragmentManagerSimplifier;
        THEError = new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                JSONObject json =(JSONObject) args[0];
                fragmentManagerSimplifier.addFragment(new AuthorisationFragment(), "authorisation");
            }
        };
    }

    public static Socket getSocket() {
        return mSocket;
    }

}
