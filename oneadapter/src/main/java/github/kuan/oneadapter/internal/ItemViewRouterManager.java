package github.kuan.oneadapter.internal;

import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;

import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.annotations.ItemViewModelTo;
import github.kuan.oneadapter.annotations.ItemViewRouterModelTo;
import github.kuan.oneadapter.imple.ItemViewWhenError;
import github.kuan.oneadapter.interfaces.ItemViewRouter;

public class ItemViewRouterManager {

    /**
     * 记录数据实体类对应的{@link ItemViewRouter}
     */
    private final HashMap<Class<?>, ItemViewRouter> mModelClassToItemViewRouterMap;

    public ItemViewRouterManager() {
        mModelClassToItemViewRouterMap = new HashMap<>();
    }

    public void registerItem(Class<?> model, ItemViewRouter itemViewRouter) {
        mModelClassToItemViewRouterMap.put(model, itemViewRouter);
    }

    @Nullable
    public ItemViewRouter finItemViewRouterBy(Class<?> modelClazz) {
        //1、从缓存中获取ItemViewRouter
        ItemViewRouter itemViewRouter = mModelClassToItemViewRouterMap.get(modelClazz);
        if (itemViewRouter == null) {
            //2、如果没找到，从数据实体的注解上获取,创建。
            //2.1 一对一注解
            ItemViewModelTo annotation = modelClazz.getAnnotation(ItemViewModelTo.class);
            if (annotation != null) {
                Class<? extends View> value = annotation.value();
                OneToOneItemViewRouter oneToOneItemViewRouter = new OneToOneItemViewRouter(value);
                registerItem(modelClazz, oneToOneItemViewRouter);
                return oneToOneItemViewRouter;
            }
            //2.2 一对多注解
            ItemViewRouterModelTo providerAnnotation = modelClazz.getAnnotation(ItemViewRouterModelTo.class);
            if (providerAnnotation != null) {
                Class<? extends ItemViewRouter> providerClass = providerAnnotation.value();
                try {
                    itemViewRouter = providerClass.newInstance();
                    registerItem(modelClazz, itemViewRouter);
                } catch (Exception e) {
                    if (OneAdapter.isDebug()) {
                        //根据注解信息，创建ItemViewRouter实例失败
                        String errInfo = String.format("(%s.java:1) must has empty construction method!", providerClass.getSimpleName());
                        throw new RuntimeException(errInfo);
                    }
                }
            }
        }

        //该数据的没有itemViewRouter，可能缺少注解
        if (itemViewRouter == null) {
            if (OneAdapter.isDebug()) {
                if (OneAdapter.isInDeveloping()) {
                    registerItem(modelClazz, new OneToOneItemViewRouter(ItemViewWhenError.class));
                } else {
                    String errInfo = String.format("(%s.java:0) need annotation: %s!,or call registerItemView()", modelClazz.getSimpleName(), "@" + ItemViewRouterModelTo.class.getSimpleName());
                    throw new RuntimeException(errInfo);
                }
            }
        }
        return itemViewRouter;
    }
}
