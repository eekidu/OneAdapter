package github.kuan.oneadapter.internal;

import android.view.View;

import java.util.HashMap;

import github.kuan.oneadapter.OneAdapter;
import github.kuan.oneadapter.anotitions.ItemViewAno;
import github.kuan.oneadapter.anotitions.ItemViewRouteAno;
import github.kuan.oneadapter.interfaces.ItemViewRouter;

public class ItemViewRouterManager {

    /**
     * 记录数据实体类对应的{@link ItemViewRouter}
     */
    private final HashMap<Class<?>, ItemViewRouter> mModelClassToItemViewRouterMap;

    public ItemViewRouterManager() {
        mModelClassToItemViewRouterMap = new HashMap<>();
    }

    public void registerItem(Class<?> model, ItemViewRouter itemViewProvider) {
        mModelClassToItemViewRouterMap.put(model, itemViewProvider);
    }

    public ItemViewRouter findProvider(Class<?> modelClazz) {
        ItemViewRouter provider = mModelClassToItemViewRouterMap.get(modelClazz);
        if (provider == null) {
            ItemViewAno annotation = modelClazz.getAnnotation(ItemViewAno.class);
            if (annotation != null) {
                Class<? extends View> value = annotation.value();
                OneToOneItemViewRouter oneToOneItemViewRouter = new OneToOneItemViewRouter(value);
                registerItem(modelClazz, oneToOneItemViewRouter);
                return oneToOneItemViewRouter;
            }

            //2、如果没找到，从数据实体的注解上找,并创建实例ItemViewProvider
            ItemViewRouteAno providerAnnotation = modelClazz.getAnnotation(ItemViewRouteAno.class);
            if (providerAnnotation != null) {
                Class<? extends ItemViewRouter> providerClass = providerAnnotation.value();
                try {
                    provider = providerClass.newInstance();
                    registerItem(modelClazz, provider);
                } catch (Exception e) {
                    if (OneAdapter.isDebug) {
                        String errInfo = String.format("(%s.java:1) must has empty construction method!", providerClass.getSimpleName());
                        throw new RuntimeException(errInfo);
                    }
                }
            } else {
                if (OneAdapter.isDebug) {
                    String errInfo = String.format("(%s.java:0) need annotation: %s!", modelClazz.getSimpleName(), "@" + ItemViewRouteAno.class.getSimpleName());
                    throw new RuntimeException(errInfo);
                }
            }
        }
        return provider;
    }
}
