[![jitPack](https://jitpack.io/v/eekidu/OneAdapter.svg)](https://jitpack.io/#eekidu/OneAdapter)

Recycler使用过程中，一条数据绑定到对应ItemView上，过程是很繁琐的，尤其是在处理多类型样式的时候，我们除了要处理数据model和ItemView以外，还要处理ViewType、ViewHolder、BindData、事件回调等等。
该库是为了简化该过程。基于注解，使数据实体与ItemView进行关联。

这里有一个约定：ItemView需要实现接口ItemView。

简单用法：
1、
```
@ItemViewAno(ItemViewVideoView.class)
public class VideoModel {
    public int type;
}

```
2、
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






# 一个RecyclerAdapter

特点：
- 简洁
这也是封装该库原因，通过一种约定，达到使用起来非常简洁的效果。数据实体类上标记使用的ItemView既可。
- 安全
某一个ItemView的创建、或是数据绑定出现异常，会进行隐藏该条目，不会对其他条目产生影响，更不会是程序崩溃