package be.ugent.zeus.hydra;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;

import be.ugent.zeus.hydra.data.ChannelCreator;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import jonathanfinerty.once.Once;

/**
 * The Hydra application.
 *
 * @author Niko Strijbol
 * @author feliciaan
 */
@SuppressWarnings("WeakerAccess")
public class HydraApplication extends Application {

    private static final String TAG = "HydraApplication";

    private Tracker tracker;

    /**
     * Get the application from an activity. The application is cast to this class.
     *
     * @param activity The activity.
     *
     * @return The application.
     */
    public static HydraApplication getApplication(@NonNull Activity activity) {
        return (HydraApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        if (BuildConfig.DEBUG) {
            enableStrictModeInDebug();
        }

        initialiseAnalytics();

        AndroidThreeTen.init(this);
        LeakCanary.install(this);
        Once.initialise(this);

        // Initialize the channels that are needed in the whole app. The channels for Minerva are created when needed.
        createChannels();
    }

    private void initialiseAnalytics() {
        Fabric.with(this, new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(false).build())
                .build()
        );
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            if (BuildConfig.DEBUG) {
                // disable google analytics while debugging
                analytics.setDryRun(true);
            }

            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }

    /**
     * Send a screen name to the analytics.
     *
     * @param screenName The screen name to send.
     */
    public void sendScreenName(String screenName) {
        Tracker t = getDefaultTracker();
        t.setScreenName(screenName);
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * Create notifications channels when needed. TODO: should this move to the SKO activity?
     */
    private void createChannels() {
        ChannelCreator channelCreator = ChannelCreator.getInstance(this);
        channelCreator.createSkoChannel();
        channelCreator.createUrgentChannel();
    }

    /**
     * Used to enable {@link StrictMode} for debug builds.
     */
    private void enableStrictModeInDebug() {

        if (!BuildConfig.DEBUG_ENABLE_STRICT_MODE) {
            return;
        }

        Log.d(TAG, "Enabling strict mode...");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
    }
}