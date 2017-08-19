package be.ugent.zeus.hydra.domain.minerva;

import java8.util.Objects;

/**
 * @author Niko Strijbol
 */
public final class Course {

    private String id;
    private String code;
    private String title;
    private String description;
    private Tutor tutor;
    private AcademicYear year;

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public AcademicYear getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
