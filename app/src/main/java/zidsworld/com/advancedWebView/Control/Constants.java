package zidsworld.com.advancedWebView.Control;

import android.net.Uri;

import java.io.File;

import zidsworld.com.advancedWebView.Elements.MyApplication;

public class Constants {

    public static final String app_package_id = MyApplication.getContext().getPackageName();

    public static final String HomePage = "https://ufile.io/"; //<< Your Site URL (Supports http, https, file:/// Protocols)
    public static final String MyPhoneNo = "+9226768768";
    public static final String TelegramLink = "https://t.me/";

    //OneSignal AppID
    public static final String OneSignalAppId = " ";

    /*Do not remove or edit the codes below!*/
    public static final String AUTHORITY = app_package_id + ".fileprovider";
    public static String currentDownloadFileName;
    public static String currentDownloadFileMimeType;
    public static Uri currentFileUri;
    public static int permReloadCount=0;
    public static File DownloadPath;
}
