package zidsworld.com.advancedWebView.Elements;

import static zidsworld.com.advancedWebView.Activities.WebActivity.openblobPdfafterDownload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;


public class JavaScriptInterface {
    private Context context;

    public JavaScriptInterface(Context context) {
        this.context = context;
    }

@JavascriptInterface
    public void getBase64FromBlobData(String base64Data) throws IOException {
        convertBase64StringToPdfAndStoreIt(base64Data);
    }
    @JavascriptInterface
    public static String getBase64StringFromBlobUrl(String blobUrl){

        if(blobUrl.startsWith("blob")){
            return "javascript: var xhr = new XMLHttpRequest();" +
                    "xhr.open('GET', '"+blobUrl +"', true);" +
                    "xhr.setRequestHeader('Content-type','application/pdf');" +
                    "xhr.responseType = 'blob';" +
                    "xhr.onload = function(e) {" +
                    "    if (this.status == 200) {" +
                    "        var blobPdf = this.response;" +
                    "        var reader = new FileReader();" +
                    "        reader.readAsDataURL(blobPdf);" +
                    "        reader.onloadend = function() {" +
                    "            base64data = reader.result;" +
                    "            Android.getBase64FromBlobData(base64data);" +
                    "        }" +
                    "    }" +
                    "};" +
                    "xhr.send();";

        }

        return "javascript: console.log('It is not a Blob URL');";

    }
    @JavascriptInterface
    public void convertBase64StringToPdfAndStoreIt(String base64PDf) throws IOException {
        final int notificationId = 1;


        String currentDate = DateFormat.getDateInstance().format(new Date());
        final File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/BlobFile" + currentDate + "_.pdf");
        byte[] pdfAsBytes = Base64.decode(base64PDf.replaceFirst("^data:application/pdf;base64,", ""), 0);
        FileOutputStream os;
        os = new FileOutputStream(dwldsPath, false);
        os.write(pdfAsBytes);
        os.flush();

        if(dwldsPath.exists()) {

            if (openblobPdfafterDownload){
                AdvancedControls.DownloadFinishedAction(context.getApplicationContext());
            }
        }}
            public void OpenPDF(String dwldsPath) {
                try {
                    File file = new File(Environment.getExternalStorageDirectory()
                            + dwldsPath);
                    if (!file.isDirectory())
                        file.mkdir();
                    Intent pdfIntent = new Intent();
                    pdfIntent.setType("application/pdf");
                    pdfIntent.setAction(Intent.ACTION_VIEW);
                    Uri uri = FileProvider.getUriForFile(context,context.getPackageName()+".fileprovider",new File(dwldsPath));
                    pdfIntent.setDataAndType(uri, "application/pdf");
                    pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(pdfIntent);
                } catch (Exception e) {

                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

    }
