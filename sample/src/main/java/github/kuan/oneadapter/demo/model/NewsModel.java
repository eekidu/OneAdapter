package github.kuan.oneadapter.demo.model;

import github.kuan.oneadapter.annotations.ItemViewRouterModelTo;
import github.kuan.oneadapter.demo.NewsItemViewRouter;

@ItemViewRouterModelTo(NewsItemViewRouter.class)
public class NewsModel {

    public NewsModel(int type) {
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
}


