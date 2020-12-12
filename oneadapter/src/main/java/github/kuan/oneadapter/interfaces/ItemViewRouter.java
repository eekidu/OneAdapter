package github.kuan.oneadapter.interfaces;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;

/**
 * ItemView提供者
 *
 * @author kuan
 */
public interface ItemViewRouter<T> {

    /**
     * 通过入参，判断出ItemView的类型，并给出ItemView的Class对象
     *
     * @param model
     * @param eventMessenger
     * @param position
     * @return
     */
    Class<? extends View> getItemView(int position, @NonNull T model, @Nullable BaseEventMessenger eventMessenger, @NonNull OneAdapter adapter);



    /**
     * 可以复写该方法，在绑定数据前后做切面处理。
     *
     * @param position
     * @param model
     * @param event
     * @param iItemView
     */
    default void onBindDataAgent(int position, @NonNull T model, @Nullable BaseEventMessenger event, @NonNull ItemView<Object> iItemView, @NonNull OneAdapter adapter) {
        iItemView.bindData(position, model, event, adapter);
    }
}
