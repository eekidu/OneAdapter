package github.kuan.oneadapter.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class HeadView extends AppCompatTextView {
    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setText("我是头部View");
    }
}
