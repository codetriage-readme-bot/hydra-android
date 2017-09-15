package be.ugent.zeus.hydra.ui.main.minerva;

import android.app.Application;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread;
import be.ugent.zeus.hydra.domain.usecases.minerva.ChangeCourseOrder;
import be.ugent.zeus.hydra.ui.common.ViewUtils;
import be.ugent.zeus.hydra.ui.common.recyclerview.adapters.SearchableDiffAdapter;
import be.ugent.zeus.hydra.ui.common.recyclerview.ordering.ItemDragHelperAdapter;
import be.ugent.zeus.hydra.ui.common.recyclerview.ordering.OnStartDragListener;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import java.util.Collections;

/**
 * Adapts a list of courses.
 *
 * @author Niko Strijbol
 */
class MinervaCourseAdapter extends SearchableDiffAdapter<CourseUnread, MinervaCourseViewHolder> implements ItemDragHelperAdapter {

    private final Application application;
    private final OnStartDragListener startDragListener;

    MinervaCourseAdapter(Application application, OnStartDragListener startDragListener) {
        super(c -> c.getCourse().getTitle().toLowerCase());
        this.startDragListener = startDragListener;
        this.application = application;
    }

    @Override
    public MinervaCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MinervaCourseViewHolder(ViewUtils.inflate(parent, R.layout.item_minerva_course), startDragListener, this);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onMoveCompleted(RecyclerView.ViewHolder viewHolder) {
        ChangeCourseOrder changeCourseOrder = HydraApplication.getComponent(application).changeCourseOrder();
        AsyncTask.execute(() -> changeCourseOrder.execute(StreamSupport.stream(items).map(CourseUnread::getCourse).collect(Collectors.toList())));
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return !this.isSearching();
    }
}