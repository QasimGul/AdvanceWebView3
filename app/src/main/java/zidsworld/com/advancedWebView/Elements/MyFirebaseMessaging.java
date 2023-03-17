package zidsworld.com.advancedWebView.Elements;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import zidsworld.com.advancedWebView.R;
import zidsworld.com.advancedWebView.Activities.WebActivity;


/**
 * NOTE: There can only be one service in each app that receives FCM messages. If multiple
 * are declared in the Manifest then the first one will be chosen.
 *
 * In order to make this Java sample functional, you must remove the following from the Kotlin messaging
 * service in the AndroidManifest.xml:
 *
 * <intent-filter>
 *   <action android:name="com.google.firebase.MESSAGING_EVENT" />
 * </intent-filter>
 */
public class MyFirebaseMessaging extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

            if (remoteMessage.getNotification() != null) {

                        sendNotification(
                        remoteMessage.getNotification().getTitle(),
                        remoteMessage.getNotification().getBody());
            }

        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "HAAAAAAAAAAAAAAAAAAAAAAAAA: " + remoteMessage.getFrom());
        }
//
//        // TODO: Implement this method to send token to your app server.
//    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String Title,String messageBody) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(Title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }


            if (notificationBuilder !=null){
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            }
    }

    public void showNotification(String title,
                                 String message) {
        // Pass the intent to switch to the MainActivity
        Intent intent
                = new Intent(this, WebActivity.class);
        // Assign channel ID
        String channel_id = "notification_channel";

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Builder object using NotificationCompat
        // class. This will allow Control over all the flags
        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.ic_os_notification_fallback_white_24dp)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager
                = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }
}