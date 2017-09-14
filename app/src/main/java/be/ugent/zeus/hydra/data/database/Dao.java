package be.ugent.zeus.hydra.data.database;

import android.content.Context;

/**
 * An abstract DAO.
 *
 * On initialisation, this class constructs an instance of a {@link android.database.sqlite.SQLiteOpenHelper}.
 *
 * By design, you do NOT need to close database connections in android, as long as you use only one. Every dao
 * uses the same helper instance, so this should not be a problem.
 *
 * @author Niko Strijbol
 */
@Deprecated
public abstract class Dao {

    protected final Context context;

    /**
     * @param context The application context.
     */
    public Dao(Context context) {
        this.context = context.getApplicationContext();
    }
}