package github.kuan.oneadapter;

public interface IItemView<T> {

    void bindData(T data, BaseEventAgent event, int position);

}
