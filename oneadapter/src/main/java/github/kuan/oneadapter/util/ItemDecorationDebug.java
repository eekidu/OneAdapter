package github.kuan.oneadapter.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorationDebug extends RecyclerView.ItemDecoration {
    public static final int TEXT_SIZE = 16;

    private final Paint mPaint;

    public ItemDecorationDebug() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setColor(Color.parseColor("#FF0033"));
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
}
