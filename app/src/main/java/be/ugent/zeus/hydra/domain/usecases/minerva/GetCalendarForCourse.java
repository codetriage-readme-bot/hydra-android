package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.AgendaItem;
import be.ugent.zeus.hydra.domain.usecases.UseCase;

import javax.inject.Inject;
import java.util.List;

/**
 * Get all calendar items for a course, optionally only getting future events. They are ordered by date, meaning the
 * earliest event is first.
 *
 * @author Niko Strijbol
 */
public class GetCalendarForCourse implements UseCase<GetCalendarForCourse.Params, LiveData<List<AgendaItem>>> {

    private final AgendaItemRepository agendaItemRepository;

    @Inject
    public GetCalendarForCourse(AgendaItemRepository agendaItemRepository) {
        this.agendaItemRepository = agendaItemRepository;
    }

    @Override
    public LiveData<List<AgendaItem>> execute(Params arguments) {
        return agendaItemRepository.getAllForCourse(arguments.courseId, arguments.onlyFuture);
    }

    public static class Params {
        final String courseId;
        final boolean onlyFuture;

        public Params(String courseId, boolean onlyFuture) {
            this.courseId = courseId;
            this.onlyFuture = onlyFuture;
        }
    }
}
