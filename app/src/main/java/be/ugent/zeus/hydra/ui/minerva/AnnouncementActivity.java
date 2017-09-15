package be.ugent.zeus.hydra.ui.minerva;

import android.app.TaskStackBuilder;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.domain.usecases.minerva.MarkAnnouncementAsRead;
import be.ugent.zeus.hydra.repository.observers.ErrorObserver;
import be.ugent.zeus.hydra.repository.observers.SuccessObserver;
import be.ugent.zeus.hydra.ui.common.BaseActivity;
import be.ugent.zeus.hydra.ui.common.html.PicassoImageGetter;
import be.ugent.zeus.hydra.ui.common.html.Utils;
import be.ugent.zeus.hydra.ui.minerva.overview.CourseActivity;
import be.ugent.zeus.hydra.ui.preferences.MinervaFragment;
import be.ugent.zeus.hydra.utils.DateUtils;
import be.ugent.zeus.hydra.utils.NetworkUtils;

/**
 * Show a Minerva announcement.
 * <p>
 * If the announcement was marked as read, the activity will return a result. The resulting intent will a boolean named
 * {@link #RESULT_ANNOUNCEMENT_READ}. True indicates the announcement was marked as read, false indicates the
 * announcement has not been changed. In this second case the activity currently does not return a result, but this
 * might change in the future, so relying on the presence of a result alone is not sufficient.
 *
 * @author Niko Strijbol
 */
public class AnnouncementActivity extends BaseActivity {

    public static final String ARG_ANNOUNCEMENT_ID = "announcement_view_id";
    public static final String RESULT_ANNOUNCEMENT_READ = "be.ugent.zeus.hydra.result.minerva.announcement.read";

    private static final String TAG = "AnnouncementActivity";
    private static final String ONLINE_URL_MOBILE = "https://minerva.ugent.be/mobile/courses/%s/announcement";
    private static final String ONLINE_URL_DESKTOP = "http://minerva.ugent.be/main/announcements/announcements.php?cidReq=%s";

    private Announcement announcement;

    /**
     * Start the activity.
     *
     * @param context The context to start with.
     * @param announcement The announcement to display.
     */
    public static void start(Context context, Announcement announcement) {
        start(context, announcement.getId());
    }

    /**
     * Start the activity.
     *
     * @param context The context.
     * @param announcementId The ID of the announcement to display, as returned by {@link Announcement#getId()}.
     */
    public static void start(Context context, int announcementId) {
        Intent intent = new Intent(context, AnnouncementActivity.class);
        intent.putExtra(ARG_ANNOUNCEMENT_ID, announcementId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minerva_announcement);

        Intent intent = getIntent();
        int announcementId = intent.getIntExtra(ARG_ANNOUNCEMENT_ID, 0);

        AnnouncementViewModel model = ViewModelProviders.of(this).get(AnnouncementViewModel.class);
        model.setId(announcementId);
        model.getData().observe(this, ErrorObserver.with(this::onError));
        model.getData().observe(this, SuccessObserver.with(this::onSuccess));
        // TODO
        //model.getData().observe(this, new ProgressObserver<>(findViewById(R.id.progress_bar)));
    }

    private void onSuccess(Announcement announcement) {
        this.announcement = announcement;
        invalidateOptionsMenu();

        TextView title = findViewById(R.id.title);
        TextView date = findViewById(R.id.date);
        TextView text = findViewById(R.id.text);
        TextView author = findViewById(R.id.author);
        TextView course = findViewById(R.id.course);

        course.setText(announcement.getCourse().getTitle());

        if (announcement.getLecturer() != null) {
            author.setText(announcement.getLecturer().getName());
        }

        if (announcement.getLastEditedAt() != null) {
            date.setText(DateUtils.relativeDateTimeString(announcement.getLastEditedAt(), getApplicationContext()));
        }

        if (announcement.getContent() != null) {
            text.setText(Utils.fromHtml(announcement.getContent(), new PicassoImageGetter(text, getResources(), getApplicationContext())));
            text.setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (announcement.getTitle() != null) {
            title.setText(announcement.getTitle());
        }

        // Mark the announcement as read if it is not already.
        if (!announcement.isRead()) {
            AsyncTask.execute(() -> {
                MarkAnnouncementAsRead useCase = HydraApplication.getComponent(getApplication()).markAnnouncementAsRead();
                useCase.execute(announcement);
            });
        }
    }

    private void onError(Throwable throwable) {
        // Do nothing, as this shouldn't happen.
        Log.e(TAG, "Problem while displaying announcement.", throwable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.minerva_announcement_link:
                // Announcement is guaranteed not null here.
                assert announcement != null;
                NetworkUtils.maybeLaunchBrowser(this, getOnlineUrl());
                return true;
            case android.R.id.home:
                // This is possible, although rarely.
                if (announcement == null) {
                    return super.onOptionsItemSelected(item);
                }
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra(CourseActivity.ARG_COURSE_ID, announcement.getCourse().getId());
                if (NavUtils.shouldUpRecreateTask(this, upIntent) || isTaskRoot()) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // To make sure we go to the correct activity, we add the flag.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // We only want to show the menu if there is a valid announcement.
        if (announcement != null) {
            getMenuInflater().inflate(R.menu.menu_minerva_announcement, menu);
            tintToolbarIcons(menu, R.id.minerva_announcement_link);
        }

        return super.onCreateOptionsMenu(menu);
    }

    private String getOnlineUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(MinervaFragment.PREF_USE_MOBILE_URL, false)) {
            return String.format(ONLINE_URL_MOBILE, announcement.getCourse().getId());
        } else {
            return String.format(ONLINE_URL_DESKTOP, announcement.getCourse().getId());
        }
    }
}