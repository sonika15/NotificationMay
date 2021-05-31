package com.galactik.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.galactik.notification.ApiUtil.MyPREFERENCES;


public class GalactikNotificationService extends FirebaseMessagingService {
    SharedPreferences sharedpreferences;
    String deviceToken = "";
    String visitorId = "";

    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage) {
        Log.wtf("message", String.valueOf(remoteMessage.getData()));
        if (remoteMessage.getData() != null) {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("notificationTitle");
            String message = data.get("notificationMessage");
            showNotification(title, message);
        }
//        if (remoteMessage.getNotification() != null) {
//            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//        }
    }


    public void showNotification(String title, String message) {
        Log.wtf("notify", title);
        Log.wtf("notifyMessage", message);
        // Intent intent = new Intent(this, activity.getClass());
        String channel_id = "notification_channel";
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String GROUP_KEY = "com.galactik.notifications.KEY";
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setContentTitle(title)
                .setContentText(message)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.setSmallIcon(R.drawable.ic_stat_name);

        } else {
            notification.setSmallIcon(R.drawable.ic_stat_name);
        }
        notificationManager.notify(0, notification.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.wtf("token", s);
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("deviceToken", s);
        editor.apply();
        super.onNewToken(s);
    }


}
