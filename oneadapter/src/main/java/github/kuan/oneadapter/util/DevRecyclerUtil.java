package github.kuan.oneadapter.util;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DevRecyclerUtil extends RecyclerView.ItemDecoration {
    public static final int TEXTSIZE = 16;

    private final Paint mPaint;

    public static void openDev(Activity activity) {
        ViewGroup root = activity.findViewById(android.R.id.content);
        addItemDecora(root);
    }


    public DevRecyclerUtil() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(TEXTSIZE);
        mPaint.setColor(Color.BLUE);

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
            float textWidth = mPaint.measureText(simpleName);
            float textHigh = mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent;

            c.drawText(simpleName, right - textWidth - 20, bottom - textHigh, mPaint);
        }
    }

    private static void addItemDecora(ViewGroup root) {
        int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = root.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                addItemDecora((ViewGroup) childAt);
                if (childAt instanceof RecyclerView) {
                    ((RecyclerView) childAt).addItemDecoration(new DevRecyclerUtil());
                }
            }
        }
    }
}