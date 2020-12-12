[![jitPack](https://jitpack.io/v/eekidu/OneAdapter.svg)](https://jitpack.io/#eekidu/OneAdapter)

# 一个RecyclerAdapter

该库的目的是简化RecyclerView的Adapter的使用，就是全局只有一个Adapter。基于注解和约定，使数据实体与ItemView绑定变得非常简洁，无需维护Type、ViewHolder。

### 简单用法：
1、步骤一：在数据实体上加注解，指定要绑定的ItemView。
```
@ItemViewAno(ItemViewVideoView.class)
public class VideoModel {
    public int type;
}

```

2、步骤二：itemView

```
public class ItemViewVideoView extends LinearLayout implements ItemView<VideoModel> {

    public ItemViewVideoView(Context context) {
        this(context, null);
    }

    @Override
    public void bindData(int position, VideoModel data, BaseEventMessenger event, OneAdapter adapter) {
     //这里绑定数据
    }
}

```
以上是"一对一"的用法，即一种数据实体类只对应一种样式的ItemView。
### "一对多"的使用
一对多指的是，一种数据实体类对应多种样式的ItemView。例如：
- 需要根据数据实体里的信息确定样式。如：数据实体里有type字段，根据type确定对应的样式；或是图片返回的数量，一张或多张，使用不同的样式ItemView。
- 同一种实体类，在不同页面或不同Tab下采用样式不一样。如：可能是列表样式、也可能是瀑布流样式。

只需要在数据实体类上标记另外一个注解：（TODO）（一共就两个注解）
表示该数据实体会用该类进行判断，确定到底用那种ItemView。从而完成一对多的分发，使具体的数据实体绑定具体的ItemView。








特点：
- 简洁
这也是封装该库原因，通过一种约定，达到使用起来非常简洁的效果。数据实体类上标记使用的ItemView既可。
- 安全
某一个ItemView的创建、或是数据绑定出现异常，会隐藏该条目，不会对其他条目产生影响，更不会是程序崩溃。
