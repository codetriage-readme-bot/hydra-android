package be.ugent.zeus.hydra.ui.minerva.overview;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.ui.common.html.Utils;
import be.ugent.zeus.hydra.utils.NetworkUtils;

import java.util.Locale;

/**
 * Show information about a course, including the description.
 *
 * @author Niko Strijbol
 */
public class CourseInfoFragment extends Fragment {

    private static final String ARG_COURSE_ID = "argCourseId";

    private static final String URL = "https://studiegids.ugent.be/%d/NL/studiefiches/%s.pdf";

    private String courseId;

    public static CourseInfoFragment newInstance(String courseId) {
        CourseInfoFragment fragment = new CourseInfoFragment();
        Bundle data = new Bundle();
        data.putString(ARG_COURSE_ID, courseId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = getArguments().getString(ARG_COURSE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minerva_course_info, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        TextView courseTitle = v.findViewById(R.id.course_title);
        TextView courseCode = v.findViewById(R.id.course_code);
        TextView courseTutor = v.findViewById(R.id.course_tutor);
        TextView courseYear = v.findViewById(R.id.course_year);
        TextView courseDescription = v.findViewById(R.id.course_description);
        TextView courseFiche = v.findViewById(R.id.course_fiche);

        View contentWrapper = v.findViewById(R.id.content_wrapper);
        View progressBar = v.findViewById(R.id.progress_bar);

        CourseViewModel viewModel = ViewModelProviders.of(getActivity()).get(CourseViewModel.class);
        viewModel.getData(courseId).observe(this, course -> {
            progressBar.setVisibility(View.GONE);
            contentWrapper.setVisibility(View.VISIBLE);
            courseTitle.setText(course.getTitle());
            courseCode.setText(course.getCode());
            courseTutor.setText(Utils.fromHtml(course.getTutor().getName()));
            courseYear.setText(getAcademicYear(course));

            if (TextUtils.isEmpty(course.getDescription())) {
                v.findViewById(R.id.course_description_header).setVisibility(View.GONE);
            } else {
                courseDescription.setText(Utils.fromHtml(course.getDescription()));
            }

            final String url = getUrl(course);
            if (url == null) {
                v.findViewById(R.id.course_fiche_header).setVisibility(View.GONE);
                courseFiche.setVisibility(View.GONE);
            } else {
                courseFiche.setOnClickListener(view -> NetworkUtils.maybeLaunchBrowser(view.getContext(), url));
            }
        });
    }

    private String getAcademicYear(Course course) {
        if (course.getYear() == null) {
            return getContext().getString(R.string.minerva_course_unknown_year);
        } else {
            return course.getYear().toString();
        }
    }

    private String getUrl(Course course) {
        if (course.getCode() == null || course.getYear() == null) {
            return null;
        }

        // We use the US locale since we are just formatting number without anything special.
        return String.format(Locale.US, URL, course.getYear().getStartYear().getValue(), course.getCode());
    }
}