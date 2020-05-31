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
    protected List mDatas;
    protected E mBaseEventAgent;
    private List<Class<? extends View>> mViews;

    private Map<Class, IItemViewProvider> map = new HashMap<>();

    public OneAdapter(List datas, E baseEventAgent) {
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
            Object data = mDatas.get(position);
            IItemView itemView = (IItemView) holder.itemView;
            itemView.bindData(data, mBaseEventAgent, position);
        } else {
            throw new RuntimeException(holder.itemView.getClass().getSimpleName() + " must implement IItemView");
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object data = mDatas.get(position);

        Class<?> model = data.getClass();
        MapToView mapToView = model.getAnnotation(MapToView.class);
        if (mapToView != null) {
            Class<? extends View> value = mapToView.value();
            int i = mViews.indexOf(value);
            if (i > -1) {
                return i;
            } else {
                mViews.add(value);
                return mViews.size() - 1;
            }
        }

        MapToViewProvider annotation = model.getAnnotation(MapToViewProvider.class);
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
            throw new RuntimeException(model.getSimpleName() + "  need MapToViewProvider!");
        }
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /*******************************/
    public List getDatas() {
        return mDatas;
    }

    public void clear() {
        List<?> datas = getDatas();
        if (datas != null) {
            datas.clear();
        }
        notifyDataSetChanged();
    }


    public void addItem(Object baseBean) {
        if (baseBean == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList();
        }
        mDatas.add(baseBean);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void appendDatas(List datas) {
        if (datas == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList();
        }
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    public void setmDatas(List datas) {
        if (datas != null) {
            mDatas = datas;
            notifyDataSetChanged();
        }
    }
}
