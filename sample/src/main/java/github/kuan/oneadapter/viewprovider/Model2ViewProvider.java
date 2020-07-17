package github.kuan.oneadapter.viewprovider;

import android.view.View;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.IItemViewProvider;
import github.kuan.oneadapter.itemview.ItemViewOne;

public class Model2ViewProvider implements IItemViewProvider {


    @Override
    public Class<? extends View> getItemView(int position, Object model, BaseEventHandlerAgent event) {
        return ItemViewOne.class;
    }
}
