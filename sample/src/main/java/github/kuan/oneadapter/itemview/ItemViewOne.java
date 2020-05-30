package github.kuan.oneadapter.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import github.kuan.oneadapter.BaseEventAgent;
import github.kuan.oneadapter.IItemModel;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.model.Model;

public class ItemViewOne extends LinearLayout implements IItemView {
    public ItemViewOne(Context context) {
        super(context);
    }

    public ItemViewOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bindData(IItemModel data, BaseEventAgent event, int position) {
        if (data instanceof Model) {
            TextView textView = new TextView(getContext());
            textView.setText("I'm itemview one！");
            addView(textView);
        }
    }
}
