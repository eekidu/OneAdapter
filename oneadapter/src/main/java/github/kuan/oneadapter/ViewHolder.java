package github.kuan.oneadapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public int getLayoutId() {
        return 0;
    }


    public void bindData(Object model, BaseEventAgent event, int position) {



    }
}
