package github.kuan.oneadapter;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.demo.model.NewsModel;
import github.kuan.oneadapter.demo.model.VideoModel;
import github.kuan.oneadapter.ext.HeaderOneAdapter;
import github.kuan.oneadapter.util.DevRecyclerUtil;

public class MainActivity extends AppCompatActivity {

    public static RecyclerView.RecycledViewPool sPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView rv = new RecyclerView(this);
        setContentView(rv);


//        demo1(rv);
        demo2(rv);

        DevRecyclerUtil.openDev(this);
    }

    private void demo2(RecyclerView recycler) {
        List<Object> objects = new ArrayList<>();

        objects.add(new VideoModel());
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(2));
        objects.add(new NewsModel(2));
        objects.add(new NewsModel(2));
        objects.add(new NewsModel(2));
        objects.add(new NewsModel(1));
        objects.add(new NewsModel(1));
        objects.add(new VideoModel());

        HeaderOneAdapter oneAdapter = new HeaderOneAdapter();
        oneAdapter.setmDatas(objects);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(oneAdapter);

        addHeaderFooter(oneAdapter);

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
