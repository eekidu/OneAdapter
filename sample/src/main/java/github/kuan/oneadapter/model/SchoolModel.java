package github.kuan.oneadapter.model;

import java.util.List;

import github.kuan.oneadapter.anotitions.ItemViewRouteAno;
import github.kuan.oneadapter.viewprovider.SchoolItemRouter;

@ItemViewRouteAno(SchoolItemRouter.class)
public class SchoolModel {

    public int level;
    public List gradeClassList;

    public String getLevelLabel() {
        switch (level) {
            case 0:
                return "幼儿园";
            case 1:
                return "小学";
            case 2:
                return "初中";
            case 3:
                return "高中";
            case 4:
                return "大学";
        }
        return "";
    }
}
