package be.ugent.zeus.hydra.resto.menu;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import be.ugent.zeus.hydra.resto.RestoMenu;
import be.ugent.zeus.hydra.resto.SingleDayFragment;
import be.ugent.zeus.hydra.utils.DateUtils;
import org.threeten.bp.LocalDate;

import java.util.Collections;
import java.util.List;

/**
 * This class provides the tabs in the resto activity.
 *
 * @author Niko Strijbol
 */
class MenuPagerAdapter extends FragmentStatePagerAdapter {

    private List<RestoMenu> data = Collections.emptyList();
    private boolean hasDataBeenSet = false;

    MenuPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<RestoMenu> data) {
        this.data = data;
        this.hasDataBeenSet = true;
        notifyDataSetChanged();
    }

    public boolean hasDataBeenSet() {
        return hasDataBeenSet;
    }

    @Override
    public Fragment getItem(int position) {
        return SingleDayFragment.newInstance(data.get(position));
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public LocalDate getTabDate(int position) {
        return data.get(position).getDate();
    }

    /**
     * The position of a tab is the number of days from today the menu is for.
     * This gets the date from the activity, because at this point, it is not guaranteed
     * the fragments have a date already. The activity does already have the dates however,
     * or no fragments will be made.
     *
     * @param position Which position the tab is in.
     *
     * @return The title.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return DateUtils.getFriendlyDate(data.get(position).getDate());
    }
}