package github.kuan.oneadapter.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.R;
import github.kuan.oneadapter.model.GradeClassModel;

public class ItemViewGradeClazz extends LinearLayout implements IItemView<GradeClassModel> {

    public TextView mTextView;
    private RecyclerView mRecyclerView;

    public ItemViewGradeClazz(Context context) {
        this(context, null);
    }

    public ItemViewGradeClazz(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public ItemViewGradeClazz(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
//        int dimension = (int)getResources().getDimension(R.dimen.padding);
        int dimension = 60;
        setPadding(0, dimension, 0, dimension);
        mTextView = new TextView(context);
        addView(mTextView);

        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView, new ViewGroup.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bindData(int position, GradeClassModel data, BaseEventHandlerAgent event) {
        mTextView.setText(data.toString());

        OneAdapter<BaseEventHandlerAgent> oneAdapter = new OneAdapter<>(data.mStudentModelList, event);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(oneAdapter);
    }
}
