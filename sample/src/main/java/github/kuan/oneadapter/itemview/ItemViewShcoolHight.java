package github.kuan.oneadapter.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.MainActivity;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.model.SchoolModel;

public class ItemViewShcoolHight extends LinearLayout implements IItemView<SchoolModel> {

    public TextView mTextView;
    private RecyclerView mRecyclerView;
    private OneAdapter<BaseEventHandlerAgent> mOneAdapter;

    public ItemViewShcoolHight(Context context) {
        this(context, null);
    }

    public ItemViewShcoolHight(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewShcoolHight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setPadding(0, 60, 0, 60);
        mTextView = new TextView(context);
        mRecyclerView = new RecyclerView(context);
        addView(mTextView);
        addView(mRecyclerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setRecycledViewPool(MainActivity.sPool);

        mOneAdapter = new OneAdapter<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setRecycleChildrenOnDetach(true);

        mRecyclerView.setAdapter(mOneAdapter);
    }

    @Override
    public void bindData(int position, SchoolModel data, BaseEventHandlerAgent event) {
        mTextView.setText(data.getLevelLabel());

        mOneAdapter.setEventAgent(event);
        mOneAdapter.setmDatas(data.gradeClassList);
    }
}
