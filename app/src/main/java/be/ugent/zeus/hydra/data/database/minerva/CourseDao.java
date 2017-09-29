package be.ugent.zeus.hydra.data.database.minerva;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;

import be.ugent.zeus.hydra.data.database.Dao;
import be.ugent.zeus.hydra.data.sync.SyncCallback;
import be.ugent.zeus.hydra.data.network.minerva.course.Course;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This class provides easy access to the {@link Course}s in the database.
 *
 * {@inheritDoc}
 *
 * @author Niko Strijbol
 */
@Deprecated
public class CourseDao extends Dao implements SyncCallback<Course, String> {

    private static final String TAG = "CourseDao";

    /**
     * @param context The application context.
     */
    public CourseDao(Context context) {
        super(context);
    }

    public void insert(Course course) {
        //insert(Collections.singleton(course));
    }

    /**
     * Add new courses to the database. This method assumes the courses are not in the database already. If they are,
     * you should remove them first, or update them instead.
     *
     * @param courses The courses to insert.
     */
    public void insert(Collection<Course> courses) {

//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        try {
//            db.beginTransaction();
//            for (Course course : courses) {
//                db.insertOrThrow(CourseTable.TABLE_NAME, null, getValues(course));
//            }
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }
    }

    /**
     * Delete all calendar items that match the given ID's. If the collection is null, everything will be deleted.
     *
     * @param ids The ID's to delete, or null for everything.
     */
    public void delete(Collection<String> ids) {

//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        if (ids == null) {
//            db.delete(CourseTable.TABLE_NAME, null, null);
//        } else if (ids.size() == 1) {
//            db.delete(CourseTable.TABLE_NAME, where(CourseTable.Columns.ID), args(ids));
//        } else {
//            db.delete(CourseTable.TABLE_NAME, in(CourseTable.Columns.ID, ids.size()), args(ids));
//        }
    }

    /**
     * Delete all courses from the database.
     */
    public void deleteAll() {
       // helper.getWritableDatabase().delete(CourseTable.TABLE_NAME, null, null);
    }

    /**
     * Update existing items. Every column will be replaced with the value from the corresponding object.
     *
     * @param items The items to update.
     */
    public void update(Collection<Course> items) {
        //update(items, false);
    }

    /**
     * Update existing items. Every column will be replaced with the value from the corresponding object.
     *
     * @param items The items to update.
     * @param keepOrder If the existing order should be kept or not.
     */
    public void update(Collection<Course> items, boolean keepOrder) {

//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        try {
//            db.beginTransaction();
//
//            for (Course item: items) {
//                ContentValues values = getValues(item);
//                values.remove(CourseTable.Columns.ID);
//                if (!keepOrder) {
//                    values.remove(CourseTable.Columns.ORDER);
//                }
//                db.update(CourseTable.TABLE_NAME, values, where(CourseTable.Columns.ID), args(item.getId()));
//            }
//
//            db.setTransactionSuccessful();
//
//        } finally {
//            db.endTransaction();
//        }
    }

    /**
     * Get a list of ids of the courses in the database.
     *
     * @return List of ids in the database.
     */
    @NonNull
    public Set<String> getIds() {

//        SQLiteDatabase db = helper.getReadableDatabase();
//        Set<String> result = new HashSet<>();
//
//        Cursor cursor = db.query(CourseTable.TABLE_NAME, new String[]{CourseTable.Columns.ID}, null, null, null, null, null);
//
//        if (cursor == null) {
//            return result;
//        }
//
//        try {
//            int columnIndex = cursor.getColumnIndex(CourseTable.Columns.ID);
//            while (cursor.moveToNext()) {
//                result.add(cursor.getString(columnIndex));
//            }
//        } finally {
//            cursor.close();
//        }
//
//        return result;
        return Collections.emptySet();
    }

    /**
     * Get all courses that are currently in the database.
     *
     * @return A list of all courses.
     */
    @NonNull
    public List<Course> getAll() {
//        Log.d(TAG, "Getting all courses");
//
//        SQLiteDatabase db = helper.getReadableDatabase();
//        List<Course> result = new ArrayList<>();
//
//        //Get the cursor.
//        Cursor c = db.query(
//                CourseTable.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                CourseTable.Columns.ORDER + " ASC, " + CourseTable.Columns.TITLE + " ASC");
//
//        //If the cursor is null, abort
//        if (c == null) {
//            return result;
//        }
//
//        //Get a helper.
//        CourseExtractor extractor = new CourseExtractor.Builder(c).defaults().build();
//
//        //Get the actual r
//        try {
//            while (c.moveToNext()) {
//                result.add(extractor.getCourse());
//            }
//        } finally {
//            c.close();
//        }
//
//        return result;
        return Collections.emptyList();
    }

    /**
     * Get a list of all courses, ordered by the order column, then by name. The second item in the pair indicates how
     * many unread announcements there are for the course in the first item of the pair.
     *
     * @return The list of pairs.
     */
    public List<Pair<Course, Integer>> getAllAndUnreadCount() {

//        // TODO: can this raw SQL be avoided?
//        String subquery = "SELECT count(*) FROM " + AnnouncementTable.TABLE_NAME + " WHERE " +
//                AnnouncementTable.Columns.COURSE + " = " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ID + " AND " +
//                AnnouncementTable.Columns.READ_DATE + " = -1";
//        String sql = "SELECT " + CourseTable.TABLE_NAME + ".*, (" + subquery + ") AS unread_count FROM " + CourseTable.TABLE_NAME +
//                " ORDER BY " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.ORDER + " ASC, " + CourseTable.TABLE_NAME + "." + CourseTable.Columns.TITLE + " ASC";
//
//        SQLiteDatabase db = helper.getReadableDatabase();
//        List<Pair<Course, Integer>> result = new ArrayList<>();
//
//        Cursor c = db.rawQuery(sql, new String[]{});
//
//        //If the cursor is null, abort
//        if (c == null) {
//            return result;
//        }
//
//        //Get a helper.
//        CourseExtractor extractor = new CourseExtractor.Builder(c).defaults().build();
//
//        //Get the actual result
//        try {
//            while (c.moveToNext()) {
//                Course course = extractor.getCourse();
//                Integer unread = c.getInt(c.getColumnIndexOrThrow("unread_count"));
//                result.add(new Pair<>(course, unread));
//            }
//        } finally {
//            c.close();
//        }
//
//        return result;

        return Collections.emptyList();
    }
}