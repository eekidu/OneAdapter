package github.kuan.oneadapter.viewprovider;

import android.view.View;

import github.kuan.oneadapter.BaseEventAgent;
import github.kuan.oneadapter.IItemViewProvider;
import github.kuan.oneadapter.itemview.ItemViewOne;

public class ModelOneViewProvider implements IItemViewProvider {
    @Override
    public Class<? extends View> getItemView(Object model, BaseEventAgent event, int position) {
        return ItemViewOne.class;
    }
}
