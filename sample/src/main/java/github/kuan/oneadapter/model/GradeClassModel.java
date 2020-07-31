package github.kuan.oneadapter.model;

import java.util.List;

import github.kuan.oneadapter.MapToViewProvider;
import github.kuan.oneadapter.viewprovider.SchoolItemProvider;

@MapToViewProvider(SchoolItemProvider.class)
public class GradeClassModel {

    private final int grade;
    private final int mClazz;
    public List<StudentModel> mStudentModelList;

    public GradeClassModel(int i, int j) {
        grade = i;
        mClazz = j;
    }

    @Override
    public String toString() {
        return grade + "年级" + mClazz + "班";
    }
}
