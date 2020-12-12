package github.kuan.oneadapter.listener;

import android.view.View;

import androidx.annotation.NonNull;

import github.kuan.oneadapter.OneAdapter;

/**
 * @author kuan
 * @date 2020/12/12
 */
public interface OnItemClickListener {

    void onItemClick(@NonNull OneAdapter adapter, @NonNull View view, int position);

}
