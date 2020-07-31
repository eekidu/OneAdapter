package github.kuan.oneadapter.viewprovider;

import android.view.View;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.IItemView;
import github.kuan.oneadapter.IItemViewProvider;
import github.kuan.oneadapter.itemview.ItemViewGradeClazz;
import github.kuan.oneadapter.itemview.ItemViewShcoolHight;
import github.kuan.oneadapter.itemview.ItemViewStudent;
import github.kuan.oneadapter.model.GradeClassModel;
import github.kuan.oneadapter.model.SchoolModel;
import github.kuan.oneadapter.model.StudentModel;

public class SchoolItemProvider implements IItemViewProvider {
    @Override
    public Class<? extends View> getItemView(int position, Object model, BaseEventHandlerAgent event) {
        if (model instanceof SchoolModel) {
            return ItemViewShcoolHight.class;
        } else if (model instanceof GradeClassModel) {
            return ItemViewGradeClazz.class;
        } else if (model instanceof StudentModel) {
            return ItemViewStudent.class;
        }
        return null;
    }

//    @Override
//    public void onBindDataAgent(int position, Object model, BaseEventHandlerAgent event, IItemView<Object> iItemView) {
//
//        iItemView.bindData(position, model, event);
//    }
}
