package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread;
import be.ugent.zeus.hydra.domain.usecases.UseCase;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.CourseRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Get all courses, with the corresponding number of unread announcements for each course. The courses are ordered first
 * by their user-defined order, and then by name.
 *
 * This use case runs async, meaning you don't need to run it in a separate thread.
 *
 * @author Niko Strijbol
 */
public class GetCoursesWithUnreadCount implements UseCase<Void, LiveData<List<CourseUnread>>> {

    private final CourseRepository repository;

    @Inject
    public GetCoursesWithUnreadCount(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public LiveData<List<CourseUnread>> execute(Void arguments) {
        return repository.getAllAndUnreadInOrder();
    }
}