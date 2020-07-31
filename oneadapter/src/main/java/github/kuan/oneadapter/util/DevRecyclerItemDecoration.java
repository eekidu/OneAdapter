package github.kuan.oneadapter.util;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class DevRecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private WeakReference<View> mWeakReference;

    public static final int TEXTSIZE = 16;

    private final Paint mPaint;

    public DevRecyclerItemDecoration() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(TEXTSIZE);
        mPaint.setColor(Color.BLUE);

    }

    private View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mWeakReference = new WeakReference<>(v);
            ((RecyclerView)v.getParent()).invalidateItemDecorations();
            return true;
        }
    };


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        view.setOnLongClickListener(mLongClickListener);
        if (mWeakReference != null && mWeakReference.get() == view) {
            outRect.bottom = 200;
        }

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int bottom = childAt.getBottom();
            int right = childAt.getRight();

            String simpleName = childAt.getClass().getSimpleName();
            float v = mPaint.measureText(simpleName);
            float top = mPaint.getFontMetrics().top;

            c.drawText(simpleName, right - v - 20, bottom - TEXTSIZE, mPaint);
        }
    }


    public static void openDev(Activity activity) {
        ViewGroup root = activity.findViewById(android.R.id.content);
        addItemDecora(root);
    }

    private static void addItemDecora(ViewGroup root) {
        int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = root.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                addItemDecora((ViewGroup) childAt);
                if (childAt instanceof RecyclerView) {
                    ((RecyclerView) childAt).addItemDecoration(new DevRecyclerItemDecoration());
                }
            }
        }
    }
}
