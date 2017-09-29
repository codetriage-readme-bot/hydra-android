package be.ugent.zeus.hydra.data.network.minerva.course;

import android.accounts.Account;
import android.content.Context;
import android.support.annotation.NonNull;

import be.ugent.zeus.hydra.data.network.minerva.MinervaRequest;

/**
 * Request to get a list of courses.
 *
 * Warning: this is an internal sync request, and should not be used to display data directly.
 *
 * @author feliciaan
 * @author Niko Strijbol
 */
public class CoursesMinervaRequest extends MinervaRequest<Courses> {

    public CoursesMinervaRequest(Context context, Account account) {
        super(Courses.class, context, account);
    }

    @Override
    @NonNull
    protected String getAPIUrl() {
        return MINERVA_API + "courses";
    }
}