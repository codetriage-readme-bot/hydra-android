package be.ugent.zeus.hydra.data.database.minerva;

import android.content.Context;
import android.support.annotation.Nullable;

import be.ugent.zeus.hydra.data.database.Dao;
import be.ugent.zeus.hydra.data.sync.SyncCallback;
import be.ugent.zeus.hydra.data.network.minerva.models.AgendaItem;
import be.ugent.zeus.hydra.data.network.minerva.course.Course;
import java8.util.stream.RefStreams;
import java8.util.stream.Stream;
import org.threeten.bp.Instant;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The database access object (DAO), to work with Minerva calendar items.
 *
 * @author Niko Strijbol
 */
@Deprecated
public class AgendaDao extends Dao implements SyncCallback<AgendaItem, Integer> {

    /**
     * @param context The application context.
     */
    public AgendaDao(Context context) {
        super(context);
    }

    /**
     * Delete all data.
     */
    public void deleteAll() {
    }

    /**
     * Delete all calendar items that match the given ID's. If the collection is null, everything will be deleted.
     *
     * @param ids The ID's to delete, or null for everything.
     */
    public void delete(Collection<Integer> ids) {
    }

    /**
     * Update existing items. Every column will be replaced with the value from the corresponding object.
     *
     * @param items The items to update.
     */
    public void update(Collection<AgendaItem> items) {
    }

    /**
     * Add new items to the database.
     *
     * @param items The items to insert.
     */
    public void insert(Collection<AgendaItem> items) {
    }

    /**
     * Get a list of ids of the agenda items for a course in the database.
     *
     * @param course The course.
     * @param reverse If the agenda items should be reversed (newest first) or not.
     *
     * @return List of ids in the database.
     */
    public List<AgendaItem> getAgendaForCourse(Course course, boolean reverse, boolean future) {
        return Collections.emptyList();
    }

    public Stream<AgendaItem> getFutureAgenda(Instant instant, @Nullable Instant upperLimit) {
        return RefStreams.empty();
    }

    public Collection<Integer> getAllIds() {
        return Collections.emptyList();
    }

    public Collection<Long> getCalendarIdsForIds(Collection<Integer> agendaIds) {
        return Collections.emptyList();
    }

    /**
     * Get a single agenda item by it's ID.
     *
     * @param id The ID.
     *
     * @return The agenda item or null if not found.
     */
    @Nullable
    public AgendaItem getItem(int id) {
        return new AgendaItem();
    }
}