package github.kuan.oneadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.imple.ItemViewWhenError;
import github.kuan.oneadapter.interfaces.ItemView;
import github.kuan.oneadapter.interfaces.ItemViewRouter;
import github.kuan.oneadapter.internal.ItemViewRouterManager;

/**
 * @author kuan
 */
public class OneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static boolean isDebug = true;

    /**
     * 数据源
     */
    protected List<Object> mDataList;
    /**
     * 信使
     * ItemView与上层之间相互通信的桥梁
     */
    protected BaseEventMessenger mEventMessenger;

    /**
     * Type到ViewClass的映射
     */
    private SparseArray<Class<? extends View>> mTypeToViewMap;

    private ItemViewRouterManager mItemViewRouterManager;

    public OneAdapter() {
        this(null, null);
    }

    public OneAdapter(List dataList) {
        this(dataList, null);
    }

    public OneAdapter(BaseEventMessenger eventHandlerAgent) {
        this(null, eventHandlerAgent);
    }

    public OneAdapter(List dataList, BaseEventMessenger eventMessenger) {
        this.mEventMessenger = eventMessenger;
        this.mDataList = dataList;
        mTypeToViewMap = new SparseArray<>();
        mItemViewRouterManager = new ItemViewRouterManager();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        try {
            Class<? extends View> aClass = mTypeToViewMap.get(viewType);//TODO 细化报错信息
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
        Object model = mDataList.get(position);
        if (holderItemView instanceof ItemView) {
            ItemView itemView = (ItemView) holderItemView;

            if (mEventMessenger != null) {
                View.OnClickListener itemClickListener = mEventMessenger.getItemClickListener(itemView.getClass());
                if (itemClickListener != null) {
                    holderItemView.setOnClickListener(itemClickListener);
                }
            }

            try {
                Class<?> modelClass = model.getClass();
                ItemViewRouter provider = mItemViewRouterManager.findProvider(modelClass);
                if (provider != null) {
                    provider.onBindDataAgent(position, model, mEventMessenger, itemView, this);
                }
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
                String errMsg = String.format("(%s.java:1) must implement IItemView interface! check %s", holderItemView.getClass().getSimpleName(), model.getClass().getSimpleName());//TODO
                throw new ClassCastException(errMsg);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object model = mDataList.get(position);
        Class<?> modelClazz = model.getClass();

        Class<? extends View> itemViewClazz = null;

        ItemViewRouter<Object> provider = mItemViewRouterManager.findProvider(modelClazz);
        if (provider != null) {
            itemViewClazz = provider.getItemView(position, model, mEventMessenger, this);
        } else {
            if (isDebug) {
                String errInfo = String.format("需要注册或者在数据实体上标记IItemViewProvider");//TODO
                throw new RuntimeException(errInfo);
            }
        }

        if (itemViewClazz != null) {
            int itemViewType = itemViewClazz.hashCode();
            mTypeToViewMap.put(itemViewType, itemViewClazz);
            return itemViewType;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public <T> void registerItemViewRouter(Class<T> modelClass, ItemViewRouter<T> itemViewRouter) {
        mItemViewRouterManager.registerItem(modelClass, itemViewRouter);
    }


    /*******************************/
    public List getDataList() {
        return mDataList;
    }

    public void clear() {
        List<?> datas = getDataList();
        if (datas != null) {
            datas.clear();
        }
        notifyDataSetChanged();
    }


    public void addItem(Object baseBean) {
        if (baseBean == null) {
            return;
        }
        if (mDataList == null) {
            mDataList = new ArrayList();
        }
        mDataList.add(baseBean);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void appendDatas(List datas) {
        if (datas == null) {
            return;
        }
        if (mDataList == null) {
            mDataList = new ArrayList();
        }
        int size = mDataList.size();
        mDataList.addAll(datas);
        notifyItemInserted(size);
    }

    public void setmDatas(List datas) {
        if (datas != null) {
            mDataList = datas;
            notifyDataSetChanged();
        }
    }
}
