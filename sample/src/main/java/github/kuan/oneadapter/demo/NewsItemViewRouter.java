package github.kuan.oneadapter.demo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.demo.itemview.ItemViewNewsType1;
import github.kuan.oneadapter.demo.itemview.ItemViewNewsType2;
import github.kuan.oneadapter.demo.model.NewsModel;
import github.kuan.oneadapter.interfaces.ItemViewRouter;

/**
 * @author kuan
 * @date 2020/12/12
 */
public class NewsItemViewRouter implements ItemViewRouter<NewsModel> {

    @Override
    public Class<? extends View> getItemView(int position, @NonNull NewsModel model, @Nullable BaseEventMessenger eventMessenger, @NonNull OneAdapter adapter) {
        if (eventMessenger != null) {
            RecyclerView.LayoutManager layoutManager = eventMessenger.getLayoutManager();
            String from = eventMessenger.getParams("from");
        }

        switch (model.type) {
            case 1:
                return ItemViewNewsType1.class;
            case 2:
                return ItemViewNewsType2.class;
        }

        return null;
    }
}
