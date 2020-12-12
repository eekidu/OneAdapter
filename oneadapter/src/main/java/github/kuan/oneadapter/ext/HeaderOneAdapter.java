package github.kuan.oneadapter.ext;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;

public class HeaderOneAdapter extends OneAdapter {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public HeaderOneAdapter() {
    }

    public HeaderOneAdapter(List dataList) {
        super(dataList);
    }

    public HeaderOneAdapter(BaseEventMessenger eventHandlerAgent) {
        super(eventHandlerAgent);
    }

    public HeaderOneAdapter(List dataList, BaseEventMessenger eventMessenger) {
        super(dataList, eventMessenger);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mHeaderViews.get(viewType);
        if (view != null) {
            return new RecyclerView.ViewHolder(view) {
            };
        }

        view = mFootViews.get(viewType);
        if (view != null) {
            return new RecyclerView.ViewHolder(view) {
            };
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        super.onBindViewHolder(holder, position - getHeadersCount());
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - super.getItemCount());
        }
        return super.getItemViewType(position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + super.getItemCount() + getFootersCount();
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + super.getItemCount();
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(view.hashCode(), view);
        notifyDataSetChanged();
    }

    public void addFootView(View view) {
        mFootViews.put(view.hashCode(), view);
        notifyDataSetChanged();
    }

    @Override
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

}
