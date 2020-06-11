package github.kuan.oneadapter.model;

import github.kuan.oneadapter.MapToViewProvider;
import github.kuan.oneadapter.viewprovider.Model2ViewProvider;

@MapToViewProvider(value = Model2ViewProvider.class)
public class Model2 {

    public Model2() {
    }

    public Model2(int modelDataType) {
        this.modelDataType = modelDataType;
    }

    int modelDataType;
}
