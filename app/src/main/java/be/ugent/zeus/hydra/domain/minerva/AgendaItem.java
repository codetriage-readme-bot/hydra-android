package be.ugent.zeus.hydra.domain.minerva;

import java8.util.Objects;
import org.threeten.bp.ZonedDateTime;

/**
 * @author Niko Strijbol
 */
public final class AgendaItem {

    private int id;
    private String title;
    private String content;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String location;
    private String type;
    private Tutor lastEditedUser;
    private ZonedDateTime lastEditDate;
    private String lastEditType;
    private Course course;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Tutor getLastEditedUser() {
        return lastEditedUser;
    }

    public ZonedDateTime getLastEditDate() {
        return lastEditDate;
    }

    public String getLastEditType() {
        return lastEditType;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgendaItem that = (AgendaItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}