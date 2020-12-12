package github.kuan.oneadapter.internal;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.interfaces.ItemViewRouter;

/**
 * @author kuan
 * @date 2020/12/12
 */
class OneToOneItemViewRouter implements ItemViewRouter<Object> {
    private final Class<? extends View> mItemViewClazz;

    public OneToOneItemViewRouter(Class<? extends View> itemViewClass) {
        mItemViewClazz = itemViewClass;
    }


    @Override
    public Class<? extends View> getItemView(int position, @NonNull Object model, @Nullable BaseEventMessenger eventMessenger, @NonNull OneAdapter adapter) {
        return mItemViewClazz;
    }
}