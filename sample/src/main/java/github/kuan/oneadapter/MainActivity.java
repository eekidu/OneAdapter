package github.kuan.oneadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.model.Model;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView rv = new RecyclerView(this);
        setContentView(rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        List<IItemModel> dataList = mockData();

        BaseEventAgent baseEventAgent = new BaseEventAgent();
        OneAdapter<BaseEventAgent> adapter = new OneAdapter<>(dataList, baseEventAgent);
        baseEventAgent.setLayoutManagerType(layoutManager);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    private List<IItemModel> mockData() {
        List<IItemModel> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Model());
        }
        return list;
    }
}
