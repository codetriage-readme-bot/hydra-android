package be.ugent.zeus.hydra.domain.entities.minerva;

import java8.util.Objects;

/**
 * Contains a course and the number of unread announcements for that course.
 *
 * @author Niko Strijbol
 */
public final class CourseUnread {

    private Course course;
    private int unreadAnnouncements;

    public Course getCourse() {
        return course;
    }

    public int getUnreadAnnouncements() {
        return unreadAnnouncements;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setUnreadAnnouncements(int unreadAnnouncements) {
        this.unreadAnnouncements = unreadAnnouncements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseUnread that = (CourseUnread) o;
        return unreadAnnouncements == that.unreadAnnouncements &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, unreadAnnouncements);
    }
}