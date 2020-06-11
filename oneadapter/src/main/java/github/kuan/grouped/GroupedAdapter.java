package github.kuan.grouped;

import java.util.List;

import github.kuan.oneadapter.BaseEventAgent;
import github.kuan.oneadapter.OneAdapter;

public class GroupedAdapter extends OneAdapter {
    public GroupedAdapter(List datas, BaseEventAgent baseEventAgent) {
        super(datas, baseEventAgent);
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
