[![jitPack](https://jitpack.io/v/eekidu/OneAdapter.svg)](https://jitpack.io/#eekidu/OneAdapter)

# OneAdapter

该库的目的是简化RecyclerView的Adapter的使用，全局只有一个Adapter，无需维护Type、ViewHolder以及书写模板的Adapter。        
使数据实体与ItemView绑定变得简洁，容易扩展。支持数据实体与ItemView"一对多"的映射关系。

## 依赖

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}


dependencies {
	implementation 'com.github.eekidu:OneAdapter:**'
}
```



## 使用
#### 1、“一对一”：即一种数据实体类永远只对应一种样式的ItemView。
1、步骤一：在数据实体上加注解，指定用哪种ItemView展示该数据。
```
@ItemViewModelTo(ItemViewVideoView.class)//表示VideoModel会走入ItemViewVideoView布局中进行展示
public class VideoModel {
    public String title;
}

```
- 不方便用注解的，也支持手动注册的方式。

2、步骤二：实现itemView样式，常规操作。

- ItemView需要实现ItemView接口😂，OneAdapter会通过该方法进行数据绑定。
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
以上，数据Model就与对应的ItemView建立了绑定关系，只要数据源有该类型的数据，就会用相应的ItemView进行展示，无需在Adapter中进行处理。    
创建OneAdapter，设置给RecyclerView既可。
```
OneAdapter oneAdapter = new OneAdapter(dataList);
recyclerView.setAdapter(oneAdapter);
```


#### 2、"一对多"：一种数据实体类对应的ItemView有多种，如：
- 需要根据数据信息确定样式。如：数据实体里有type字段，根据type确定对应的样式；或是有图片返回，需要根据图片张数确定ItemView样式等。
- 需要根据场景确定样式。如：不同页面，同一数据实体可能会在列表、或瀑布流中进行展示，需要根据所处的环境来选择相应的ItemView样式。


1、步骤一：在数据实体类上使用另外一个注解进行标记。

```
@ItemViewRouterModelTo(NewsItemViewRouter.class)//表示该数据实体会用该类完成一对多的分发，使数据实体绑定具体的ItemView。
public class NewsModel {

    public NewsModel(int type) {
        this.type = type;
    }

    public int type;
    public String title;
    public String content;
}
```
2、步骤二：实现“一对多”分发，使数据最终找到自己的家(ItemView)。

```
public class NewsItemViewRouter implements ItemViewRouter<NewsModel> {

    @Override
    public Class<? extends View> getItemView(int position, @NonNull NewsModel model, @Nullable BaseEventMessenger eventMessenger, @NonNull OneAdapter adapter) {
        if (eventMessenger != null) {
            RecyclerView.LayoutManager layoutManager = eventMessenger.getLayoutManager();
            String from = eventMessenger.getParams("from");
        }

        //这里可以结合数据信息，已经页面使用的布局等各种信息，来最终判断得出该数据使用何种布局
        switch (model.type) {
            case 1:
                return ItemViewNewsType1.class;
            case 2:
                return ItemViewNewsType2.class;
        }

        return null;
    }
}
```
剩下的就是把OneAdapter设置给RecyclerView，同上。

#### 3、通信
BaseEventMessenger是ItemView与上层页面之间相互通信的一个信使。它可以持有上层页面的一些参数 传递给每个ItemView。ItemView的点击回调或者自定义回调，都是通过它传递给ItemView的。详见DemoEventMessenger实现。


#### 总结：
RecyclerView显示流程的起点是数据源，数据驱动显示，在数据上加注解，告知数据下一步该去哪。

- 简洁，结构清晰。当进行Item类型扩展时，会非常简洁高效。只需新增数据实体，完成新样式的ItemView既可，无效改动已有代码。
- 复用性高，ItemView的样式与BaseEventMessenger中的事件处理，在不同页面都可以得到很好的复用。
- 安全。某一个ItemView的创建、或是数据绑定出现异常，会隐藏该条目，不会对其他条目产生影响，更不会是程序崩溃。
