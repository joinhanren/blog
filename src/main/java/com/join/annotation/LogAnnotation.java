package com.join.annotation;

import java.lang.annotation.*;

/**
 * @author join
 * @Description
 * @date 2022/9/5 22:40
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";
    String operation() default "";
}
