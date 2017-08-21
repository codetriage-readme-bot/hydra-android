package be.ugent.zeus.hydra.data.database.minerva2.announcement;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import be.ugent.zeus.hydra.data.database.minerva.AnnouncementTable;
import be.ugent.zeus.hydra.data.database.minerva.CourseTable;
import be.ugent.zeus.hydra.data.database.minerva2.course.Course;
import org.threeten.bp.ZonedDateTime;

/**
 * Represents an announcement from Minerva.
 *
 * @author Niko Strijbol
 */
@Entity(
        tableName = AnnouncementTable.TABLE_NAME,
        foreignKeys = {
                @ForeignKey(
                        entity = Course.class,
                        parentColumns = CourseTable.Columns.ID,
                        childColumns = AnnouncementTable.Columns.COURSE
                )
        }
)
public final class Announcement {

    @ColumnInfo(name = AnnouncementTable.Columns.TITLE)
    private String title;
    @ColumnInfo(name = AnnouncementTable.Columns.CONTENT)
    private String content;
    @ColumnInfo(name = AnnouncementTable.Columns.EMAIL_SENT)
    private boolean wasEmailSent;
    @PrimaryKey
    @ColumnInfo(name = AnnouncementTable.Columns.ID)
    private int id;
    @ColumnInfo(name = AnnouncementTable.Columns.LECTURER)
    private String lecturer;
    @ColumnInfo(name = AnnouncementTable.Columns.DATE)
    private ZonedDateTime lastEditedAt;
    @ColumnInfo(name = AnnouncementTable.Columns.READ_DATE)
    private ZonedDateTime readAt;
    @ColumnInfo(name = AnnouncementTable.Columns.COURSE, index = true)
    private String courseId;

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

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}