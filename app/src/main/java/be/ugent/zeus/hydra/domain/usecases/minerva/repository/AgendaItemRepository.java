package be.ugent.zeus.hydra.domain.usecases.minerva.repository;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;
import be.ugent.zeus.hydra.domain.usecases.FullRepository;

import java.util.List;

/**
 * Provides access to {@link AgendaItem}s.
 *
 * @author Niko Strijbol
 */
public interface AgendaItemRepository extends FullRepository<Integer, AgendaItem> {

    /**
     * Get all calendar items associated with a certain course.
     *
     * @param courseId The ID of the course.
     * @param onlyFuture True if only calendar items in the future should be fetched.
     *
     * @return The items.
     */
    LiveData<List<AgendaItem>> getAllForCourse(String courseId, boolean onlyFuture);
}