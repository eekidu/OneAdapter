package github.kuan.oneadapter.util;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class RecyclerViewUtil {

    /**
     * 设置holder全屏
     *
     * @param holder
     */
    public static void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder == null) {
            return;
        }
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {

            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }
}
