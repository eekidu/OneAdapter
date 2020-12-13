package github.kuan.oneadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import github.kuan.oneadapter.imple.ItemViewWhenError;
import github.kuan.oneadapter.interfaces.ItemView;
import github.kuan.oneadapter.interfaces.ItemViewRouter;
import github.kuan.oneadapter.internal.ItemViewRouterManager;
import github.kuan.oneadapter.internal.OneToOneItemViewRouter;
import github.kuan.oneadapter.listener.OnItemClickListener;

/**
 * @author kuan
 */
public class OneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * debug开关，影响异常的处理方式。release会隐藏掉异常的ItemView。
     */
    private static boolean isDebug = false;

    /**
     * 开发阶段，会把异常作为item进行显示，不影响调试其他条目。
     */
    private static boolean isInDeveloping = false;

    public static boolean isDebug() {
        return isDebug;
    }

    public static boolean isInDeveloping() {
        return isInDeveloping;
    }

    public static void setDebug(boolean isDebug, boolean isInDeveloping) {
        OneAdapter.isDebug = isDebug;
        OneAdapter.isInDeveloping = isInDeveloping;
    }


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
    private final SparseArray<Class<? extends View>> mTypeToViewMap;

    private final ItemViewRouterManager mItemViewRouterManager;

    private WeakReference<RecyclerView> mRecyclerViewWeakReference;

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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerViewWeakReference = new WeakReference<>(recyclerView);
        if (mEventMessenger != null) {
            mEventMessenger.setRecyclerView(recyclerView);
        }
    }

    public void setEventMessenger(BaseEventMessenger eventMessenger) {
        this.mEventMessenger = eventMessenger;
        if (mRecyclerViewWeakReference != null && mRecyclerViewWeakReference.get() != null) {
            mEventMessenger.setRecyclerView(mRecyclerViewWeakReference.get());
        }
        if (mTypeToViewMap.size() > 0) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object model = mDataList.get(position);
        if (model != null) {
            Class<?> modelClazz = model.getClass();
            ItemViewRouter<Object> itemViewRouter = mItemViewRouterManager.finItemViewRouterBy(modelClazz);

            Class<? extends View> itemViewClazz = null;
            if (itemViewRouter != null) {
                //让数据找到ItemView
                itemViewClazz = itemViewRouter.getItemView(position, model, mEventMessenger, this);
                if (itemViewClazz != null) {
                    //生成Type值记录下来
                    int itemViewType = generateTypeBy(itemViewClazz);
                    mTypeToViewMap.put(itemViewType, itemViewClazz);
                    return itemViewType;
                } else {
                    //返回的ItemView为null
                    if (isDebug() && !isInDeveloping()) {
                        String errInfo = String.format("(%s.java:0) 没有返回ItemView。数据:%s",
                                itemViewRouter.getClass().getSimpleName(), model.toString());
                        throw new RuntimeException(errInfo);
                    }
                }
            }
        }

        //-1 代表该数据找不到ItemView。
        return -1;
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
            itemView = new ItemViewWhenError(parent.getContext());
        }

        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(itemView) {
        };
        addItemClickListener(viewHolder);
        return viewHolder;
    }

    private void addItemClickListener(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition() - getHeadersCount();
                if (mEventMessenger != null) {
                    OnItemClickListener onItemClickListener = mEventMessenger.getItemClickListener(((ItemView) v).getClass());
                    if (onItemClickListener == null) {
                        onItemClickListener = mEventMessenger.getOnItemClickListener();
                    }
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(OneAdapter.this, v, position);
                    }
                }
            }
        });
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View holderItemView = holder.itemView;
        Object model = mDataList.get(position);
        if (holderItemView instanceof ItemView) {
            ItemView itemView = (ItemView) holderItemView;
            try {
                Class<?> modelClass = model.getClass();
                ItemViewRouter provider = mItemViewRouterManager.finItemViewRouterBy(modelClass);
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
                String errMsg = String.format("(%s.java:1) must implement %s.class interface! From %s.class",
                        holderItemView.getClass().getSimpleName(),
                        ItemView.class.getSimpleName(),
                        model.getClass().getSimpleName());
                throw new ClassCastException(errMsg);
            }
        }
    }

    public int getHeadersCount() {
        return 0;
    }


    /**
     * 根据ItemView的class对象生成唯一且固定的Type。
     * 这里不用放入集合中的index做Type值是因为多个adapter时，同一种ItemView的Type不固定，不利于全局的复用。
     *
     * @param itemViewClazz
     * @return
     */
    private int generateTypeBy(Class<? extends View> itemViewClazz) {
        return itemViewClazz.hashCode();//没想到特别好的，暂时用hashCode，可能会
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    /**
     * 适用于一些不方便加注解的数据实体类
     *
     * @param modelClass
     * @param itemViewClazz
     * @param <T>
     */
    public <T> void registerItemView(Class<T> modelClass, Class<? extends View> itemViewClazz) {
        OneToOneItemViewRouter oneToOneItemViewRouter = new OneToOneItemViewRouter(itemViewClazz);
        mItemViewRouterManager.registerItem(modelClass, oneToOneItemViewRouter);
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

    public void appendDataList(List datas) {
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

    public void setDataList(List datas) {
        if (datas != null) {
            mDataList = datas;
            notifyDataSetChanged();
        }
    }
}