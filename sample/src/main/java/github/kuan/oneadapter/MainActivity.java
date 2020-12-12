package github.kuan.oneadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.demo.model.NewsModel;
import github.kuan.oneadapter.demo.model.VideoModel;
import github.kuan.oneadapter.ext.HeaderOneAdapter;
import github.kuan.oneadapter.itemview.ItemViewGradeClazz;
import github.kuan.oneadapter.itemview.ItemViewStudent;
import github.kuan.oneadapter.model.GradeClassModel;
import github.kuan.oneadapter.model.SchoolModel;
import github.kuan.oneadapter.model.StudentModel;
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

    @Deprecated
    private void demo1(RecyclerView rv) {
        sPool = new RecyclerView.RecycledViewPool();

        MainActivity.sPool.setMaxRecycledViews(ItemViewStudent.class.hashCode(), 5000);
        MainActivity.sPool.setMaxRecycledViews(ItemViewGradeClazz.class.hashCode(), 100);


        rv.setRecycledViewPool(sPool);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        List dataList = mockData();


        GradeClassModel gradeClassModel = new GradeClassModel(99, 99);
        gradeClassModel.mStudentModelList = someStudent();
        dataList.add(gradeClassModel);

        StudentModel studentModel = new StudentModel();
        studentModel.name = "我是特等生";
        dataList.add(studentModel);

        dataList.addAll(someStudent());


        BaseEventMessenger baseEventAgent = new BaseEventMessenger(rv);
//        baseEventAgent.setOnItemClickListener(ItemViewStudent.class, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "学生条目点击", Toast.LENGTH_SHORT).show();
//            }
//        });


        baseEventAgent.registerCustomListener(ItemViewStudent.OnSutdentItemClick.class, new ItemViewStudent.OnSutdentItemClick() {
            @Override
            public void onStudentClick(int position, StudentModel studentModel) {
                Toast.makeText(MainActivity.this, studentModel.name, Toast.LENGTH_SHORT).show();
            }
        });

        HeaderOneAdapter adapter = new HeaderOneAdapter(baseEventAgent);


        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        //设置数据
        adapter.setmDatas(dataList);

        //添加头部View
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        textView.setText("我是头部布局");
        adapter.addHeaderView(textView);
    }

    private List mockData() {

        List list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add(new StudentModel());
//        }

        for (int i = 0; i < 20; i++) {
            SchoolModel schoolModel = new SchoolModel();
            schoolModel.level = i % 5;
            schoolModel.gradeClassList = mockGradeClass();
            list.add(schoolModel);
        }

        return list;
    }


    private List mockGradeClass() {
        List dataList = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            for (int j = 1; j < 10; j++) {
                GradeClassModel gradeClassModel = new GradeClassModel(i, j);
                gradeClassModel.mStudentModelList = someStudent();
                dataList.add(gradeClassModel);

            }
        }
        StudentModel studentModel = new StudentModel();
        studentModel.name = "个体学生";
        dataList.add(studentModel);
        return dataList;
    }


    private List someStudent() {
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            StudentModel studentModel = new StudentModel();
            studentModel.name = "学生" + i;
            list.add(studentModel);
        }
        return list;
    }
}
