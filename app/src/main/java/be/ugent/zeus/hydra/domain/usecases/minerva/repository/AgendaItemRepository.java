package be.ugent.zeus.hydra.domain.usecases.minerva.repository;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;
import be.ugent.zeus.hydra.domain.usecases.FullRepository;
import org.threeten.bp.ZonedDateTime;

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
     * @param courseId   The ID of the course.
     * @param onlyFuture True if only calendar items in the future should be fetched.
     *
     * @return The items.
     */
    LiveData<List<AgendaItem>> getAllForCourse(String courseId, boolean onlyFuture);

    /**
     * Get all items between two dates. The lower date is inclusive, the upper date is exclusive. More formal, we can
     * express it as {@code ∀ item ∈ results: lower ≤ item.date < upper}.
     *
     * @param lower  The lower bound, inclusive.
     * @param higher The upper bound, exclusive.
     *
     * @return The results.
     */
    LiveData<List<AgendaItem>> getBetween(ZonedDateTime lower, ZonedDateTime higher);
}