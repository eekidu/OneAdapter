package github.kuan.oneadapter.demo.itemview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.demo.model.NewsModel;
import github.kuan.oneadapter.demo.model.VideoModel;
import github.kuan.oneadapter.interfaces.ItemView;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class ItemViewNewsType2 extends LinearLayout implements ItemView<NewsModel> {

    private TextView mTextView;

    public ItemViewNewsType2(Context context) {
        this(context, null);
    }

    public ItemViewNewsType2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewNewsType2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        setLayoutParams(layoutParams);
        setOrientation(VERTICAL);
        mTextView = new TextView(context);
        setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        addView(mTextView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bindData(int position, NewsModel data, BaseEventMessenger event, OneAdapter adapter) {
        mTextView.setText("我是新闻类型：" + data.type);
    }
}
