package zidsworld.com.advancedWebView.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import zidsworld.com.advancedWebView.R;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("darktheme", false)) {
            setTheme(R.style.DarkThemeSettings);
        }
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);


            SwitchPreference swipe_switch = findPreference("swiperefresh");
            SwitchPreference hide_bottom_switch = findPreference("hidebottombar");
            SwitchPreference location_switch = findPreference("geolocation");
            SwitchPreference dark_switch = findPreference("darktheme");
            SwitchPreference night_switch = findPreference("nightmode");
            SwitchPreference fullscr_switch = findPreference("fullscreen");
            SwitchPreference nativ_loader_switch = findPreference("nativeload");
            SwitchPreference blockAds_switch = findPreference("blockAds");
            SwitchPreference immersive_switch = findPreference("immersive_mode");
            SwitchPreference permissions_switch = findPreference("permission_query");
            SwitchPreference lastpage_switch = findPreference("loadLastUrl");
            SwitchPreference autoToolbar_switch = findPreference("autohideToolbar");
            SwitchPreference toolbarHide_switch = findPreference("hideToolbar");

            if (toolbarHide_switch != null) {
                toolbarHide_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    WebActivity.ChangeListener = true;
                    return true;
                });

            if (autoToolbar_switch != null) {
                autoToolbar_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    WebActivity.ChangeListener = true;
                    return true;
                });

            if (hide_bottom_switch != null) {
                hide_bottom_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    WebActivity.ChangeListener = true;
                    return true;
                });

            if (swipe_switch != null) {
                swipe_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    WebActivity.ChangeListener = true;
                    return true;
                });

            if (lastpage_switch != null) {
                lastpage_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    WebActivity.ChangeListener = true;
                    return true;
                });
            }

            if (location_switch != null) {
                location_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    WebActivity.ChangeListener = true;
                    return true;
                });
            }


            if (dark_switch != null) {
                dark_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    try {


                        Intent intent = new Intent(requireContext(), SettingsActivity.class);
                        startActivity(intent); // start same activity
                       requireActivity().finish(); // destroy older activity
                       requireActivity().overridePendingTransition(0, 0);
//                            requireActivity().recreate();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    WebActivity.ChangeListener = true;
                    return true;
                });
            }

            if (night_switch != null) {
                night_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                    WebActivity.ChangeListener = true;
                    return true;
                });
                if (fullscr_switch != null) {
                    fullscr_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                        WebActivity.ChangeListener = true;
                        return true;
                    });


                    if (nativ_loader_switch != null) {
                        nativ_loader_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                            WebActivity.ChangeListener = true;
                            return true;
                        });
                        if (blockAds_switch != null) {
                            blockAds_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                                WebActivity.ChangeListener = true;
                                return true;
                            });

                        if (immersive_switch != null) {
                            immersive_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                                WebActivity.ChangeListener = true;
                                return true;
                            });

                            if (permissions_switch != null) {
                                permissions_switch.setOnPreferenceChangeListener((arg0, isChanged) -> {
                                    WebActivity.ChangeListener = true;
                                    return true;
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}}}}}}