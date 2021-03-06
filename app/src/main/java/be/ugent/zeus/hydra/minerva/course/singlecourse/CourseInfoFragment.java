package be.ugent.zeus.hydra.minerva.course.singlecourse;

import android.accounts.Account;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.common.database.RepositoryFactory;
import be.ugent.zeus.hydra.common.sync.SyncUtils;
import be.ugent.zeus.hydra.common.ui.html.Utils;
import be.ugent.zeus.hydra.minerva.account.AccountUtils;
import be.ugent.zeus.hydra.minerva.account.MinervaConfig;
import be.ugent.zeus.hydra.minerva.common.sync.MinervaAdapter;
import be.ugent.zeus.hydra.minerva.course.Course;
import be.ugent.zeus.hydra.minerva.course.CourseRepository;
import be.ugent.zeus.hydra.minerva.course.Module;
import be.ugent.zeus.hydra.utils.NetworkUtils;
import org.threeten.bp.LocalDate;

import java.util.Locale;

/**
 * Show information about a course, including the description.
 *
 * @author Niko Strijbol
 */
public class CourseInfoFragment extends Fragment {

    private static final String TAG = "CourseInfoFragment";
    private static final String ARG_COURSE = "argCourse";

    private static final String URL = "https://studiegids.ugent.be/%d/NL/studiefiches/%s.pdf";
    private static final int DEFAULT_YEAR = LocalDate.now().getYear();

    private Course course;
    private boolean courseWasModified;

    public static CourseInfoFragment newInstance(Course course) {
        CourseInfoFragment fragment = new CourseInfoFragment();
        Bundle data = new Bundle();
        data.putParcelable(ARG_COURSE, course);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = getArguments().getParcelable(ARG_COURSE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minerva_course_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        TextView courseTitle = v.findViewById(R.id.course_title);
        TextView courseCode = v.findViewById(R.id.course_code);
        TextView courseTutor = v.findViewById(R.id.course_tutor);
        TextView courseYear = v.findViewById(R.id.course_year);
        TextView courseDescription = v.findViewById(R.id.course_description);
        TextView courseFiche = v.findViewById(R.id.course_fiche);
        View announcementHeader = v.findViewById(R.id.course_settings_announcement_header);
        View announcementDescription = v.findViewById(R.id.course_settings_announcement_description);
        View calendarHeader = v.findViewById(R.id.course_settings_calendar_header);
        View calendarDescription = v.findViewById(R.id.course_settings_calendar_description);

        courseTitle.setText(course.getTitle());
        courseCode.setText(course.getCode());
        courseTutor.setText(Utils.fromHtml(course.getTutorName()));
        courseYear.setText(getAcademicYear());

        if (TextUtils.isEmpty(course.getDescription())) {
            v.findViewById(R.id.course_description_header).setVisibility(View.GONE);
        } else {
            courseDescription.setText(Utils.fromHtml(course.getDescription()));
        }

        final String url = getUrl();
        if (url == null) {
            v.findViewById(R.id.course_fiche_header).setVisibility(View.GONE);
            courseFiche.setVisibility(View.GONE);
        } else {
            courseFiche.setOnClickListener(view -> NetworkUtils.maybeLaunchBrowser(view.getContext(), url));
        }

        if (course.getEnabledModules().contains(Module.ANNOUNCEMENTS)) {
            Switch announcementSwitch = v.findViewById(R.id.course_settings_announcement_switch);
            announcementSwitch.setChecked(course.getIgnoreAnnouncements());
            announcementSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                course.setIgnoreAnnouncements(isChecked);
                courseWasModified = true;
            });
            announcementHeader.setOnClickListener(vi -> announcementSwitch.toggle());
        } else {
            announcementHeader.setVisibility(View.GONE);
            announcementDescription.setVisibility(View.GONE);
        }

        if (course.getEnabledModules().contains(Module.CALENDAR)) {
            Switch calendarSwitch = v.findViewById(R.id.course_settings_calendar_switch);
            calendarSwitch.setChecked(course.getIgnoreCalendar());
            calendarSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                course.setIgnoreCalendar(isChecked);
                courseWasModified = true;
            });
            calendarHeader.setOnClickListener(vi -> calendarSwitch.toggle());
        } else {
            calendarHeader.setVisibility(View.GONE);
            calendarDescription.setVisibility(View.GONE);
        }
    }

    private String getAcademicYear() {
        if (course.getAcademicYear() == 0) {
            return getContext().getString(R.string.minerva_course_unknown_year);
        } else {
            return String.valueOf(course.getAcademicYear());
        }
    }

    private String getUrl() {
        int year = course.getAcademicYear();
        if (year == 0) {
            year = DEFAULT_YEAR;
        }

        if (course.getCode() == null) {
            return null;
        }

        // We use the US locale since we are just formatting number without anything special.
        return String.format(Locale.US, URL, year, course.getCode());
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save the course if necessary.
        if (courseWasModified) {
            Log.d(TAG, "Course was modified, saving...");
            CourseRepository repository = RepositoryFactory.getCourseRepository(getContext());
            Account account = AccountUtils.getAccount(getContext());
            AsyncTask.execute(() -> {
                repository.update(course);
                Bundle bundle = new Bundle();
                // Don't sync the announcements, this is not needed.
                bundle.putBoolean(MinervaAdapter.SYNC_ANNOUNCEMENTS, false);
                SyncUtils.requestSync(account, MinervaConfig.SYNC_AUTHORITY, bundle);
            });
            Toast.makeText(getContext(), R.string.minerva_course_info_settings_wait, Toast.LENGTH_LONG).show();
            courseWasModified = false;
        }
    }
}