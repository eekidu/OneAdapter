package github.kuan.oneadapter.imple;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.interfaces.ItemView;
import github.kuan.oneadapter.OneAdapter;

/**
 * 当出现异常时，会用该View进行占位。
 *
 * @author kuan
 * @date 2020/06/08
 */
public class ItemViewWhenError extends AppCompatTextView implements ItemView {


    private int viewType;
    private Exception e;

    public ItemViewWhenError(Context context) {
        this(context, null);
    }

    public ItemViewWhenError(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewWhenError(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float density = Resources.getSystem().getDisplayMetrics().density;
        if (OneAdapter.isDebug()) {
            int padding = (int) (density * 20);
            setPadding(0, padding, 0, padding);
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            setBackgroundColor(Color.YELLOW);
        } else {
            setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        setMaxHeight((int) (density * 300));
    }

    @Override
    public void bindData(int position, Object data, BaseEventMessenger event, OneAdapter adapter) {
        if (OneAdapter.isDebug()) {
            if (OneAdapter.isInDeveloping()) {
                String errorTip;
                if (e instanceof NoItemViewImplException) {
                    errorTip = String.format("%s\n%s", "请检查该数据是否有对应ItemView返回", data.toString());
                } else {
                    errorTip = e == null ? "" : e.toString();
                }
                setText(errorTip);
            }
        }
    }

    public void setError(int viewType, Exception e) {
        this.viewType = viewType;
        this.e = e;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static class NoItemViewImplException extends NullPointerException {

    }
}
