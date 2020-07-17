package github.kuan.oneadapter;

import android.view.View;

/**
 * ItemView提供者
 *
 * @author kuan
 */
public interface IItemViewProvider {

    /**
     * 通过入参，判断出ItemView的类型，并给出ItemView的Class对象
     *
     * @param model
     * @param event
     * @param position
     * @return
     */
    Class<? extends View> getItemView(int position, Object model, BaseEventHandlerAgent event);


    /**
     * 可以复现该方法，在绑定数据前后做切面处理。
     *
     * @param position
     * @param model
     * @param event
     * @param iItemView
     */
    default void onBindDataAgent(int position, Object model, BaseEventHandlerAgent event, IItemView<Object> iItemView) {
        iItemView.bindData(position, model, event);
    }
}
