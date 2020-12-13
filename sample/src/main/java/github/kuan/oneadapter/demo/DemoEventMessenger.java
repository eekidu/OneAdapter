package github.kuan.oneadapter.demo;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.demo.itemview.ItemViewNewsType3;
import github.kuan.oneadapter.listener.OnItemClickListener;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class DemoEventMessenger extends BaseEventMessenger {

    private final Context mContext;
    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(@NonNull OneAdapter adapter, @NonNull View view, int position) {
            Toast.makeText(mContext, view.getClass().getSimpleName() + " 条目：" + position, Toast.LENGTH_SHORT).show();
        }
    };

    private ItemViewNewsType3.OnNewsType1ClickListener item3ClickListener = new ItemViewNewsType3.OnNewsType1ClickListener() {
        @Override
        public void onTitleClick() {
            Toast.makeText(mContext, "标题点击", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onContentClick() {
            Toast.makeText(mContext, "内容点击", Toast.LENGTH_SHORT).show();
        }
    };

    public DemoEventMessenger(Context context) {
        mContext = context;
        setOnItemClickListener(mOnItemClickListener);
        registerCustomListener(ItemViewNewsType3.OnNewsType1ClickListener.class, item3ClickListener);
    }

}
