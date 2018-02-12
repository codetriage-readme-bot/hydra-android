package be.ugent.zeus.hydra.minerva.overview;

import android.accounts.*;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import be.ugent.zeus.hydra.MainActivity;
import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.common.sync.SyncUtils;
import be.ugent.zeus.hydra.common.ui.recyclerview.ResultStarter;
import be.ugent.zeus.hydra.minerva.account.AccountUtils;
import be.ugent.zeus.hydra.minerva.account.MinervaConfig;
import be.ugent.zeus.hydra.minerva.common.sync.MinervaAdapter;
import be.ugent.zeus.hydra.minerva.common.sync.SyncBroadcast;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;

/**
 * Overview fragment for Minerva in the main activity. Manages logging in, and manages showing the tabs.
 * Which tabs are displayed is managed by {@link MinervaPagerAdapter}.
 *
 * @author Niko Strijbol
 */
public class OverviewFragment extends Fragment implements ResultStarter, MainActivity.ScheduledRemovalListener {

    private static final String TAG = "OverviewFragment";
    private static final int REQUEST_ANNOUNCEMENT_CHANGED_CODE = 56532;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MinervaPagerAdapter minervaPagerAdapter;

    private View authWrapper;
    private AccountManager manager;
    private Snackbar syncBar;

    private View loginText;
    private View logoutText;

    private AccountViewModel accountViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_minerva_overview, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialise the local stuff, but don't do anything related to the activity yet.
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        manager = AccountManager.get(getContext());

        Button authorize = view.findViewById(R.id.authorize);
        authorize.setOnClickListener(v -> accountViewModel.getAccountStateLiveData().setState(AccountState.LOGGING_IN));

