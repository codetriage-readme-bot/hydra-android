package be.ugent.zeus.hydra.ui.minerva.overview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import be.ugent.zeus.hydra.ui.common.AdapterOutOfBoundsException;

/**
 * This provides the tabs in a minerva course.
 *
 * @author Niko Strijbol
 */
public class MinervaCoursePagerAdapter extends FragmentPagerAdapter {

    private final String courseId;

    public MinervaCoursePagerAdapter(FragmentManager fm, String courseId) {
        super(fm);
        this.courseId = courseId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CourseInfoFragment.newInstance(courseId);
            case 1:
                return AnnouncementFragment.newInstance(courseId);
            case 2:
                return AgendaFragment.newInstance(courseId);
        }

        throw new AdapterOutOfBoundsException(position, getCount());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Info";
            case 1:
                return "Aankondigingen";
            case 2:
                return "Agenda";
        }

        throw new AdapterOutOfBoundsException(position, getCount());
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }
}