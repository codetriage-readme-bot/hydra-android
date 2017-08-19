package be.ugent.zeus.hydra.domain.minerva;

import java8.util.Objects;
import org.threeten.bp.ZonedDateTime;

/**
 * Represents an announcement from Minerva.
 *
 * This class correctly implements {@link #equals(Object)} and {@link #hashCode()}.
 *
 * @author Niko Strijbol
 */
public final class Announcement {

    private String title;
    private String content;
    private boolean wasEmailSent;
    private int id;
    private Tutor lecturer;
    private ZonedDateTime lastEditedAt;
    private ZonedDateTime readAt;
    private Course course;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isWasEmailSent() {
        return wasEmailSent;
    }

    public void setWasEmailSent(boolean wasEmailSent) {
        this.wasEmailSent = wasEmailSent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tutor getLecturer() {
        return lecturer;
    }

    public void setLecturer(Tutor lecturer) {
        this.lecturer = lecturer;
    }

    public ZonedDateTime getLastEditedAt() {
        return lastEditedAt;
    }

    public void setLastEditedAt(ZonedDateTime lastEditedAt) {
        this.lastEditedAt = lastEditedAt;
    }

    public ZonedDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(ZonedDateTime readAt) {
        this.readAt = readAt;
    }

    /**
     * @return Returns true if the announcement was read.
     */
    public boolean isRead() {
        return readAt != null;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Two instances of this class are considered equal when they have the same {@link #getId()} and
     * they have the same {@link #getReadAt()} status.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return id == that.id && Objects.equals(readAt, that.readAt);
    }

    /**
     * Instances with the same {@link #getId()} and{@link #getReadAt()} will produce the same hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, readAt);
    }
}