package github.kuan.oneadapter;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明在model上，标记该model应用的ItemViewProvider
 *
 * @author kuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewProvider {

    Class<? extends IItemViewProvider> value();
}
