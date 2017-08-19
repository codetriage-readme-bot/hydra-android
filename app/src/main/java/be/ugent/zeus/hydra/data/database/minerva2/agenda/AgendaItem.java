package be.ugent.zeus.hydra.data.database.minerva2.agenda;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import be.ugent.zeus.hydra.data.database.minerva.AgendaTable;
import be.ugent.zeus.hydra.data.database.minerva.CourseTable;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;
import org.threeten.bp.ZonedDateTime;

/**
 * Represents an agenda item as it is saved in the database.
 *
 * @author Niko Strijbol
 */
@Entity(
        tableName = AgendaTable.TABLE_NAME,
        foreignKeys = {
                @ForeignKey(
                        entity = Course.class,
                        parentColumns = CourseTable.Columns.ID,
                        childColumns = AgendaTable.Columns.COURSE
                )
        }
)
public class AgendaItem {

    /**
     * Constant indicating this agenda item does not have an ID for the Android calendar.
     */
    public static final long NO_CALENDAR_ID = -1;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AgendaTable.Columns.ID)
    private int id;
    @ColumnInfo(name = AgendaTable.Columns.TITLE)
    private String title;
    @ColumnInfo(name = AgendaTable.Columns.CONTENT)
    private String content;
    @ColumnInfo(name = AgendaTable.Columns.START_DATE)
    private ZonedDateTime startDate;
    @ColumnInfo(name = AgendaTable.Columns.END_DATE)
    private ZonedDateTime endDate;
    @ColumnInfo(name = AgendaTable.Columns.LOCATION)
    private String location;
    @ColumnInfo(name = AgendaTable.Columns.TYPE)
    private String type;
    @ColumnInfo(name = AgendaTable.Columns.LAST_EDIT_USER)
    private String lastEditedUser;
    @ColumnInfo(name = AgendaTable.Columns.LAST_EDIT)
    private ZonedDateTime lastEditDate;
    @ColumnInfo(name = AgendaTable.Columns.LAST_EDIT_TYPE)
    private String lastEditType;
    @ColumnInfo(name = AgendaTable.Columns.COURSE, index = true)
    private String courseId;
    @ColumnInfo(name = AgendaTable.Columns.CALENDAR_ID)
    private long calendarId = NO_CALENDAR_ID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastEditedUser() {
        return lastEditedUser;
    }

    public void setLastEditedUser(String lastEditedUser) {
        this.lastEditedUser = lastEditedUser;
    }

    public ZonedDateTime getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(ZonedDateTime lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public String getLastEditType() {
        return lastEditType;
    }

    public void setLastEditType(String lastEditType) {
        this.lastEditType = lastEditType;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }
}