package github.kuan.oneadapter;

import android.view.View;

/**
 * ItemView提供者
 */
public interface IItemViewProvider {

    /**
     * 在列表中使用
     *
     * @param model
     * @param event
     * @param position
     * @return
     */
    Class<? extends View> getItemView(Object model, BaseEventAgent event, int position);
}
