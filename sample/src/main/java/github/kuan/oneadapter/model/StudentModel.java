package github.kuan.oneadapter.model;

import github.kuan.oneadapter.anotitions.ItemViewRouteAno;
import github.kuan.oneadapter.viewprovider.SchoolItemRouter;

@ItemViewRouteAno(SchoolItemRouter.class)
public class StudentModel {
    public String name;
}
