package github.kuan.oneadapter.demo.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.demo.model.NewsModel;
import github.kuan.oneadapter.interfaces.ItemView;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class ItemViewNewsType3 extends LinearLayout implements ItemView<NewsModel> {

    public interface OnNewsType1ClickListener {
        void onTitleClick();

        void onContentClick();
    }

    private TextView mTvTitle;
    private TextView mTvContent;

    private OnNewsType1ClickListener mOnNewsType1ClickListener;

    public ItemViewNewsType3(Context context) {
        this(context, null);
    }

    public ItemViewNewsType3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewNewsType3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        setLayoutParams(layoutParams);
        setOrientation(VERTICAL);
        mTvTitle = new TextView(context);
        setGravity(Gravity.CENTER);
        addView(mTvTitle, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTvContent = new TextView(context);
        mTvContent.setMinHeight(100);
        addView(mTvContent, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTvTitle.setOnClickListener(this::onClick);
        mTvContent.setOnClickListener(this::onClick);
    }

    public void setOnNewsType1ClickListener(OnNewsType1ClickListener clickListener) {
        mOnNewsType1ClickListener = clickListener;
    }

    @Override
    public void bindData(int position, NewsModel data, BaseEventMessenger event, OneAdapter adapter) {
        mTvTitle.setText("新闻类型：" + data.type);
        mTvContent.setText("我是正文，我所在位置是" + position + "，标题和我点击事件不一样");
        if (event != null) {
            OnNewsType1ClickListener customListener = event.getCustomListener(OnNewsType1ClickListener.class);
            setOnNewsType1ClickListener(customListener);
            event.getItemClickListener(this.getClass());
        }
    }

    public void onClick(View v) {
        if (mOnNewsType1ClickListener != null) {
            if (v == mTvTitle) {
                mOnNewsType1ClickListener.onTitleClick();
            } else if (v == mTvContent) {
                mOnNewsType1ClickListener.onContentClick();
            }
        }
    }

}