        loginText = view.findViewById(R.id.login_text);
        logoutText = view.findViewById(R.id.logout_text);
        authWrapper = view.findViewById(R.id.auth_wrapper);
        viewPager = view.findViewById(R.id.pager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getActivity() != null;

        // Initialise the activity-related stuff.
        tabLayout = getActivity().findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        minervaPagerAdapter = new MinervaPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(minervaPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActivity().invalidateOptionsMenu();
            }
        });

        // Bind the observer.
        accountViewModel.getAccountStateLiveData().observe(this, accountState -> {
            if (accountState == null) return; // Ignore null.
            switch (accountState) {
                case LOGGED_IN:
                    onLoggedIn();
                    return;
                case LOGGING_OUT:
                    onLoggingOut();
                    return;
                case LOGGED_OUT:
                    onLoggedOut();
                    return;
                case LOGGING_IN:
                    onStartLoggingIn(); // We handle this ourselves.
                    return;
                default:
                    throw new IllegalStateException("Non-exhaustive switch.");
            }
        });

        if (!accountViewModel.getAccountStateLiveData().isInitialised()) {
            Log.d(TAG, "onActivityCreated: not initialised");
            // The chance that the account is logging out is very small, so we ignore that.
            accountViewModel.getAccountStateLiveData().setState(isLoggedIn() ? AccountState.LOGGED_IN : AccountState.LOGGED_OUT);
        }
    }

    /**
     * Called when the user has just logged in.
     */
    private void onLoggedIn() {
        Log.d(TAG, "onLoggedIn");
        authWrapper.setVisibility(View.GONE);
        minervaPagerAdapter.setLoggedIn(true);
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        getActivity().invalidateOptionsMenu();
    }

    /**
     * Called when the user has logged out.
     */
    private void onLoggedOut() {
        Log.d(TAG, "onLoggedOut");
        authWrapper.setVisibility(View.VISIBLE);
        loginText.setVisibility(View.VISIBLE);
        logoutText.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        minervaPagerAdapter.setLoggedIn(false);
    }

    /**
     * Called when the user is logging out.
     */
    private void onLoggingOut() {
        Log.d(TAG, "onLoggingOut");
        authWrapper.setVisibility(View.VISIBLE);
        loginText.setVisibility(View.GONE);
        logoutText.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        minervaPagerAdapter.setLoggedIn(false);
        getActivity().invalidateOptionsMenu();
        dismissSyncBar();
    }

    /**
     * Called when the user wants to log in.
     */
    private void onStartLoggingIn() {
        Log.d(TAG, "onStartLoggingIn");
        AccountManagerCallback<Bundle> callback = future -> {
            try {
                Bundle result = future.getResult();
                Log.d(TAG, "Account " + result.getString(AccountManager.KEY_ACCOUNT_NAME) + " was created.");
                // Handle the actions for the added account.
                requestFirstSync();
                accountViewModel.getAccountStateLiveData().setState(AccountState.LOGGED_IN);
            } catch (OperationCanceledException e) {
                Toast.makeText(getContext().getApplicationContext(), R.string.minerva_no_permission, Toast.LENGTH_LONG).show();
            } catch (IOException | AuthenticatorException e) {
                Log.w(TAG, "Account not added.", e);
            }
        };

        manager.addAccount(
                MinervaConfig.ACCOUNT_TYPE,
                MinervaConfig.DEFAULT_SCOPE,
                null,
                null,
                getActivity(),
                callback,
                null
        );
    }

    private void requestFirstSync() {
        Log.d(TAG, "requestFirstSync");
        //Get an account
        Account account = AccountUtils.getAccount(getContext());

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN,  null);

        //Request first sync
        Log.d(TAG, "Requesting first sync...");
        Bundle bundle = new Bundle();
        bundle.putBoolean(MinervaAdapter.EXTRA_FIRST_SYNC, true);
        SyncUtils.requestSync(account, MinervaConfig.SYNC_AUTHORITY, bundle);
    }

    /**
     * @return True if the user is logged in to Minerva.
     */
    private boolean isLoggedIn() {
        return AccountUtils.hasAccount(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (isLoggedIn()) {
            inflater.inflate(R.menu.menu_main_minerva_overview, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                accountViewModel.getAccountStateLiveData().logout(getContext());
                return true;
            case R.id.action_sync_all:
                manualSync();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void manualSync() {
        Account account = AccountUtils.getAccount(getContext());
        Bundle bundle = new Bundle();
        Toast.makeText(getContext(), R.string.minerva_syncing, Toast.LENGTH_LONG)
                .show();
        SyncUtils.requestSync(account, MinervaConfig.SYNC_AUTHORITY, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.registerReceiver(syncReceiver, SyncBroadcast.getBroadcastFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.unregisterReceiver(syncReceiver);
        dismissSyncBar();
    }

    private void dismissSyncBar() {
        if (syncBar != null && syncBar.isShown()) {
            syncBar.dismiss();
            syncBar = null;
        }
    }

    //This will only be called if manually set to send broadcasts.
    private BroadcastReceiver syncReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            assert getView() != null;
            switch (intent.getAction()) {
                case SyncBroadcast.SYNC_COURSES:
                    Log.d(TAG, "Courses!");
                    ensureSyncStatus(getString(R.string.minerva_sync_getting_courses));
                    return;
                case SyncBroadcast.SYNC_AGENDA:
                    Log.d(TAG, "Calendar!");
                    ensureSyncStatus(getString(R.string.minerva_sync_getting_calendar));
                    return;
                case SyncBroadcast.SYNC_CANCELLED:
                    Log.d(TAG, "Cancelled!");
                    ensureSyncStatus(getString(R.string.minerva_sync_cancelled), Snackbar.LENGTH_LONG);
                    return;
                case SyncBroadcast.SYNC_DONE:
                    Log.d(TAG, "Done!");
                    ensureSyncStatus(getString(R.string.minerva_sync_done), Snackbar.LENGTH_SHORT);
                    return;
                case SyncBroadcast.SYNC_ERROR:
                    Log.d(TAG, "Error");
                    ensureSyncStatus(getString(R.string.failure), Snackbar.LENGTH_LONG);
                    return;
                case SyncBroadcast.SYNC_PROGRESS_WHATS_NEW:
                    int current = intent.getIntExtra(SyncBroadcast.ARG_SYNC_PROGRESS_CURRENT, 0);
                    int total = intent.getIntExtra(SyncBroadcast.ARG_SYNC_PROGRESS_TOTAL, 0);
                    Log.d(TAG, "Progress: handled " + current + " of " + total);
                    ensureSyncStatus(getString(R.string.minerva_sync_progress, current, total));
            }
        }
    };

    /**
     * Ensure the sync status is enabled, and set the the given text on the snackbar.
     *
     * @param text To display on the snackbar.
     */
    private void ensureSyncStatus(String text, int duration) {
        assert getView() != null;
        if (syncBar == null) {
            authWrapper.setVisibility(View.GONE);
            syncBar = Snackbar.make(getView(), text, Snackbar.LENGTH_INDEFINITE);
            syncBar.show();
        } else {
            syncBar.setText(text);
        }
        syncBar.setDuration(duration);
        syncBar.show();
    }

    /**
     * Ensure the sync status is enabled, and set the the given text on the snackbar.
     *
     * @param text To display on the snackbar.
     */
    private void ensureSyncStatus(String text) {
        ensureSyncStatus(text, Snackbar.LENGTH_INDEFINITE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the result to the nested fragments.
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public int getRequestCode() {
        return REQUEST_ANNOUNCEMENT_CHANGED_CODE;
    }

    @Override
    public void onRemovalScheduled() {
        // Propagate this to the children.
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment instanceof MainActivity.ScheduledRemovalListener) {
                ((MainActivity.ScheduledRemovalListener) fragment).onRemovalScheduled();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabLayout.setVisibility(View.GONE);
    }
}