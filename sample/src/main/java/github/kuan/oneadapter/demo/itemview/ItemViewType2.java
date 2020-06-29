package github.kuan.oneadapter.demo.itemview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.MainActivity;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.demo.model.BModel;
import github.kuan.oneadapter.interfaces.ItemView;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class ItemViewType2 extends LinearLayout implements ItemView<BModel> {

    private TextView mTextView;

    public ItemViewType2(Context context) {
        this(context, null);
    }

    public ItemViewType2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewType2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        setLayoutParams(layoutParams);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);

        mTextView = new TextView(context);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(Color.WHITE);
        addView(mTextView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bindData(int position, BModel data, BaseEventMessenger event, OneAdapter adapter) {
        mTextView.setText(data.getTitle());
        setBackgroundColor(MainActivity.getItemColor(data.type));
    }
}
