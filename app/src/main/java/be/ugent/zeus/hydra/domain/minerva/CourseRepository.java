package be.ugent.zeus.hydra.domain.minerva;

import android.arch.lifecycle.LiveData;

import be.ugent.zeus.hydra.domain.FullRepository;

import java.util.List;

/**
 * @author Niko Strijbol
 */
public interface CourseRepository extends FullRepository<String, Course> {

    List<Course> getIn(List<String> ids);

    LiveData<List<Course>> getInLive(List<String> ids);

    LiveData<List<CourseUnread>> getAllAndUnreadInOrder();

}