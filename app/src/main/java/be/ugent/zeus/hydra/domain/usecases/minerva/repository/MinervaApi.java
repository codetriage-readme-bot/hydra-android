package be.ugent.zeus.hydra.domain.usecases.minerva.repository;


import be.ugent.zeus.hydra.domain.entities.minerva.Course;
import be.ugent.zeus.hydra.domain.requests.Result;

import java.util.List;

/**
 * Provides access to the remote API for Minerva.
 *
 * @author Niko Strijbol
 */
public interface MinervaApi {
    Result<List<Course>> getAllCourses();
}