package github.kuan.oneadapter.viewprovider;

import android.view.View;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.interfaces.ItemViewRouter;
import github.kuan.oneadapter.itemview.ItemViewGradeClazz;
import github.kuan.oneadapter.itemview.ItemViewShcoolHight;
import github.kuan.oneadapter.itemview.ItemViewStudent;
import github.kuan.oneadapter.model.GradeClassModel;
import github.kuan.oneadapter.model.SchoolModel;
import github.kuan.oneadapter.model.StudentModel;

public class SchoolItemRouter implements ItemViewRouter {

    @Override
    public Class<? extends View> getItemView(int position, Object model, BaseEventMessenger event, OneAdapter adapter) {
        if (model instanceof SchoolModel) {
            return ItemViewShcoolHight.class;
        } else if (model instanceof GradeClassModel) {
            return ItemViewGradeClazz.class;
        } else if (model instanceof StudentModel) {
            return ItemViewStudent.class;
        }
        return null;
    }
}
