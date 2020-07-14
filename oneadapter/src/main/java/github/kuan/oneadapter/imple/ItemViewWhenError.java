package github.kuan.oneadapter.imple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.OneAdapter;

public class ItemViewWhenError extends AppCompatTextView implements IItemView {
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
    public void bindData(Object data, BaseEventHandlerAgent event, int position) {
        setText(data.toString());
        if (!OneAdapter.isDebug) {
            setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
    }
}
