package github.kuan.oneadapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;

public class IItemViewViewHolder extends ViewHolder {

    public static ViewHolder createViewHolder(Context context, Class<? extends View> aClass) {
        View itemView;
        try {
            Constructor<? extends View> constructor = aClass.getConstructor(Context.class);
            itemView = constructor.newInstance(context);
        } catch (Exception ex) {
            ex.printStackTrace();
            itemView = new ItemViewWhenError(context);
        }
        return new IItemViewViewHolder(itemView);
    }


    public IItemViewViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Object model, BaseEventAgent event, int position) {
        View holderItemView = this.itemView;
        if (holderItemView instanceof IItemView) {
            IItemView itemView = (IItemView) holderItemView;
            try {
                itemView.bindData(model, event, position);
            } catch (Exception ex) {
                if (OneAdapter.isDebug) {
                    if (ex instanceof ClassCastException) {
                        StackTraceElement[] stackTrace = ex.getStackTrace();
                        if (stackTrace.length > 0) {
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
            if (OneAdapter.isDebug) {
                String errMsg = String.format("(%s.java:1) must implement IItemView interface!", holderItemView.getClass().getSimpleName());
                throw new ClassCastException(errMsg);
            }
        }
    }
}
