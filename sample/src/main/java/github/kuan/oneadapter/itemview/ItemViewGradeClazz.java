package github.kuan.oneadapter.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.interfaces.ItemView;
import github.kuan.oneadapter.MainActivity;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.model.GradeClassModel;

public class ItemViewGradeClazz extends LinearLayout implements ItemView<GradeClassModel> {

    public TextView mTextView;
    private RecyclerView mRecyclerView;
    private OneAdapter mOneAdapter;

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
        addView(mRecyclerView, new ViewGroup.LayoutParams(20, ViewGroup.LayoutParams.WRAP_CONTENT));


        mRecyclerView.setRecycledViewPool(MainActivity.sPool);


        mOneAdapter = new OneAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mOneAdapter);
        mRecyclerView.setItemViewCacheSize(10000);
    }

    @Override
    public void bindData(int position, GradeClassModel data, BaseEventMessenger event, OneAdapter adapter) {
        mTextView.setText(data.toString());

//        mOneAdapter.setEventAgent(event);
        mOneAdapter.setmDatas(data.mStudentModelList);
        Log.d("1234", "ItemViewGradeClazz: " + data.mStudentModelList.size());
    }
}
