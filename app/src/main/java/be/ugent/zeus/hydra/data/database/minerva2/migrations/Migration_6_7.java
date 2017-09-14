package be.ugent.zeus.hydra.data.database.minerva2.migrations;

import android.accounts.Account;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import be.ugent.zeus.hydra.data.auth.AccountUtils;
import be.ugent.zeus.hydra.data.auth.MinervaConfig;
import be.ugent.zeus.hydra.data.database.minerva2.agenda.AgendaTable;
import be.ugent.zeus.hydra.data.sync.SyncUtils;
import be.ugent.zeus.hydra.data.sync.course.CourseAdapter;
import be.ugent.zeus.hydra.ui.preferences.MinervaFragment;

/**
 * An older migration, kept for compatibility reasons.
 *
 * @author Niko Strijbol
 */
public class Migration_6_7 extends Migration {

    private final Context context;

    public Migration_6_7(Context context) {
        super(6, 7);
        this.context = context;
    }

    @Override
    public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {

        Log.i("Migrations", "Migrating database from " + this.startVersion + " to " + this.endVersion);

        /* -----------------------------------------
         * Upgrade the database structure
         * ----------------------------------------- */
        // Add column
        supportSQLiteDatabase.execSQL("ALTER TABLE " + AgendaTable.TABLE_NAME + " ADD COLUMN " + AgendaTable.Columns.CALENDAR_ID + " INTEGER");
        // Add data
        supportSQLiteDatabase.execSQL("UPDATE " + AgendaTable.TABLE_NAME + " SET " + AgendaTable.Columns.CALENDAR_ID + "=-1");

        /* ----------------------------------------------------------------------
         * Schedule new sync adapters, if the current sync adapter is set.
         * ---------------------------------------------------------------------- */
        // Read current settings.

        if (!AccountUtils.hasAccount(context)) {
            return; // There is no account for some reason.
        }

        Account account = AccountUtils.getAccount(context);

        // The current adapter is set, we set the new ones, otherwise not.
        if (ContentResolver.getSyncAutomatically(account, MinervaConfig.ANNOUNCEMENT_AUTHORITY)) {
            // Set the agenda with the same frequency as the announcements.
            long announcementFrequency = SyncUtils.frequencyFor(context, MinervaConfig.ANNOUNCEMENT_AUTHORITY);
            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
            p.edit()
                    .putString(MinervaFragment.PREF_SYNC_FREQUENCY_CALENDAR, String.valueOf(announcementFrequency))
                    .apply();

            // Request a full synchronisation
            Bundle bundle = new Bundle();
            bundle.putBoolean(CourseAdapter.EXTRA_SCHEDULE_ANNOUNCEMENTS, true);
            bundle.putBoolean(CourseAdapter.EXTRA_SCHEDULE_AGENDA, true);
            SyncUtils.requestSync(account, MinervaConfig.COURSE_AUTHORITY, bundle);
        }
    }
}
