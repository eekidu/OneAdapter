package github.kuan.oneadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kuan
 */
public class OneAdapter<E extends BaseEventAgent> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<IItemModel> mDatas;
    protected E mBaseEventAgent;
    private List<Class<? extends View>> mViews;

    private Map<Class, IItemViewProvider> map = new HashMap<>();

    public OneAdapter(List<IItemModel> datas, E baseEventAgent) {
        mDatas = datas;
        mBaseEventAgent = baseEventAgent;
        mViews = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class<? extends View> aClass = mViews.get(viewType);
        try {
            Constructor<? extends View> constructor = aClass.getConstructor(Context.class);
            View view = constructor.newInstance(parent.getContext());
            return new RecyclerView.ViewHolder(view) {
            };
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.itemView instanceof IItemView) {
            IItemModel data = mDatas.get(position);
            IItemView itemView = (IItemView) holder.itemView;
            itemView.bindData(data, mBaseEventAgent, position);
        } else {
            throw new RuntimeException(holder.itemView.getClass().getSimpleName() + " must implement IItemView");
        }
    }

    @Override
    public int getItemViewType(int position) {
        IItemModel data = mDatas.get(position);

        Class<? extends IItemModel> model = data.getClass();
        ViewProvider annotation = model.getAnnotation(ViewProvider.class);
        if (annotation != null) {
            Class<? extends IItemViewProvider> providerClass = annotation.value();
            IItemViewProvider iItemViewProvider = map.get(providerClass);
            if (iItemViewProvider == null) {
                try {
                    iItemViewProvider = providerClass.newInstance();
                    map.put(providerClass, iItemViewProvider);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            if (iItemViewProvider != null) {
                Class<? extends View> itemView = iItemViewProvider.getItemView(data, mBaseEventAgent, position);
                int i = mViews.indexOf(itemView);
                if (i > -1) {
                    return i;
                } else {
                    mViews.add(itemView);
                    return mViews.size() - 1;
                }
            } else {
                throw new RuntimeException(providerClass.getSimpleName() + "  newInstance() fail! Constructors cannot be private！");
            }
        } else {
            throw new RuntimeException(model.getSimpleName() + "  need ViewProvider!");
        }
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /*******************************/
    public List<? extends IItemModel> getDatas() {
        return mDatas;
    }

    public void clear() {
        List<?> datas = getDatas();
        if (datas != null) {
            datas.clear();
        }
        notifyDataSetChanged();
    }


    public void addItem(IItemModel baseBean) {
        mDatas.add(baseBean);
//        notifyItemInserted(datas.size() - 1);
    }

    public void appendDatas(List<? extends IItemModel> datas) {
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }


    public void setmDatas(List<? extends IItemModel> datas) {
        mDatas = (List<IItemModel>) datas;
        notifyDataSetChanged();
    }
}
