package org.juicecode.telehlam.socketio;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

public class loginListener implements Emitter.Listener {
    private MainActivity mainActivity;
    private FragmentManagerSimplifier fragmentManagerSimplifier;

    public loginListener(MainActivity mainAcivity, FragmentManagerSimplifier fragmentManagerSimplifier) {
        this.mainActivity = mainAcivity;
        this.fragmentManagerSimplifier = fragmentManagerSimplifier;
    }

    @Override
    public void call(final Object... args) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // opening authorisation if user's token is wrong
                JSONObject json =(JSONObject) args[0];
                fragmentManagerSimplifier.addFragment(R.id.authorisationFragment);
            }
        });
    }
}
