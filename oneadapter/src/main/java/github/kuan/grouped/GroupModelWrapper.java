package github.kuan.grouped;

import android.view.View;

import github.kuan.oneadapter.IItemView;

public abstract class GroupModelWrapper<T> {
    T mGoupModel;

    public GroupModelWrapper(T goupModel) {
        mGoupModel = goupModel;
    }

    public abstract int getChildrenCount();

    public abstract boolean hasHeader();

    public abstract Class<IItemView> providerHeaderView();

    public abstract boolean hasFooter();

    public abstract Class<IItemView> providerFooterView();

}
