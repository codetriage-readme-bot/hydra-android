package be.ugent.zeus.hydra.data.network.minerva;

import android.content.Context;

import be.ugent.zeus.hydra.data.auth.AccountUtils;
import be.ugent.zeus.hydra.data.network.minerva.course.CourseMapper;
import be.ugent.zeus.hydra.data.network.minerva.course.CoursesMinervaRequest;
import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.requests.Result;
import be.ugent.zeus.hydra.domain.usecases.minerva.repository.MinervaApi;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Niko Strijbol
 */
@Singleton
public class MinervaRequestApi implements MinervaApi {

    private final Context context;
    private final CourseMapper courseMapper;

    @Inject
    public MinervaRequestApi(Context context, CourseMapper courseMapper) {
        this.context = context;
        this.courseMapper = courseMapper;
    }

    @Override
    public Result<List<Course>> getAllCourses() {
        return new CoursesMinervaRequest(context, AccountUtils.getAccount(context)).performRequest(null)
                .map(courses -> StreamSupport.stream(courses.getCourses())
                        .map(courseMapper::courseToCourse)
                        .collect(Collectors.toList()));
    }
}