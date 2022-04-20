package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义一个LoginRequired注解，在用拦截器来判断所有使用该注解的方法是否登录
 *
 * @author wuzexin
 * @created 2022/4/20 08:11
 */
@Target(ElementType.METHOD) // 注解写在方法之上
@Retention(RetentionPolicy.RUNTIME) // 注解在程序运行时就有效
public @interface LoginRequired {
}
