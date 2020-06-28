package github.kuan.oneadapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kuan
 */
public class OneAdapter<E extends BaseEventAgent> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static boolean isDebug = true;

    protected List mDatas;
    protected E mBaseEventAgent;

    private Map<Class, IItemViewProvider> mapViewProviderCache = new HashMap<>();

    private List<TempHolder> mTempHolderSparseArray;

    public OneAdapter() {
        mTempHolderSparseArray = new ArrayList<>();
    }

    public OneAdapter(E baseEventAgent) {
        this();
        mBaseEventAgent = baseEventAgent;
    }

    public OneAdapter(List datas, E baseEventAgent) {
        this(baseEventAgent);
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //报错1
        TextView textView = new TextView(parent.getContext());
        textView.setText("viewType  " + viewType);
        return new ViewHolder(textView);



//        try {
//            TempHolder holder = mTempHolderSparseArray.get(viewType);
//            if (holder.viewClazz != null) {
//                return IItemViewViewHolder.createViewHolder(parent.getContext(), holder.viewClazz);
//            } else {
//                return holder.mViewHolder;
//            }
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//
//            TextView textView = new TextView(parent.getContext());
//            ViewHolder viewHolder = new ViewHolder(textView);
////            return new IItemViewViewHolder(new ItemViewWhenError(parent.getContext()));
//            return viewHolder;
//        }

//        View itemView = null;
//        try {
//            Class<? extends View> aClass = mTypeViews.get(viewType);
//            Constructor<? extends View> constructor = aClass.getConstructor(Context.class);
//            itemView = constructor.newInstance(parent.getContext());
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            itemView = new ItemViewWhenError(parent.getContext());
//        }
//        return new RecyclerView.ViewHolder(itemView) {
//        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object model = mDatas.get(position);
        holder.itemView.setBackgroundColor(Color.BLUE);
//        holder.bindData(model, mBaseEventAgent, position);
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
//        Object model = mDatas.get(position);
//        Class<?> modelClazz = model.getClass();
//
//        MapToView mapToViewAnnotation = modelClazz.getAnnotation(MapToView.class);
//        MapToViewProvider providerAnnotation = null;
//        Class<? extends View> itemViewClazz = null;
//        if (mapToViewAnnotation != null) {
//            itemViewClazz = mapToViewAnnotation.value();
//        }
//
//        if (itemViewClazz == null) {
//            providerAnnotation = modelClazz.getAnnotation(MapToViewProvider.class);
//            if (providerAnnotation != null) {
//                Class<? extends IItemViewProvider> providerClass = providerAnnotation.value();
//                IItemViewProvider viewProvider = mapViewProviderCache.get(providerClass);
//                if (viewProvider == null) {
//                    try {
//                        viewProvider = providerClass.newInstance();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InstantiationException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                if (viewProvider != null) {
//                    mapViewProviderCache.put(providerClass, viewProvider);
//                    itemViewClazz = viewProvider.getItemView(model, mBaseEventAgent, position);
//                } else {
//                    if (isDebug) {
//                        String errInfo = String.format("(%s.java:1) must has empty construction method!", viewProvider.getClass().getSimpleName());
//                        throw new RuntimeException(errInfo);
//                    }
//                }
//            }
//        }
//        if (mapToViewAnnotation == null && providerAnnotation == null) {
//            if (isDebug) {
//                String errInfo = String.format("(%s.java:0) need annotation: %s!", modelClazz.getSimpleName(), "@" + MapToView.class.getSimpleName() + " or @" + MapToViewProvider.class.getSimpleName());
//                throw new RuntimeException(errInfo);
//            }
//        }
//        if (itemViewClazz != null) {
//
//            TempHolder tempHolder = new TempHolder();
//            tempHolder.viewClazz = itemViewClazz;
//
//
//            int index = mTempHolderSparseArray.indexOf(tempHolder);
//            if (index > -1) {
//                return index;
//            } else {
//                mTempHolderSparseArray.add(tempHolder);
//                return mTempHolderSparseArray.size() - 1;
//            }
//        }
//        return -1;
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /********************************/
    public class TempHolder {
        public Class<? extends View> viewClazz;
        public ViewHolder mViewHolder;

        @Override
        public int hashCode() {
            if (viewClazz != null) {
                return viewClazz.hashCode();
            } else if (mViewHolder != null) {
                return mViewHolder.getLayoutId();
            }
            return super.hashCode();
        }

        public Class<? extends View> getViewClazz() {
            return viewClazz;
        }

        @Override
        public boolean equals(Object obj) {
            TempHolder that = (TempHolder) obj;
            if (viewClazz != null && viewClazz.equals(that.viewClazz)) {
                return true;
            }

            if (mViewHolder != null && mViewHolder.equals(that.mViewHolder)) {
                return true;
            }
            return super.equals(obj);
        }
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
