package org.lfh.blog_demo.aop;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE}) //这里的METHOD是指作用在方法上，而TYPE指的是作用到类上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String value() default "";

    String desc() default "";
}
