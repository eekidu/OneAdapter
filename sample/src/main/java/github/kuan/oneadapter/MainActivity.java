package github.kuan.oneadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.demo.DemoEventMessenger;
import github.kuan.oneadapter.demo.model.AModel;
import github.kuan.oneadapter.demo.model.BModel;

public class MainActivity extends AppCompatActivity {

    public static RecyclerView.RecycledViewPool sPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView);

        OneAdapter.setDebug(true, true);

        demo1(recyclerView);
    }

    private void demo1(RecyclerView recycler) {
        List<Object> objects = new ArrayList<>();

        objects.add(new AModel());
        objects.add(new BModel(1));
        objects.add(new BModel(2));
        objects.add(new BModel(2));
        objects.add(new BModel(2));
        objects.add(new BModel(3));
        objects.add(new BModel(1));
        objects.add(new BModel(1));
        objects.add(new BModel(4));
        objects.add(new BModel(1));
        objects.add(new BModel(1));
        objects.add(new AModel());

        OneAdapter oneAdapter = new OneAdapter();
        oneAdapter.setDataList(objects);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(dividerItemDecoration);
        recycler.setAdapter(oneAdapter);

        addHeaderFooter(oneAdapter);
        addClickListener(recycler, oneAdapter);

    }

    private void addClickListener(RecyclerView recycler, OneAdapter oneAdapter) {
        BaseEventMessenger demoEventMessage = new DemoEventMessenger(MainActivity.this);
        oneAdapter.setEventMessenger(demoEventMessage);
    }

    private void addHeaderFooter(OneAdapter oneAdapter) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(layoutParams);
            if (i < 2) {
                textView.setText("我是头布局" + i);
                oneAdapter.addHeaderView(textView);
            } else {
                textView.setText("我是尾布局" + i);
                oneAdapter.addFooterView(textView);
            }

        }
    }

    public static int[] getItemColor() {
        int[] colors = new int[]{Color.parseColor("#336699"),
                Color.parseColor("#99CC33"),
                Color.parseColor("#99CCCC"),
        };
        return colors;
    }

    public static int getItemColor(int position) {
        int[] itemColor = getItemColor();
        return itemColor[position % itemColor.length];
    }

}
