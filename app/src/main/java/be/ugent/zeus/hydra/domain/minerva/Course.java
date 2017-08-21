package be.ugent.zeus.hydra.domain.minerva;

import java8.lang.Integers;
import java8.util.Objects;

/**
 * @author Niko Strijbol
 */
public final class Course implements Comparable<Course> {

    private String id;
    private String code;
    private String title;
    private String description;
    private Tutor tutor;
    private AcademicYear year;
    private int order;

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

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public void setYear(AcademicYear year) {
        this.year = year;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(order, this.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order);
    }

    @Override
    public int compareTo(Course course) {
        return Integers.compare(this.order, course.order);
    }
}
