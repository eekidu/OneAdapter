package github.kuan.oneadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import github.kuan.oneadapter.ext.HeaderOneAdapter;
import github.kuan.oneadapter.itemview.ItemViewStudent;
import github.kuan.oneadapter.model.GradeClassModel;
import github.kuan.oneadapter.model.SchoolModel;
import github.kuan.oneadapter.model.StudentModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView rv = new RecyclerView(this);
        setContentView(rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        List dataList = mockData();


        GradeClassModel gradeClassModel = new GradeClassModel(99, 99);
        gradeClassModel.mStudentModelList = someStudent();
        dataList.add(gradeClassModel);

        StudentModel studentModel = new StudentModel();
        studentModel.name = "我是特等生";
        dataList.add(studentModel);


        BaseEventHandlerAgent baseEventAgent = new BaseEventHandlerAgent();
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

        HeaderOneAdapter<BaseEventHandlerAgent> adapter = new HeaderOneAdapter<>(baseEventAgent);
        baseEventAgent.setLayoutManagerType(layoutManager);


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

        for (int i = 0; i < 5; i++) {
            SchoolModel schoolModel = new SchoolModel();
            schoolModel.level = i % 5;
            schoolModel.gradeClassList = mockGradeClass();
            list.add(schoolModel);
        }

        return list;
    }


    private List mockGradeClass() {
        List<GradeClassModel> dataList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 5; j++) {
                GradeClassModel gradeClassModel = new GradeClassModel(i, j);
                gradeClassModel.mStudentModelList = someStudent();
                dataList.add(gradeClassModel);

            }
        }
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
