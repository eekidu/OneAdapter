package github.kuan.oneadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.imple.ItemViewWhenError;

/**
 * @author kuan
 */
public class OneAdapter<E extends BaseEventHandlerAgent> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static boolean isDebug = true;

    protected List mDatas;
    protected E mBaseEventHandlerAgent;

    /**
     * Type到ViewClass的映射
     */
    private SparseArray<Class<? extends View>> mTypeToViewMap;

    private ItemViewProviderManager mItemViewProviderManager;

    public OneAdapter() {
        this(null, null);

    }

    public OneAdapter(List datas) {
        this(datas, null);
    }

    public OneAdapter(E eventHandlerAgent) {
        this(null, eventHandlerAgent);
    }

    public OneAdapter(List datas, E baseEventHandlerAgent) {
        this.mBaseEventHandlerAgent = baseEventHandlerAgent;
        this.mDatas = datas;
        mTypeToViewMap = new SparseArray<>();
        mItemViewProviderManager = new ItemViewProviderManager();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        try {
            Class<? extends View> aClass = mTypeToViewMap.get(viewType);
            Constructor<? extends View> constructor = aClass.getConstructor(Context.class);
            itemView = constructor.newInstance(parent.getContext());

        } catch (Exception ex) {
            ex.printStackTrace();
            itemView = new ItemViewWhenError(parent.getContext());
        }
        return new RecyclerView.ViewHolder(itemView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View holderItemView = holder.itemView;
        if (holderItemView instanceof IItemView) {
            Object model = mDatas.get(position);
            IItemView itemView = (IItemView) holderItemView;

            if (mBaseEventHandlerAgent != null) {
                View.OnClickListener itemClickListener = mBaseEventHandlerAgent.getItemClickListener(itemView.getClass());
                if (itemClickListener != null) {
                    holderItemView.setOnClickListener(itemClickListener);
                }
            }

            try {
                Class<?> aClass = model.getClass();
                IItemViewProvider provider = mItemViewProviderManager.findProvider(aClass);
                if (provider != null) {
                    provider.onBindDataAgent(position, model, mBaseEventHandlerAgent, itemView, this);
                }
//                itemView.onBindDataAgent(position, model, mBaseEventHandlerAgent);
            } catch (Exception ex) {
                if (isDebug) {
                    if (ex instanceof ClassCastException) {
                        StackTraceElement[] stackTrace = ex.getStackTrace();
                        if (stackTrace != null && stackTrace.length > 0) {
                            StackTraceElement stackTraceElement = stackTrace[0];
                            String localInfo = stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber();
                            String errMsg = String.format("(%s) Generic Type error! %s", localInfo, ex.getMessage());
                            throw new ClassCastException(errMsg);
                        }
                    }
                    throw ex;
                }
            }
        } else {
            if (isDebug) {
                String errMsg = String.format("(%s.java:1) must implement IItemView interface!", holderItemView.getClass().getSimpleName());
                throw new ClassCastException(errMsg);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object model = mDatas.get(position);
        Class<?> modelClazz = model.getClass();

        Class<? extends View> itemViewClazz = null;

        IItemViewProvider provider = mItemViewProviderManager.findProvider(modelClazz);
        if (provider != null) {
            itemViewClazz = provider.getItemView(position, model, mBaseEventHandlerAgent);
        } else {
            if (isDebug) {
                String errInfo = String.format("需要注册或者在数据实体上标记IItemViewProvider");
                throw new RuntimeException(errInfo);
            }
        }

        if (itemViewClazz != null) {
            int index = mTypeToViewMap.indexOfValue(itemViewClazz);
            if (index > -1) {
                return index;
            } else {
                mTypeToViewMap.put(mTypeToViewMap.size(), itemViewClazz);
                return mTypeToViewMap.size() - 1;
            }
        }
        return -1;
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public void setItemViewProviderManager(ItemViewProviderManager itemViewProviderManager) {

        mItemViewProviderManager = itemViewProviderManager;
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
