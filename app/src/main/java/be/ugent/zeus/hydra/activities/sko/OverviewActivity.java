package be.ugent.zeus.hydra.activities.sko;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.activities.common.ToolbarActivity;
import be.ugent.zeus.hydra.fragments.preferences.SkoFragment;
import be.ugent.zeus.hydra.notifications.FirebaseMessageService;
import be.ugent.zeus.hydra.utils.NetworkUtils;
import be.ugent.zeus.hydra.viewpager.SkoPagerAdapter;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * SKO overview activity.
 *
 * @author Niko Strijbol
 */
public class OverviewActivity extends ToolbarActivity {

    private static final String TAG = "SkoOverviewActivity";

    private static final String SKO_WEBSITE = "http://www.studentkickoff.be/";

    public static final String ARG_TAB = "argTab";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sko_main);

        ViewPager viewpager = $(R.id.pager);
        viewpager.setAdapter(new SkoPagerAdapter(getSupportFragmentManager(), this));

        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                HydraApplication app = (HydraApplication) OverviewActivity.this.getApplication();
                app.sendScreenName("SKO tab: " + SkoPagerAdapter.names[position]);
            }
        });

        final AppBarLayout appBarLayout = $(R.id.app_bar_layout);
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                appBarLayout.setExpanded(true);
            }
        });

        TabLayout tabLayout = $(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewpager);

        //Get start position
        int start = getIntent().getIntExtra(ARG_TAB, 0);
        viewpager.setCurrentItem(start, false);

        //Subscribe to topic if needed
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.contains(SkoFragment.PREF_SKO_NOTIFICATION)) {
            Log.i(TAG, "Subscribing to notifications.");
            FirebaseMessaging.getInstance().subscribeToTopic(FirebaseMessageService.SKO_TOPIC);
            prefs.edit().putBoolean(SkoFragment.PREF_SKO_NOTIFICATION, true).apply();
            showNotificationSnackbar();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sko, menu);
        tintToolbarIcons(menu, R.id.sko_visit_website);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sko_visit_website:
                NetworkUtils.maybeLaunchBrowser(this, SKO_WEBSITE);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, PreferenceActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showNotificationSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Meldingen ingeschakeld", Snackbar.LENGTH_LONG);
        snackbar.setAction("Uitschakelen", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(FirebaseMessageService.SKO_TOPIC);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(OverviewActivity.this);
                prefs.edit().putBoolean(SkoFragment.PREF_SKO_NOTIFICATION, false).apply();
            }
        });
        snackbar.show();
    }
}