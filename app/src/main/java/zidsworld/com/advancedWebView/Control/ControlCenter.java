package zidsworld.com.advancedWebView.Control;

public class ControlCenter {

    public final boolean SHOW_DRAWER_MENU = false; // Hide or show left side navigation drawer
    public boolean SHOW_BOTTOM_MENU = false;//Bottom navigation menu
    public boolean ENABLE_SWIPE_REFRESH = false; //Swipe down to refresh web page
    public boolean SHOW_TOOLBAR = false; //Header toolbar. If this set false, option menu and drawer menus will also be hidden
    public boolean AUTO_HIDE_TOOLBAR = false; //Auto hide header toolbar when scrolling web page
    //FUNCTIONS
    public final boolean CLEAR_CACHE_ON_APP_EXIT = false; // Clear all cache upon exiting app
    public final boolean ENABLE_CACHING = false; // If set true, webView will try load cache first, if cache not available, it will load from network
    //This can improve speed, But remember, this can prevent showing updated content
    public final boolean ASK_TO_EXIT = false; //Shows App Exit Dialogue
    public final boolean SUPPORT_MULTI_WINDOWS = true; // Enables TARGET_BLANK in a separate window
    public static boolean LOAD_LAST_WEBPAGE = false; // Load the last webpage after restarting app
    public boolean BLOCK_ADS = false; //Value overrides in Settings, Experimental, if true, this should disable some ads in web pages. This has bugs, be careful
     public boolean REQUEST_RUNTIME_PERMISSIONS = false; // Unused
    public boolean LOAD_LAST_WEBPAGE_ON_ACCIDENTAL_APP_EXIT = false;
    public boolean BLOCK_EXTERNAL_LINKS = false;  //If you don't want load links other than your website links, set it true
    public boolean OPEN_EXTERNAL_LINKS_OUTSIDE = false; // Load all foreign links outside, in a browser
    public static final boolean SHOW_LIST_ACTIVITY = false; // If true, this will show a list activity before launching WebActivity
    //useful for showing a list of items or options of your website. customize your icons in ListMainActivity
    //and recyclerview adapter
    public static final boolean SHOW_WELCOME_SLIDER = false; // Enable or disable welcome slider here
    public static final boolean SHOW_APP_VERSION = false; // Show app version in splash screen

    //CUSTOMIZE APP EXIT DIALOGUE, THIS WILL WORK ONLY IF ENABLED ASK_TO_EXIT = TRUE;
    public static String AppExitTitle = "Exit app";
    public static String AppExitMsg = "Sure to exit?";
    public static String AppExitYes = "Exit";
    public static String AppExitNo = "No";
}
