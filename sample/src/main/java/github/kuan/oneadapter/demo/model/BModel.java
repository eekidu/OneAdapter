package github.kuan.oneadapter.demo.model;

import github.kuan.oneadapter.annotations.ItemViewRouterModelTo;
import github.kuan.oneadapter.demo.BModelRouter;

/**
 * 一对多用法：一种数据实体对应多种布局样式。在ItemViewRouterDemo中进行分发
 */
@ItemViewRouterModelTo(BModelRouter.class)
public class BModel {

    public BModel(int type) {
        this.type = type;
    }

    public int type;
    public String title;
    public String content;


    @Override
    public String toString() {
        return "NewsModel{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTitle() {
        return "样式类型：type =" + type;
    }
}


