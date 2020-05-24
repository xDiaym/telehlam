package org.juicecode.telehlam;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {
    private static boolean isActive;

    public static void setIsActive(boolean isActive) {
        NotificationService.isActive = isActive;
    }

    public static boolean isIsActive() {
        return isActive;
    }

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
        if(!intent.getBooleanExtra("isActive",true)){
            //sending notification
            Notification customNotification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .build();
            Log.i("notification","this is future notification" );
            startForeground(1, customNotification);
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
