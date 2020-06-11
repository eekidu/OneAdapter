package github.kuan.oneadapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在model上，多对多关系，通过ItemViewProvider，使得model映射到某一ItemView。
 *
 * @author kuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapToViewProvider {

    Class<? extends IItemViewProvider> value();

}
