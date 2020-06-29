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
import github.kuan.oneadapter.demo.model.AModel;
import github.kuan.oneadapter.interfaces.ItemView;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class ItemViewSimple extends LinearLayout implements ItemView<AModel> {


    private TextView mTextView;

    public ItemViewSimple(Context context) {
        this(context, null);
    }

    public ItemViewSimple(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewSimple(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        mTextView = new TextView(context);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(Color.WHITE);
        addView(mTextView);
    }

    @Override
    public void bindData(int position, AModel data, BaseEventMessenger event, OneAdapter adapter) {
        mTextView.setText(data.title);
        setBackgroundColor(MainActivity.getItemColor(0));
    }


}
