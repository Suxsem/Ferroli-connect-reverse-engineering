package javax.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.servlet.DispatcherType;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebFilter {
    boolean asyncSupported() default false;

    String description() default "";

    DispatcherType[] dispatcherTypes() default {DispatcherType.REQUEST};

    String displayName() default "";

    String filterName() default "";

    WebInitParam[] initParams() default {};

    String largeIcon() default "";

    String[] servletNames() default {};

    String smallIcon() default "";

    String[] urlPatterns() default {};

    String[] value() default {};
}
