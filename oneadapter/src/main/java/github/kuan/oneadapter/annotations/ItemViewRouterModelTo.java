package github.kuan.oneadapter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在model上，通过ItemViewProvider，使得数据Model映射到某一具体ItemView.class。
 *
 * @author kuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemViewRouterModelTo {

    Class<? extends github.kuan.oneadapter.interfaces.ItemViewRouter> value();

}
