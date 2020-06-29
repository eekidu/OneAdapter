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
import github.kuan.oneadapter.demo.model.BModel;
import github.kuan.oneadapter.interfaces.ItemView;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class ItemViewType3 extends LinearLayout implements ItemView<BModel> {

    public interface OnCustomClickListener {
        void onTitleClick();

        void onContentClick();
    }

    private TextView mTvTitle;
    private TextView mTvContent;

    private OnCustomClickListener mOnCustomClickListener;

    public ItemViewType3(Context context) {
        this(context, null);
    }

    public ItemViewType3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewType3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        setLayoutParams(layoutParams);
        setOrientation(VERTICAL);

        mTvTitle = new TextView(context);
        setGravity(Gravity.CENTER);
        mTvTitle.setTextSize(28);
        addView(mTvTitle, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mTvContent = new TextView(context);
        mTvContent.setMinHeight(100);
        addView(mTvContent, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mTvTitle.setOnClickListener(this::onClick);
        mTvContent.setOnClickListener(this::onClick);
    }

    public void setOnNewsType1ClickListener(OnCustomClickListener clickListener) {
        mOnCustomClickListener = clickListener;
    }

    @Override
    public void bindData(int position, BModel data, BaseEventMessenger event, OneAdapter adapter) {
        mTvTitle.setText(data.getTitle());
        mTvContent.setText("我是正文，我所在位置是" + position + "，标题和我点击事件不一样");
        if (event != null) {
            OnCustomClickListener customListener = event.getCustomListener(OnCustomClickListener.class);
            setOnNewsType1ClickListener(customListener);
            event.getItemClickListener(this.getClass());
        }
    }

    public void onClick(View v) {
        if (mOnCustomClickListener != null) {
            if (v == mTvTitle) {
                mOnCustomClickListener.onTitleClick();
            } else if (v == mTvContent) {
                mOnCustomClickListener.onContentClick();
            }
        }
    }

}
