package be.ugent.zeus.hydra.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by silox on 17/10/15.
 */

public class MinervaFragment extends AbstractFragment {
    @Override
    public void onResume() {
        super.onResume();
        this.sendScreenTracking("Minerva");
    }
}
