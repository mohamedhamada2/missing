package com.alatheer.missing.Data.Remote;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class ChatSocket {
    private static Socket mSocket;

    public static Socket getChatSocket() {
        if (mSocket == null) {
            try {
                mSocket = IO.socket("http://chat.socket.io");
            } catch (URISyntaxException e) {
                Log.e("exception:",e.getMessage());
            }
        }
        return mSocket;
    }

}
