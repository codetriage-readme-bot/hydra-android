package be.ugent.zeus.hydra.sko.studentvillage;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.common.arch.observers.AdapterObserver;
import be.ugent.zeus.hydra.common.arch.observers.ErrorObserver;
import be.ugent.zeus.hydra.common.arch.observers.ProgressObserver;
import be.ugent.zeus.hydra.common.ui.BaseActivity;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

/**
 * Show a list of exhibitors in the student village.
 *
 * @author Niko Strijbol
 */
public class ExhibitorFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ExhibitorFragment";

    private ExhibitorViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sko_village, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExhibitorAdapter adapter = new ExhibitorAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.requestFocus();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.sko_red);
        refreshLayout.setOnRefreshListener(this);

        RecyclerFastScroller scroller = view.findViewById(R.id.fast_scroller);
        scroller.attachRecyclerView(recyclerView);

        model = ViewModelProviders.of(this).get(ExhibitorViewModel.class);
        model.getData().observe(this, ErrorObserver.with(this::onError));
        model.getData().observe(this, new ProgressObserver<>(view.findViewById(R.id.progress_bar)));
        model.getData().observe(this, new AdapterObserver<>(adapter));
        model.getRefreshing().observe(this, refreshLayout::setRefreshing);
        recyclerView.requestFocus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
        BaseActivity activity = (BaseActivity) getActivity();
        BaseActivity.tintToolbarIcons(activity.getToolbar(), menu, R.id.action_refresh);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {
            onRefresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        model.onRefresh();
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, "Error while getting data.", throwable);
        Snackbar.make(getView(), getString(R.string.failure), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.again), v -> onRefresh())
                .show();
    }
}