package github.kuan.oneadapter.interfaces;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;

/**
 * ItemView需要实现该接口，adapter最终会通过该方法，为View绑定数据。
 *
 * @param <M>
 */
public interface ItemView<M> {

    void bindData(int position, M data, BaseEventMessenger event, OneAdapter adapter);

}
