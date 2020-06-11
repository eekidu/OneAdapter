package github.kuan.oneadapter;

/**
 * ItemView接口，子条目View需要实现该接口，adapter中通过该方法，为View设置数据。
 *
 * @param <T>
 */
public interface IItemView<T> {

    void bindData(T data, BaseEventAgent event, int position);

}
