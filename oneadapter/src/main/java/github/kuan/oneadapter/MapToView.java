package github.kuan.oneadapter;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在model类上，一对一关系，model直接映射到view
 *
 * @author kee
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapToView {
    Class<? extends View> value();
}
