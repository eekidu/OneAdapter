package github.kuan.oneadapter.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class RecyclerViewUtil {

    //    <editor-fold desc="获取可见范围">
    public static int[] findVisiableRange(RecyclerView recyclerView) {
        return findVisiableRangeByLayoutManager(recyclerView == null ? null : recyclerView.getLayoutManager());
    }

    /**
     * 获取可见区域的范围
     *
     * @param layoutManager
     * @return int[]{findFirstVisibleItemPosition,findLastVisibleItemPosition}
     * 如果LayoutManager为空，返回{-1,-2}
     */
    public static int[] findVisiableRangeByLayoutManager(RecyclerView.LayoutManager layoutManager) {
        int[] range = {-1, -2};
        if (layoutManager instanceof LinearLayoutManager) {
            range = findRangeLinear((LinearLayoutManager) layoutManager);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            range = findRangeStaggeredGrid((StaggeredGridLayoutManager) layoutManager);
        }
        return range;
    }

    private static int[] findRangeLinear(LinearLayoutManager manager) {
        int[] range = {-1, -2};
        if (manager.findFirstVisibleItemPosition() > -1) {
            range[0] = manager.findFirstVisibleItemPosition();
        }
        if (manager.findLastVisibleItemPosition() > -1) {
            range[1] = manager.findLastVisibleItemPosition();
        }
        return range;
    }


    private static int[] findRangeStaggeredGrid(StaggeredGridLayoutManager manager) {
        int[] startPos = new int[manager.getSpanCount()];
        int[] endPos = new int[manager.getSpanCount()];
        manager.findFirstVisibleItemPositions(startPos);
        manager.findLastVisibleItemPositions(endPos);
        int[] range = findRange(startPos, endPos);
        return range;
    }

    private static int[] findRange(int[] startPos, int[] endPos) {
        int start = -1;
        int end = -2;
        for (int i = 1; i < startPos.length; i++) {
            if (startPos[i] > -1 && start > startPos[i]) {
                start = startPos[i];
            }
        }
        for (int i = 1; i < endPos.length; i++) {
            if (endPos[i] > -1 && end < endPos[i]) {
                end = endPos[i];
            }
        }
        int[] res = new int[]{start, end};
        return res;
    }
//    </editor-fold>

//    <editor-fold desc="判断是否已经到滑动到底部、或者接近底部">

    public static boolean isToEnd(RecyclerView recyclerView) {
        return isCloseToEnd(recyclerView, 0);
    }

    /**
     * 是否已经接近底部
     *
     * @param recyclerView
     * @param lastItemCount 倒数第几条触发，[0,]
     * @return
     */
    public static boolean isCloseToEnd(RecyclerView recyclerView, int lastItemCount) {
        if (recyclerView == null || recyclerView.getLayoutManager() == null || lastItemCount < 0 || lastItemCount > recyclerView.getLayoutManager().getItemCount()) {
            return false;
        }
        int[] visiableRange = RecyclerViewUtil.findVisiableRange(recyclerView);
        if (visiableRange[1] >= recyclerView.getLayoutManager().getItemCount() - 1 - lastItemCount) {
            return true;
        }
        return false;
    }

    //  </editor-fold>
    //  <editor-fold desc="滚动相关">

    public static void smoothoScrollMakeItemCenter(Context context, LinearLayoutManager linearLayoutManager, int targetPosition) {
        if (context == null) {
            return;
        }
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(context) {


            @Override
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100f / displayMetrics.densityDpi;
            }

        };
        linearSmoothScroller.setTargetPosition(targetPosition);
        linearLayoutManager.startSmoothScroll(linearSmoothScroller);
    }
//  </editor-fold>

}
