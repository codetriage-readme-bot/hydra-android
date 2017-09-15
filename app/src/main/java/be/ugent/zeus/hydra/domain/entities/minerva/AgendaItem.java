package be.ugent.zeus.hydra.domain.entities.minerva;

import java8.util.Objects;
import org.threeten.bp.ZonedDateTime;

/**
 * @author Niko Strijbol
 */
public final class AgendaItem {

    /**
     * Constant indicating this agenda item does not have an ID for the Android calendar.
     */
    public static final long NO_CALENDAR_ID = -1;
    public static final String AGENDA_URI = "hydra://minerva/calendar/";

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
    private long calendarId = NO_CALENDAR_ID;

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

    public long getCalendarId() {
        return calendarId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLastEditedUser(Tutor lastEditedUser) {
        this.lastEditedUser = lastEditedUser;
    }

    public void setLastEditDate(ZonedDateTime lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public void setLastEditType(String lastEditType) {
        this.lastEditType = lastEditType;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }

    /**
     * Get the URI for this item. The URI consists of {@link #AGENDA_URI} with the id ({@link #getId()} appended to it.
     *
     * @return The URI.
     */
    public String getUri() {
        return AGENDA_URI + getId();
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