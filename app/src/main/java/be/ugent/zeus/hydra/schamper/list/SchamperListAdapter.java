package be.ugent.zeus.hydra.schamper.list;

import android.view.ViewGroup;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.common.ui.ViewUtils;
import be.ugent.zeus.hydra.common.ui.customtabs.ActivityHelper;
import be.ugent.zeus.hydra.common.ui.recyclerview.adapters.ItemDiffAdapter;
import be.ugent.zeus.hydra.schamper.Article;

/**
 * @author Niko Strijbol
 * @author feliciaan
 */
class SchamperListAdapter extends ItemDiffAdapter<Article, SchamperViewHolder> {

    private final ActivityHelper helper;

    SchamperListAdapter(ActivityHelper helper) {
        super();
        this.helper = helper;
    }

    @Override
    public SchamperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SchamperViewHolder(ViewUtils.inflate(parent, R.layout.item_schamper), helper);
    }
}