package github.kuan.oneadapter.model;

import github.kuan.oneadapter.MapToViewProvider;
import github.kuan.oneadapter.viewprovider.SchoolItemProvider;

@MapToViewProvider(SchoolItemProvider.class)
public class StudentModel {
    public String name;
}
