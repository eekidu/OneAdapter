package github.kuan.grouped;

import java.util.List;

import github.kuan.oneadapter.BaseEventMessenger;
import github.kuan.oneadapter.OneAdapter;

public class GroupedAdapter extends OneAdapter {
    public GroupedAdapter(List datas, BaseEventMessenger baseEventMessenger) {
        super(datas, baseEventMessenger);
    }

    @Override
    public int getItemCount() {

        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        mDataList.get(position);

        return super.getItemViewType(position);
    }
}
