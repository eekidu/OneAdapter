package github.kuan.oneadapter;

import java.util.HashMap;

public class ItemViewProviderManager {

    private final HashMap<Class, IItemViewProvider> mModelClassToItemViewProviderMap;

    public ItemViewProviderManager() {
        mModelClassToItemViewProviderMap = new HashMap<>();
    }

    public void registe(Class model, IItemViewProvider itemViewProvider) {
        mModelClassToItemViewProviderMap.put(model, itemViewProvider);
    }

    public IItemViewProvider findProvider(Class<?> modelClazz) {
        IItemViewProvider provider = mModelClassToItemViewProviderMap.get(modelClazz);
        if (provider == null) {
            //2、如果没找到，从数据实体的注解上找,并创建实例ItemViewProvider
            MapToViewProvider providerAnnotation = modelClazz.getAnnotation(MapToViewProvider.class);
            if (providerAnnotation != null) {
                Class<? extends IItemViewProvider> providerClass = providerAnnotation.value();
                try {
                    provider = providerClass.newInstance();
                    registe(modelClazz, provider);
                } catch (Exception e) {
                    if (OneAdapter.isDebug) {
                        String errInfo = String.format("(%s.java:1) must has empty construction method!", providerClass.getSimpleName());
                        throw new RuntimeException(errInfo);
                    }
                }
            } else {
                if (OneAdapter.isDebug) {
                    String errInfo = String.format("(%s.java:0) need annotation: %s!", modelClazz.getSimpleName(), "@" + MapToViewProvider.class.getSimpleName());
                    throw new RuntimeException(errInfo);
                }
            }
        }

        return provider;
    }
}
