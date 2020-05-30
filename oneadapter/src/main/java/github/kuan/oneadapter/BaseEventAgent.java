package github.kuan.oneadapter;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 作用：
 * <p>
 * 1、可以作为参数容器使用，使得页面的参数传递到ItemView中去；
 * 2、页面与ItemView之间通信，可以通过注册listener或者callback；
 * 3、多种ItemView通用事件处理、业务逻辑写在该类，达到复用的效果。，
 * <p>
 * note：继承该类，进行扩展
 *
 * @author kuan
 * @date 2020-05-30
 */
public class BaseEventAgent {

    private Bundle mBundle;
    private int mLayoutManagerType;
    private View.OnClickListener mOnClickListener;


    public int getLayoutManagerType() {
        return mLayoutManagerType;
    }

    public void setLayoutManagerType(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            mLayoutManagerType = 3;
        } else if (layoutManager instanceof GridLayoutManager) {
            mLayoutManagerType = 2;
        } else if (layoutManager instanceof LinearLayoutManager) {
            mLayoutManagerType = 1;
        }
    }

    public Bundle getBundle() {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        return mBundle;
    }

}
