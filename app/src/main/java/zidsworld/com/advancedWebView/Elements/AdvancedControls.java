package zidsworld.com.advancedWebView.Elements;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import java.io.File;

import zidsworld.com.advancedWebView.Control.Constants;
import zidsworld.com.advancedWebView.R;

import static android.content.ContentValues.TAG;

public class AdvancedControls {

    public static final File path = Constants.DownloadPath;

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert con_manager != null;
        return (con_manager.getActiveNetworkInfo() == null
                || !con_manager.getActiveNetworkInfo().isAvailable()
                || !con_manager.getActiveNetworkInfo().isConnected());
    }

    public static void CompletionReciever(Context context) {

        Constants.currentFileUri = FileProvider.getUriForFile(context, Constants.AUTHORITY, new File(path
                + "/" + Constants.currentDownloadFileName));
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                DownloadFinishedAction(context);

            }
        };
        context.registerReceiver(receiver, filter);
    }


    public static void DownloadFinishedAction(Context context) {
        try {


            new AlertDialog.Builder(context)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Download finished")
//                .setMessage("What do you like to do?")
                    .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openx(context, path);
                        }

                    })

                    .setNeutralButton("Share", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Uri uri = FileProvider.getUriForFile(context, Constants.AUTHORITY, new File(path
                                            + "/" + Constants.currentDownloadFileName));
                                    ShareFile(context, uri, Constants.currentDownloadFileMimeType);
                                }
                            }
                    ).setNegativeButton("Cancel", null)

                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void openx(Context context, File path) {


        Uri uri;
        try {
            uri = FileProvider.getUriForFile(context, Constants.AUTHORITY, new File(path
                    + "/" + Constants.currentDownloadFileName));

        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Unable to get content url from FileProvider", e);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(uri.toString()), Constants.currentDownloadFileMimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void ShareFile(Context context, Uri fileUri, String mimetype) {



        Intent shareIntent = new Intent();
        Intent chooser = Intent.createChooser(shareIntent, "Share File");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType(mimetype);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(shareIntent);
    }


    public static void shareFile11(Context context,Uri fileUri, String mimetype) {

        String AUTHORITY = context.getApplicationContext().getPackageName() + ".fileprovider";


        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");


        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this");
//        final File file1 = new File(VIDEO_LOCATION);
        Uri uri = FileProvider.getUriForFile(context.getApplicationContext(), AUTHORITY, new File(String.valueOf(fileUri)));
        Log.e("Path", "" + uri);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType(mimetype);

        //sendIntent.setType("audio/3gp"); // sending audio
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);

    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
