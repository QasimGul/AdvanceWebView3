package zidsworld.com.advancedWebView.Elements;

import static zidsworld.com.advancedWebView.Control.Constants.OneSignalAppId;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDexApplication;

import com.onesignal.OneSignal;

import zidsworld.com.advancedWebView.Activities.WebActivity;


public class MyApplication extends MultiDexApplication {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        OneSignal.initWithContext(this);
        OneSignal.setAppId(OneSignalAppId);

        OneSignal.setNotificationOpenedHandler(result -> {
            String launchURL = result.getNotification().getLaunchURL();

            if (launchURL != null) {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", launchURL);
                startActivity(intent);
            }
        });
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}