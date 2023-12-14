package javax.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServlet {
    boolean asyncSupported() default false;

    String description() default "";

    String displayName() default "";

    WebInitParam[] initParams() default {};

    String largeIcon() default "";

    int loadOnStartup() default -1;

    String name() default "";

    String smallIcon() default "";

    String[] urlPatterns() default {};

    String[] value() default {};
}
