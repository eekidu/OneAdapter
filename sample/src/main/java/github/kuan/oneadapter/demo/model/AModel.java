package github.kuan.oneadapter.demo.model;


import github.kuan.oneadapter.annotations.ItemViewModelTo;
import github.kuan.oneadapter.demo.itemview.ItemViewSimple;

/**
 * 一对一：数据实体，只对应一种布局
 */
@ItemViewModelTo(ItemViewSimple.class)
public class AModel {

    public String title = "简单的数据实体，只对应一种样式";
}
