package be.ugent.zeus.hydra.domain.usecases.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.usecases.FullRepository;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.entities.minerva.CourseUnread;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public interface CourseRepository extends FullRepository<String, Course> {

    List<Course> getIn(List<String> ids);

    LiveData<List<Course>> getInLive(List<String> ids);

    LiveData<List<CourseUnread>> getAllAndUnreadInOrder();
}