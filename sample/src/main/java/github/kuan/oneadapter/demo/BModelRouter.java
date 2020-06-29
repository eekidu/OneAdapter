package github.kuan.oneadapter.demo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.demo.itemview.ItemViewType1;
import github.kuan.oneadapter.demo.itemview.ItemViewType2;
import github.kuan.oneadapter.demo.itemview.ItemViewType3;
import github.kuan.oneadapter.demo.model.BModel;
import github.kuan.oneadapter.interfaces.ItemViewRouter;

/**
 * 数据实体分发到对应到ItemView中
 *
 * @author kuan
 * @date 2020/12/12
 */
public class BModelRouter implements ItemViewRouter<BModel> {

    @Override
    public Class<? extends View> getItemView(int position, @NonNull BModel model, @Nullable BaseEventMessenger eventMessenger, @NonNull OneAdapter adapter) {
        if (eventMessenger != null) {
            //1.可以得到布局类型
            RecyclerView.LayoutManager layoutManager = eventMessenger.getLayoutManager();
            //2.可以得到页面传递的参数
            String from = eventMessenger.getParams("from");
        }

        //3.数据情况
        //综合以上信息，最终决定用那种类型的View
        switch (model.type) {
            case 1:
                return ItemViewType1.class;
            case 2:
                return ItemViewType2.class;
            case 3:
                return ItemViewType3.class;
        }
        //没有找到匹配的样式
        return null;
    }
}
