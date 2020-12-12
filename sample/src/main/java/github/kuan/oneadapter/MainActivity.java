package github.kuan.oneadapter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.demo.DemoEventMessenger;
import github.kuan.oneadapter.demo.model.NewsModel;
import github.kuan.oneadapter.demo.model.VideoModel;
import github.kuan.oneadapter.ext.HeaderOneAdapter;
import github.kuan.oneadapter.util.DevRecyclerUtil;

public class MainActivity extends AppCompatActivity {

    public static RecyclerView.RecycledViewPool sPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView);

        OneAdapter.setDebug(true, true);

        demo1(recyclerView);
        DevRecyclerUtil.openDev(this);
    }

    private void demo1(RecyclerView recycler) {
        List<Object> objects = new ArrayList<>();

        objects.add(new VideoModel());
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(2));
        objects.add(new NewsModel(2));
        objects.add(new NewsModel(4));
        objects.add(new NewsModel(2));
        objects.add(new NewsModel(3));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new VideoModel());

        HeaderOneAdapter oneAdapter = new HeaderOneAdapter();
        oneAdapter.setDataList(objects);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(oneAdapter);

        addHeaderFooter(oneAdapter);
        addClickListener(recycler, oneAdapter);

    }

    private void addClickListener(RecyclerView recycler, HeaderOneAdapter oneAdapter) {
        BaseEventMessenger demoEventMessage = new DemoEventMessenger(MainActivity.this, recycler);
        oneAdapter.setEventMessenger(demoEventMessage);

    }

    private void addHeaderFooter(HeaderOneAdapter oneAdapter) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        TextView textView = new TextView(this);
        textView.setText("我是头布局");
        textView.setLayoutParams(layoutParams);
        oneAdapter.addHeaderView(textView);

        TextView footer = new TextView(this);
        footer.setText("我是尾布局");
        footer.setLayoutParams(layoutParams);
        oneAdapter.addFootView(footer);
    }

}
