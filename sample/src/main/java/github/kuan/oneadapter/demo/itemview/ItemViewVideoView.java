package github.kuan.oneadapter.demo.itemview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.demo.model.VideoModel;
import github.kuan.oneadapter.interfaces.ItemView;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class ItemViewVideoView extends LinearLayout implements ItemView<VideoModel> {

    private TextView mTextView;

    public ItemViewVideoView(Context context) {
        this(context, null);
    }

    public ItemViewVideoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewVideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        setLayoutParams(layoutParams);
        setOrientation(VERTICAL);
        mTextView = new TextView(context);
        addView(mTextView);
        setBackgroundColor(Color.RED);
    }

    @Override
    public void bindData(int position, VideoModel data, BaseEventMessenger event, OneAdapter adapter) {
        mTextView.setText("我是视频布局");
    }
}