package org.juicecode.telehlam.notifications;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

import static org.juicecode.telehlam.notifications.App.CHANNEL_ID;

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
        if(!(intent.getBooleanExtra("isActive",true))){
            //sending notification
            Message message = (Message) intent.getSerializableExtra("message");
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
                .setSmallIcon(R.drawable.notification_icon);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        if(new SharedPreferencesRepository(this).getFingerPrint()){
                    builder.setContentTitle("New message from JOhn DAun")
                    .setContentText("Go to app to check message")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);
        } else {
            builder.setContentTitle("John Daun")
                    .setContentText(message.getText())
                    .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(message.getText()))
                    .setColor(Color.BLUE)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
        stopSelf();
    }
}
