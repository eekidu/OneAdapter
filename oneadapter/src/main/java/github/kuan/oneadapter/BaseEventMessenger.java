package github.kuan.oneadapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import github.kuan.oneadapter.interfaces.ItemView;
import github.kuan.oneadapter.listener.OnItemClickListener;

/**
 * 作用：
 * 1、可以作为参数载体，使得上层页面的参数传递到每个ItemView中去；
 * 2、用来实现ItemView与页面之间通信.
 * 3、ItemView通用的事件处理、业务逻辑在此实现，达到复用的效果。
 *
 * @author kuan
 * @date 2020-05-30
 */
public class BaseEventMessenger {

    private final WeakReference<RecyclerView> mRv;
    /**
     * 上层需要向ItemView传递的一些参数
     */
    private Map<Object, Object> mExtParams;

    /**
     * 条目点击事件回调
     */
    private OnItemClickListener mItemClickListener;
    /**
     * 设置条目点击事件
     */
    private Map<Class, OnItemClickListener> mItemClickListeners;

    /**
     * 设置自定义事件Callback
     */
    private Map<Class, Object> mCustomListeners;

    public BaseEventMessenger(RecyclerView rv) {
        mRv = new WeakReference<RecyclerView>(rv);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    /**
     * 设置ItemView的点击事件
     *
     * @param itemViewClazz
     * @param onItemClickListener
     */
    public void setOnItemClickListener(Class<? extends ItemView> itemViewClazz, OnItemClickListener onItemClickListener) {
        if (mItemClickListeners == null) {
            mItemClickListeners = new HashMap<>();
        }
        mItemClickListeners.put(itemViewClazz, onItemClickListener);
    }

    /**
     * 获取ItemView的点击事件
     *
     * @param clazz
     * @return
     */
    public OnItemClickListener getItemClickListener(Class<? extends ItemView> clazz) {
        return mItemClickListeners != null ? mItemClickListeners.get(clazz) : null;
    }

    /**
     * ItemView 自定义的回调事件，通过类名
     *
     * @param customListenerClass
     * @param customListenerInstance
     * @param <T>
     */
    public <T> void registerCustomListener(Class<T> customListenerClass, T customListenerInstance) {
        if (customListenerClass == null) {
            throw new IllegalArgumentException("customListenerClass can't be null, is used for Key!");
        }

        if (customListenerInstance != null) {
            if (mCustomListeners == null) {
                mCustomListeners = new HashMap<>();
            }
            mCustomListeners.put(customListenerClass, customListenerInstance);
        }
    }

    public <T> T getCustomListener(Class<T> listenerClass) {
        return (T) mCustomListeners.get(listenerClass);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        if (mRv != null && mRv.get() != null) {
            return mRv.get().getLayoutManager();
        }
        return null;
    }


    public Map<Object, Object> getExtParams() {
        if (mExtParams == null) {
            mExtParams = new HashMap<>();
        }
        return mExtParams;
    }

    public void putParams(Object key, Object value) {
        getExtParams().put(key, value);
    }

    public <T> T getParams(Object key) {
        return (T) getExtParams().get(key);
    }
}
