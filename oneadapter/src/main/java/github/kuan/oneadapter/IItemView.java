package github.kuan.oneadapter;

/**
 * ItemView需要实现该接口，adapter最终会通过该方法，为View绑定数据。
 *
 * @param <T>
 */
public interface IItemView<T> {

    void bindData(int position, T data, BaseEventHandlerAgent event);

    /**
     * 传入Adapter
     *
     * @param adapter
     */
    default void setAdapter(OneAdapter adapter) {
    }

}
