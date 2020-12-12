package github.kuan.oneadapter.imple;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

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

    public ItemViewWhenError(Context context) {
        this(context, null);
    }

    public ItemViewWhenError(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemViewWhenError(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (OneAdapter.isDebug()) {
            int padding = (int) Resources.getSystem().getDisplayMetrics().density * 20;
            setPadding(0, padding, 0, padding);
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
    }

    @Override
    public void bindData(int position, Object data, BaseEventMessenger event, OneAdapter adapter) {
        if (OneAdapter.isDebug()) {
            if(OneAdapter.isInDeveloping()){
                String text = String.format("%s\n%s", "请检查该数据是否有对应ItemView返回", data.toString());
                setText(text);
            }
        }


    }
}
