package github.kuan.oneadapter.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.model.StudentModel;

public class ItemViewStudent extends LinearLayout implements IItemView<StudentModel>, View.OnClickListener {

    private TextView mTextView;
    private StudentModel mData;
    private int mPosition;
    private BaseEventHandlerAgent mEvent;


    public interface OnSutdentItemClick {
        void onStudentClick(int position, StudentModel studentModel);
    }

    public ItemViewStudent(Context context) {
        this(context, null);
    }

    public ItemViewStudent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewStudent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(context);
        addView(mTextView);
        setOnClickListener(this);
    }

    @Override
    public void bindData(int position, StudentModel data, BaseEventHandlerAgent event) {
        mData = data;
        mPosition = position;
        mEvent = event;
        mTextView.setText(data.name);

    }

    @Override
    public void onClick(View v) {
        if (mEvent != null) {
            OnSutdentItemClick customListener = mEvent.getCustomListener(OnSutdentItemClick.class);
            if (customListener != null) {
                customListener.onStudentClick(mPosition, mData);
            }
        }
    }

}