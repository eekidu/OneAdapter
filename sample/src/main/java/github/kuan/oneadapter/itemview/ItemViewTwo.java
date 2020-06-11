package github.kuan.oneadapter.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import github.kuan.oneadapter.BaseEventAgent;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.model.Model2;

public class ItemViewTwo extends LinearLayout implements IItemView<Model2> {

    private TextView mTextView;

    public ItemViewTwo(Context context) {
        this(context, null);
    }

    public ItemViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(context);
        addView(mTextView);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
    }


    @Override
    public void bindData(Model2 data, BaseEventAgent event, int position) {
        mTextView.setText(data.hashCode()+"");
    }


}
