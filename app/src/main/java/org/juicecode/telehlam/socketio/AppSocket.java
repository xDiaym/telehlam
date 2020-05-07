package org.juicecode.telehlam.socketio;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.juicecode.telehlam.utils.Constant;

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
