package github.kuan.oneadapter;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 作用：
 * 1、可以作为参数载体，使得上层页面的参数传递到每个ItemView中去；
 * 2、用来实现页面与ItemView之间通信，可以通过注册条目点击listener或者自定义事件callback；
 * 3、ItemView通用的事件处理、业务逻辑在此实现，达到复用的效果。
 * <p>
 * note：继承该类，进行扩展
 *
 * @author kuan
 * @date 2020-05-30
 */
public class BaseEventAgent {
    public static final int LAYOUT_TYPE_LINEAR = 1;
    public static final int LAYOUT_TYPE_GRID = 2;
    public static final int LAYOUT_TYPE_STAGGERED = 3;

    /**
     * 上层需要向ItemView传递的一些参数
     */
    private Bundle mExtParams;
    /**
     * 布局类型
     */
    private int mLayoutManagerType;
    /**
     * 设置条目点击事件
     */
    private Map<Class, View.OnClickListener> mItemClickListeners;
    /**
     * 设置自定义事件Callback
     */
    private Map<Class, Object> mCustomListeners;


    /**
     * 设置ItemView的点击事件
     *
     * @param clazz
     * @param itemClickListener
     */
    public void setOnItemClickListener(Class<? extends IItemView> clazz, View.OnClickListener itemClickListener) {
        if (mItemClickListeners == null) {
            mItemClickListeners = new HashMap<>();
        }
        mItemClickListeners.put(clazz, itemClickListener);
    }

    /**
     * 获取ItemView的点击事件
     *
     * @param clazz
     * @return
     */
    public View.OnClickListener getItemClickListener(Class<? extends IItemView> clazz) {
        return mItemClickListeners != null ? mItemClickListeners.get(clazz) : null;
    }


    public <T> void registerCustomListener(T customListener) {
        //TODO
        registerCustomListener(null, customListener);
    }

    public <T> void registerCustomListener(Class<T> clazz, T customListener) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz can't be null, is used for Key!");
        }

        if (customListener != null) {
            if (mCustomListeners == null) {
                mCustomListeners = new HashMap<>();
            }
            mCustomListeners.put(clazz, customListener);
        }
    }

    public <T> T getCustomListener(Class<T> listenerClass) {
        return (T) mCustomListeners.get(listenerClass);
    }

    public int getLayoutManagerType() {
        return mLayoutManagerType;
    }

    public void setLayoutManagerType(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            mLayoutManagerType = LAYOUT_TYPE_STAGGERED;
        } else if (layoutManager instanceof GridLayoutManager) {
            mLayoutManagerType = LAYOUT_TYPE_GRID;
        } else if (layoutManager instanceof LinearLayoutManager) {
            mLayoutManagerType = LAYOUT_TYPE_LINEAR;
        }
    }

    public Bundle getExtParams() {
        if (mExtParams == null) {
            mExtParams = new Bundle();
        }
        return mExtParams;
    }
}
