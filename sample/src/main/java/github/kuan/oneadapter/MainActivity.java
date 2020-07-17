package github.kuan.oneadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.ext.HeaderOneAdapter;
import github.kuan.oneadapter.model.Model;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView rv = new RecyclerView(this);
        setContentView(rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        List dataList = mockData();

        BaseEventHandlerAgent baseEventAgent=new BaseEventHandlerAgent();
        HeaderOneAdapter<BaseEventHandlerAgent> adapter = new HeaderOneAdapter<>(baseEventAgent);
        baseEventAgent.setLayoutManagerType(layoutManager);



        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        adapter.setmDatas(dataList);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        textView.setText("我是头部布局");
        adapter.addHeaderView(textView);


    }

    private List mockData() {
        List list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Model());
        }
        return list;
    }
}
