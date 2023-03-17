package zidsworld.com.advancedWebView.Activities;


import static zidsworld.com.advancedWebView.Control.Constants.HomePage;
import static zidsworld.com.advancedWebView.Control.Constants.MyPhoneNo;
import static zidsworld.com.advancedWebView.Control.Constants.TelegramLink;
import static zidsworld.com.advancedWebView.R.layout.httpauthlayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import zidsworld.com.advancedWebView.Control.Constants;
import zidsworld.com.advancedWebView.Control.ControlCenter;
import zidsworld.com.advancedWebView.R;
import zidsworld.com.advancedWebView.databinding.WebactivityLayoutBinding;
import zidsworld.com.webviewlibrary.WebDome;


public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    public static final boolean openblobPdfafterDownload = true;
    private static final String TAG = WebActivity.class.getSimpleName();

    private static final int PERMISSION_WBCHROME = 3;
    public static boolean ChangeListener = false;

    public LinearLayout errorLayout;
    public Context mContext;
    public WebView webView;
    public String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
            // you can add more permissions here, and in manifest (both required)
    };
    public String name;
    DrawerLayout drawer;
    LinearLayout urlLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Button errorReloadButton;
    Button errorHomeButton;
    Button errorExitButton;
    BottomNavigationView bottomNavigationView;
    ProgressBar toolBarProgress;
    ProgressBar HorizontalProgressBar;
    ProgressDialog progressDialog;
    FrameLayout horizontalProgressFramelayout;
    Intent UrlIntent;
    Uri data;
    RelativeLayout nativeLoadview;
    String[] outLinks = {
            "https://google.com", "https://facebook.com"
    };
    ControlCenter controlCenter = new ControlCenter();


    WebDome webDome = new WebDome();
    RelativeLayout alertLayoutX;
    private AdView mAdView;
    private SwipeRefreshLayout swipeView;
    private EditText urlEdittext;
    private SharedPreferences preferences;
    private ProgressBar simpleProgressbar;
    private InterstitialAd mInterstitialAd;
    private RelativeLayout windowContainer;
    private SharedPreferences prefs;
    private ImageButton web_button;
    private AppBarLayout mAppBarLayout;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

                    return true;
                }
            }
        }

        return false;
    }

    WebactivityLayoutBinding binding;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("darktheme", false)) {
            setTheme(R.style.DarkTheme);
        }

        setContentView(R.layout.webactivity_layout);

        getWindow(). addFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mContext = this.getApplicationContext();

        RelativeLayout mContainer = findViewById(R.id.web_container);
        windowContainer = findViewById(R.id.window_container);
        ProgressBar windowProgressbar = findViewById(R.id.WindowProgressBar);

        data = getIntent().getData();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        prefs = mContext.getSharedPreferences("apprater", 0);
        UrlIntent = getIntent();

        drawer = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        progressDialog = new ProgressDialog(WebActivity.this);
        horizontalProgressFramelayout = findViewById(R.id.frameLayoutHorizontalProgress);
        mAppBarLayout = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.webView);

        mAdView = findViewById(R.id.adView);
        navigationView = findViewById(R.id.nav_view);
        toolBarProgress = findViewById(R.id.toolbarprogress);

        HorizontalProgressBar = findViewById(R.id.progressbar);
        errorReloadButton = findViewById(R.id.reloadButton);

        errorLayout = findViewById(R.id.errorLayout);
        TextView errorCode = findViewById(R.id.errorinfo);

        swipeView = findViewById(R.id.swipeLayout);
        urlEdittext = findViewById(R.id.urledittextbox);
        urlLayout = findViewById(R.id.urllayoutroot);
        errorExitButton = findViewById(R.id.errorlayoutExitButton);
        errorHomeButton = findViewById(R.id.errorlayouHomeButton);
        nativeLoadview = findViewById(R.id.nativeloadview);


        //Alerter attributes
        alertLayoutX = findViewById(R.id.toastContainer);
        TextView text = findViewById(R.id.toastText);
        webDome.alertLayout = alertLayoutX;
        webDome.alertLayoutMsgTextView = text;
        webDome.alertLayoutLottie = findViewById(R.id.lottie);

        //WEB-DOME HIGHLY ADVANCED WEB ENGINE
        webDome.setHomePage = HomePage;

        //customize exit dialog in ControlCenter
        WebDome.exitTitle = ControlCenter.AppExitTitle;
        WebDome.exitMsg = ControlCenter.AppExitMsg;
        WebDome.exitYes = ControlCenter.AppExitYes;
        WebDome.exitNo = ControlCenter.AppExitNo;;

        webDome.setSupportMultiwindow = controlCenter.SUPPORT_MULTI_WINDOWS;
        webDome.errorCode = errorCode;
        webDome.errorLayout = errorLayout;
        webDome.errorReloadButton = errorReloadButton;
        webDome.errorHomeButton = errorHomeButton;
        webDome.errorExitButton = errorExitButton;

        webDome.simpleProgressBar = simpleProgressbar;
        webDome.HorizontalProgressBar = HorizontalProgressBar;
        webDome.horizProgressLayout = horizontalProgressFramelayout;
        webDome.nativeloadview = nativeLoadview;
        webDome.windowProgressbar = windowProgressbar;

        webDome.mContainer = mContainer;
        webDome.windowContainer = windowContainer;
        webDome.useSelfWebClient = false;

        swipeView.setEnabled(false);
        swipeView.setRefreshing(false);
        InitiatePreferences();
        InitiateComponents();

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);


        webSettings.setLoadWithOverviewMode(false);
        webSettings.setUseWideViewPort(true);

        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setUserAgentString(webSettings.getUserAgentString().replace("wv", ""));
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptThirdPartyCookies(webView);

        webSettings.setJavaScriptEnabled(true);
        //Enable this to support Blob operations

        webSettings.setAllowFileAccess(true);

        if (controlCenter.SUPPORT_MULTI_WINDOWS) {
            webSettings.setSupportMultipleWindows(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        }

        if (controlCenter.ENABLE_CACHING) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        webView.setSaveEnabled(true);
        webSettings.setAllowContentAccess(true);

        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);

        if (!webDome.useSelfWebClient) {
            webView.setWebViewClient(new AdvancedWebViewClient());
        }

        if (UrlIntent.hasExtra("url")) {
            webDome.setHomePage = Objects.requireNonNull(getIntent().getStringExtra("url"));
            webDome.CreateWebView(WebActivity.this, webView);
        } else if (data != null) {
            webDome.setHomePage = data.toString();
            webDome.CreateWebView(WebActivity.this, webView);
        } else {
            webDome.CreateWebView(WebActivity.this, webView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {

        if (ChangeListener) {
            overridePendingTransition(0, 0);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);

            ChangeListener = false;
        }
        super.onResume();
    }

    private void InitiateComponents() {


        if (controlCenter.ASK_TO_EXIT) {
            webDome.AskToExit = true;
        }

        if (ControlCenter.LOAD_LAST_WEBPAGE) {
            webDome.LoadLastWebPage = true;
        } else {
            ClearLastUrl();
        }

        if (!controlCenter.SHOW_DRAWER_MENU) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

            navigationView.setItemIconTintList(null);

            //Menu of Left Swipe Navigation Drawer
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.nav_home) {
                        HandleDrawerUrls(HomePage);

                    }  else if (id == R.id.nav_contactus) {

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + MyPhoneNo));
                        startActivity(intent);

                    } else if (id == R.id.nav_telegram) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TelegramLink)));

                    }

                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        }
    }

    private void HandleDrawerUrls(String url) {

        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }

        if (windowContainer.getVisibility() == View.VISIBLE) {
            webDome.ClosePopupWindow();
        }
        webView.loadUrl(url);
    }

    private void InitiatePreferences() {

        if (preferences.getBoolean("hidebottombar", false)) {
            controlCenter.SHOW_BOTTOM_MENU = false;
        }

        if (preferences.getBoolean("swiperefresh", false)) {
            controlCenter.ENABLE_SWIPE_REFRESH = true;
        }

        if (preferences.getBoolean("blockAds", false)) {
            controlCenter.BLOCK_ADS = true;
        }

        if (preferences.getBoolean("fullscreen", false)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (preferences.getBoolean("immersive_mode", false)) {
            controlCenter.SHOW_TOOLBAR = false;

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (preferences.getBoolean("permission_query", false)) {
            controlCenter.REQUEST_RUNTIME_PERMISSIONS = true;
        }

        if (preferences.getBoolean("loadLastUrl", false)) {
            controlCenter.LOAD_LAST_WEBPAGE_ON_ACCIDENTAL_APP_EXIT = true;
        }
        if (preferences.getBoolean("autohideToolbar", true)) {
            controlCenter.AUTO_HIDE_TOOLBAR = true;
        }else {
            controlCenter.AUTO_HIDE_TOOLBAR = false;
        }

        if (preferences.getBoolean("hideToolbar", false)) {
            controlCenter.SHOW_TOOLBAR = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

       if (id == R.id.exit) {
            if (controlCenter.CLEAR_CACHE_ON_APP_EXIT) {
                webView.clearCache(true);
            }

            if (!ControlCenter.LOAD_LAST_WEBPAGE) {
                ClearLastUrl();
            }
            finish();

            try {
                finishAffinity();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Uri[] results = null;
        //Check if response is positive
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == webDome.FCR) {
                if (null == webDome.mUMA) {
                    return;
                }
                if (intent == null) {
                    //Capture Photo if no image available
                    if (webDome.mCM != null) {
                        results = new Uri[]{Uri.parse(webDome.mCM)};
                    }
                } else {
                    String dataString = intent.getDataString();
                    ClipData clipData = intent.getClipData();
                    if (clipData != null) {
                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }
        }
        try {
            webDome.mUMA.onReceiveValue(results);
            webDome.mUMA = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MANAGING BACK BUTTON
    @Override
    public void onBackPressed() {
        try {
            webDome.HandleWebViewBackKey(WebActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ClearLastUrl() {
        SharedPreferences pp = PreferenceManager.getDefaultSharedPreferences(mContext);
        pp.edit().remove("lasturl").apply();
    }

    @Override
    public void onClick(View view) {
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        view.startAnimation(buttonClick);

        if (view.getId() == R.id.reloadButton) {
            webDome.HandleBtnClicks("errorReload");
        } else if (view.getId() == R.id.errorlayouHomeButton) {
            webDome.HandleBtnClicks("errorGoHome");
        } else if (view.getId() == R.id.errorlayoutExitButton) {
            webDome.HandleBtnClicks("errorExit");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                webDome.loadUrl(webDome.LastUrl, webView);
            }
        }

        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Constants.permReloadCount++;

                new AlertDialog.Builder(WebActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("App restart required")
                        .setMessage("The app need to be restarted to apply the permissions, would you like to restart now?")
                        .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recreate();
                            }
                        })
                        .setNegativeButton("Not now", null)
                        .show();
            } else {

                showAlert("Permission denied!, some app functions require permissions", true);
            }
        } else if (requestCode == PERMISSION_WBCHROME) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                webView.reload();
            }
        }
    }

    public void showAlert(String message, boolean isError) {
        webDome.invokeZalert(WebActivity.this, message, isError);
    }

    public void ShareItem(Context context, String ShareText, String Subject, String ShareTitle) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, ShareText);
        context.startActivity(Intent.createChooser(sharingIntent, ShareTitle));
    }

    private class AdvancedWebViewClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            if (controlCenter.BLOCK_ADS) {
                if (url.contains("googleads.g.doubleclick.net")) {
                    InputStream textStream = new ByteArrayInputStream("".getBytes());
                    return getTextWebResource(textStream);
                }
            }
            return super.shouldInterceptRequest(view, url);
        }

        private WebResourceResponse getTextWebResource(InputStream data) {
            return new WebResourceResponse("text/plain", "UTF-8", data);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            webView.setAlpha((float) 0.7);
            if (errorLayout.getVisibility() == View.VISIBLE) {
                errorLayout.setVisibility(View.GONE);
            }
            if (controlCenter.BLOCK_EXTERNAL_LINKS) {
                URI currentUri;
                URI homeUri;
                try {
                    currentUri = new URI(url);
                    homeUri = new URI(HomePage);
                    String myDomain = homeUri.getHost();
                    String OutDomain = currentUri.getHost();

                    if (OutDomain != null && !OutDomain.matches(myDomain)) {
                        view.stopLoading();
                        Toast.makeText(mContext, "The link is blocked", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(() -> {
                            if (simpleProgressbar.getVisibility() == View.VISIBLE) {
                                simpleProgressbar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            if (controlCenter.OPEN_EXTERNAL_LINKS_OUTSIDE) {
                URI currentUri;
                URI homeUri;
                try {
                    currentUri = new URI(url);
                    homeUri = new URI(HomePage);
                    String myDomain = homeUri.getHost();
                    String OutDomain = currentUri.getHost();

                    if (OutDomain != null && !OutDomain.matches(myDomain)) {
                        view.stopLoading();

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);

                        new Handler().postDelayed(() -> {
                            if (simpleProgressbar.getVisibility() == View.VISIBLE) {
                                simpleProgressbar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            if (url.startsWith("http://") || url.startsWith("file:///") || url.startsWith("https://"))

                return false;

            webDome.HandleIntents(url, view);

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if (ControlCenter.LOAD_LAST_WEBPAGE) {
                preferences.edit().putString("lasturl", url).apply();
            }
            super.onPageFinished(view, url);
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            if (description.matches("net::ERR_FAILED")) {

            } else {

                webDome.HideErrorPage(failingUrl, description);
            }
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
//            super.onReceivedHttpAuthRequest(view, handler, host, realm);
            if (handler.useHttpAuthUsernamePassword()) {
                String[] creds = view.getHttpAuthUsernamePassword(host, realm);
                if (creds != null) {
                    handler.proceed(creds[0], creds[1]);
                    return;
                }
            }

            View authlayout = getLayoutInflater().inflate(R.layout.httpauthlayout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);

            builder.setView(authlayout);
            EditText nameEdit = authlayout.findViewById(R.id.usr);
            EditText passEdit = authlayout.findViewById(R.id.pss);

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = nameEdit.getText().toString();
                    String pass = passEdit.getText().toString();
                    view.setHttpAuthUsernamePassword(host, realm, name, pass);
                    handler.proceed(name, pass);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    handler.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
