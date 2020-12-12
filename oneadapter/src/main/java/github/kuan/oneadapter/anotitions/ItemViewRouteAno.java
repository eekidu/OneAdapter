package github.kuan.oneadapter.anotitions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import github.kuan.oneadapter.interfaces.ItemViewRouter;

/**
 * 标记在model上，通过ItemViewProvider，使得数据Model映射到某一具体ItemView.class。
 *
 * @author kuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemViewRouteAno {

    Class<? extends ItemViewRouter> value();

}
