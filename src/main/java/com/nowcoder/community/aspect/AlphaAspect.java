package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Aspect示例
 *
 * @author wuzexin
 * @created 2022/4/26 14:21
 */

//@Component
//@Aspect
public class AlphaAspect {

    // 第一个*表示方法的返回值，第二个*所有的类，第三个*所有的方法，（..）表示所有的参数
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {

    }

    // 连接点开始
    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    // 有了返回值以后
    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    // 抛出异常之后
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    // 前后都织入逻辑
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before");
        // 调用要处理的目标组件的方法
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }
}
