package github.kuan.oneadapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ItemViewWhenError extends TextView implements IItemView {
    public ItemViewWhenError(Context context) {
        this(context, null);
    }

    public ItemViewWhenError(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewWhenError(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bindData(Object data, BaseEventAgent event, int position) {
        setText(data.toString());
        if (!OneAdapter.isDebug) {
            setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
    }
}
