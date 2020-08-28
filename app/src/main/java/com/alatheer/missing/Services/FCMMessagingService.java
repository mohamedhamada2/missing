package com.alatheer.missing.Services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class FCMMessagingService extends FirebaseMessagingService {
    String type = "";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            type = "json";
            sendNotification(remoteMessage.getData().toString());
        }

        if (remoteMessage.getNotification() != null) {
            type = "message";
            sendNotification(remoteMessage.getNotification().getBody());

        }
        Log.e("message",remoteMessage.getData().toString());
    }

    private void sendNotification(String messageBody) {
        String id = "", message = "", title = "";
        if (type.equals("json")) {
            try {
                JSONObject jsonObject = new JSONObject(messageBody);
                id = jsonObject.getString("id");
                message = jsonObject.getString("message");
                title = jsonObject.getString("title");
            } catch (JSONException e) {
                //
            }
        } else if (type.equals("message")) {
            message = messageBody;
        }
        Intent intent = new Intent("com.alatheer.missing_FCM-MESSAGE");
        intent.putExtra("message",message);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);
    }
}
