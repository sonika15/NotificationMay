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
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }


    public void showNotification(String title, String message) {
        Log.wtf("notify", title);
        // Intent intent = new Intent(this, activity.getClass());
        String channel_id = "notification_channel";
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true);
//                .setContentIntent(pendingIntent);
        builder = builder.setContentTitle(title).setContentText(message);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.wtf("token", s);
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("deviceToken", s);
        editor.apply();
        sendPost();
        super.onNewToken(s);
    }


    public void sendPost() {
        deviceToken = ApiUtil.getToken(getApplicationContext());
        visitorId = ApiUtil.getVisitorId(getApplicationContext());
        Log.wtf("deviceToken", deviceToken);
        Log.wtf("visitorId", visitorId);
        NetworkService service = ApiUtil.getAPIService();
        Subscription subscription = new Subscription(deviceToken);
        DeviceSubscribe deviceSubscribe = new DeviceSubscribe(visitorId, subscription, "android");
        service.savePost(deviceSubscribe).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    Log.wtf("post submitted to API.", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.wtf("failure", "Unable to submit post to API.");
            }
        });
    }

}
