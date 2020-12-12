package github.kuan.oneadapter.imple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.interfaces.ItemView;
import github.kuan.oneadapter.OneAdapter;

public class ItemViewWhenError extends AppCompatTextView implements ItemView {
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
    public void bindData(int position, Object data, BaseEventMessenger event, OneAdapter adapter) {
        setText(data.toString());
        if (!OneAdapter.isDebug) {
            setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
    }
}
