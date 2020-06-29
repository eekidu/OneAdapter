package github.kuan.grouped;

import java.util.List;

import github.kuan.oneadapter.BaseEventHandlerAgent;
import github.kuan.oneadapter.OneAdapter;

public class GroupedAdapter extends OneAdapter {
    public GroupedAdapter(List datas, BaseEventHandlerAgent baseEventHandlerAgent) {
        super(datas, baseEventHandlerAgent);
    }

    @Override
    public int getItemCount() {

        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        mDatas.get(position);

        return super.getItemViewType(position);
    }
}
