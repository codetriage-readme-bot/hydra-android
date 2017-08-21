package be.ugent.zeus.hydra.data.database.minerva2.course;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

/**
 * Contains a course and the number of unread announcements for that course.
 *
 * @author Niko Strijbol
 */
public class CourseUnread {

    @Embedded
    private Course course;
    @ColumnInfo(name = "unread_count")
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
}