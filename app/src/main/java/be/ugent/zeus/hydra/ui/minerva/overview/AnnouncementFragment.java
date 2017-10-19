package be.ugent.zeus.hydra.ui.minerva.overview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.data.sync.minerva.helpers.NotificationHelper;
import be.ugent.zeus.hydra.domain.entities.minerva.Announcement;
import be.ugent.zeus.hydra.repository.observers.AdapterObserver;
import be.ugent.zeus.hydra.repository.observers.ErrorObserver;
import be.ugent.zeus.hydra.repository.observers.ProgressObserver;
import be.ugent.zeus.hydra.repository.observers.SuccessObserver;
import be.ugent.zeus.hydra.ui.common.recyclerview.EmptyViewObserver;
import be.ugent.zeus.hydra.ui.common.recyclerview.ResultStarter;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Show Minerva announcements.
 *
 * This fragment will call {@link android.app.Activity#setResult(int)} when appropriate. See the documentation
 * of {@link CourseActivity} for more information.
 *
 * @author Niko Strijbol
 */
public class AnnouncementFragment extends Fragment implements ResultStarter {

    private static final String TAG = "AnnouncementFragment";
    private static final String ARG_COURSE_ID = "argCourseId";

    private static final int ANNOUNCEMENT_RESULT_CODE = 5555;

    private String courseId;

    public static AnnouncementFragment newInstance(String courseId) {
        AnnouncementFragment fragment = new AnnouncementFragment();
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
        return inflater.inflate(R.layout.fragment_minerva_course_announcements, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnnouncementAdapter adapter = new AnnouncementAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.registerAdapterDataObserver(new EmptyViewObserver(recyclerView, view.findViewById(R.id.no_data_view)));

        RecyclerFastScroller scroller = view.findViewById(R.id.fast_scroller);
        scroller.attachRecyclerView(recyclerView);

        AnnouncementViewModel viewModel = ViewModelProviders.of(this).get(AnnouncementViewModel.class);
        viewModel.setCourse(courseId);
        viewModel.getData().observe(this, ErrorObserver.with(this::onError));
        viewModel.getData().observe(this, new AdapterObserver<>(adapter));
        viewModel.getData().observe(this, new ProgressObserver<>(view.findViewById(R.id.progress_bar)));

        viewModel.getData().observe(this, new SuccessObserver<List<Announcement>>() {
            @Override
            protected void onSuccess(List<Announcement> data) {
                NotificationManagerCompat manager = NotificationManagerCompat.from(getContext());
                NotificationHelper.cancel(manager, courseId, data);
            }
        });
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, "Error while getting data.", throwable);
        Snackbar.make(getView(), getString(R.string.failure), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ANNOUNCEMENT_RESULT_CODE && resultCode == RESULT_OK) {
            // One of the announcements was marked as read, so update the UI.
//            viewModel.requestRefresh();
//            Intent intent = new Intent();
//            // TODO: prevent the fragment from depending on the activity.
//            intent.putExtra(CourseActivity.RESULT_ANNOUNCEMENT_UPDATED, true);
//            getActivity().setResult(RESULT_OK, intent);
        }
    }

    @Override
    public int getRequestCode() {
        return ANNOUNCEMENT_RESULT_CODE;
    }
}