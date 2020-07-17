package github.kuan.oneadapter.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.model.Model;

public class ItemViewOne extends LinearLayout implements IItemView {
    private TextView mTextView;

    public ItemViewOne(Context context) {
        this(context, null);
    }

    public ItemViewOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(context);
        addView(mTextView);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
    }


    @Override
    public void bindData(int position, Object data, BaseEventHandlerAgent event) {
        if (data instanceof Model) {
            mTextView.setText(data.hashCode() + "");
        }
    }
}
