package be.ugent.zeus.hydra.minerva.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import android.provider.Settings;
import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.minerva.account.AccountUtils;
import be.ugent.zeus.hydra.minerva.account.MinervaConfig;
import be.ugent.zeus.hydra.common.sync.SyncUtils;
import be.ugent.zeus.hydra.minerva.common.sync.MinervaAdapter;

/**
 * Preferences for Minerva-related things.
 *
 * @author Niko Strijbol
 */
public class MinervaPreferenceFragment extends PreferenceFragment {

    public static final String PREF_SYNC_FREQUENCY = "pref_minerva_sync_announcement_frequency";
    public static final String PREF_ANNOUNCEMENT_NOTIFICATION = "pref_minerva_announcement_notification";
    public static final String PREF_ANNOUNCEMENT_NOTIFICATION_EMAIL = "pref_minerva_announcement_notification_email";
    public static final String PREF_USE_MOBILE_URL = "pref_minerva_use_mobile_url";

    public static final String PREF_DETECT_DUPLICATES = "pref_minerva_detect_duplicates";

    public static final String PREF_PREFIX_EVENT_TITLES = "pref_minerva_prefix_event_titles";
    public static final String PREF_PREFIX_EVENT_ACRONYM = "pref_minerva_prefix_event_acronym";

    //In seconds
    public static final String PREF_DEFAULT_SYNC_FREQUENCY = "86400";
    public static final boolean PREF_DEFAULT_ANNOUNCEMENT_NOTIFICATION_EMAIL = false;

    private int oldInterval;
    private int newInterval;

    private boolean oldDetectDuplicates;
    private boolean newDetectDuplicates;

    private boolean oldPrefixEventTitles;
    private boolean newPrefixEventTitles;

    private boolean oldPrefixAcronyms;
    private boolean newPrefixAcronyms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_minerva);

        // Open the account settings for the correct account.
        findPreference("account_settings").setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
            intent.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{MinervaConfig.SYNC_AUTHORITY});
            getActivity().startActivity(intent);
            return true;
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        oldInterval = Integer.parseInt(preferences.getString(PREF_SYNC_FREQUENCY, PREF_DEFAULT_SYNC_FREQUENCY));
        newInterval = oldInterval;

        oldDetectDuplicates = preferences.getBoolean(PREF_DETECT_DUPLICATES, false);
        newDetectDuplicates = oldDetectDuplicates;

        oldPrefixEventTitles = preferences.getBoolean(PREF_PREFIX_EVENT_TITLES, false);
        newPrefixEventTitles = oldPrefixEventTitles;

        oldPrefixAcronyms = preferences.getBoolean(PREF_PREFIX_EVENT_ACRONYM, true);
        newPrefixAcronyms = oldPrefixAcronyms;

        Preference intervalPreference = findPreference(PREF_SYNC_FREQUENCY);
        Preference detectPreference = findPreference(PREF_DETECT_DUPLICATES);
        Preference prefixTitles = findPreference(PREF_PREFIX_EVENT_TITLES);
        Preference prefixAbbreviations = findPreference(PREF_PREFIX_EVENT_ACRONYM);
        prefixAbbreviations.setEnabled(oldPrefixEventTitles);

        intervalPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            newInterval = Integer.parseInt((String) newValue);
            return true;
        });

        detectPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            newDetectDuplicates = (boolean) newValue;
            return true;
        });

        prefixTitles.setOnPreferenceChangeListener((preference, newValue) -> {
            newPrefixEventTitles = (boolean) newValue;
            prefixAbbreviations.setEnabled(newPrefixEventTitles);
            return true;
        });

        prefixAbbreviations.setOnPreferenceChangeListener((preference, newValue) -> {
            newPrefixAcronyms = (boolean) newValue;
            return true;
        });

        if(!AccountUtils.hasAccount(getAppContext())) {
            intervalPreference.setEnabled(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        boolean hasAccount = AccountUtils.hasAccount(getAppContext());
        if (hasAccount && oldInterval != newInterval) {
            SyncUtils.changeSyncFrequency(getAppContext(), MinervaConfig.SYNC_AUTHORITY, newInterval);
        }
        if (hasAccount && (oldDetectDuplicates != newDetectDuplicates || oldPrefixEventTitles != newPrefixEventTitles || oldPrefixAcronyms != newPrefixAcronyms)) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(MinervaAdapter.SYNC_ANNOUNCEMENTS, false);
            SyncUtils.requestSync(AccountUtils.getAccount(getAppContext()), MinervaConfig.SYNC_AUTHORITY, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HydraApplication.getApplication(getActivity()).sendScreenName("Settings > Minerva");
    }

    private Context getAppContext() {
        return getActivity().getApplicationContext();
    }
}