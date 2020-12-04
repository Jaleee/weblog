package com.jale.weblog.user.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* com.jale.weblog.user.app.controller.*.*(..))")
//    @Pointcut("within(com.jale.weblog.user.app.controller.*)") // 匹配指定类型的类实例，最细粒度为类
//    @Pointcut("@within(com.jale.weblog.common.annotations.IgnoreAuth)") // 匹配带有指定注解的类
//    @Pointcut("args(com.jale.weblog.user.api.dataobject.User, ..)") // 匹配参数类型和参数数量
//    @Pointcut("@args(com.jale.weblog.common.annotations.IgnoreAuth)") // 匹配指定注解标注的类作为某个方法的参数的方法
//    @Pointcut("@annotation(com.jale.weblog.common.annotations.IgnoreAuth)") // 匹配带有指定注解的方法
    public void MyAspect(){}

    /*@Before("MyAspect()")
    public void before() {
        System.out.println("Before");
    }

    @After("MyAspect()")
    public void after() {
        System.out.println("After");
    }

    @AfterReturning("MyAspect()")
    public void afterReturn() {
        System.out.println("AfterReturning");
    }

    @AfterThrowing("MyAspect()")
    public void afterThrowing() {
        System.out.println("AfterThrowing");
    }

    @Around("MyAspect()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        try {
            System.out.println("Around1");
            point.proceed();
            System.out.println("Around2");
        }catch (Throwable throwable) {
            System.out.println("AroundException");
        }

    }*/
}
