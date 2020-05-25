package org.juicecode.telehlam;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.juicecode.telehlam.database.messages.Message;

import static org.juicecode.telehlam.App.CHANNEL_ID;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isActive = intent.getBooleanExtra("isActive",true);
        Log.i("bool", String.valueOf(isActive));
        if(!(intent.getBooleanExtra("isActive",true))){
            //sending notification
            Message message = (Message) intent.getSerializableExtra("message");
            Log.i("messageText", message.getText());
            createNotification(message);

        }
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void createNotification(Message message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("New message")
                .setContentText(message.getText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
        stopSelf();
    }
}
