package com.cy.pj.common.aspect;

import com.cy.pj.common.annotation.ClearCache;
import com.cy.pj.common.annotation.RequiredCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class SysCacheAspect {

    private Map<String,Map<String,Object>> cacheMap=new ConcurrentHashMap<>();

    //环绕通知
    @Around("com.cy.pj.common.aspect.PointCut.doCache()")
    public Object doCacheAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("select Data...");
        //获得目标方法对象RequiredCache注解上的name值作为模块缓存的key
        String cacheName = getCacheName(RequiredCache.class, joinPoint);
        //从缓存Map中获取模块缓存
        Map<String, Object> cache = cacheMap.get(cacheName);
        if (cache==null){
            //如果不存在模块缓存，则新建一个加入缓存Map中
            cache=new ConcurrentHashMap();
            cacheMap.put(cacheName,cache);
        }

        Object result = cache.get("MethodCache");
        if (result!=null){
            return result;//从缓存中查到数据则返回缓存数据
        }else {
            //目标方法
            result = joinPoint.proceed();//缓存中没有数据则从业务层获取，
            System.out.println("select Data from database.....");
            cache.put("MethodCache", result);//将查到的数据存入模块缓存
            return result;
        }

    }

    //目标方法成功执行后通知
    @AfterReturning("com.cy.pj.common.aspect.PointCut.doClearCache()")
    public void doClearCache(JoinPoint joinPoint) throws NoSuchMethodException {
        String cacheName = getCacheName(ClearCache.class, joinPoint);
        Map<String, Object> cache = cacheMap.get(cacheName);
        cache.clear();
    }

    //获取目标方法注解上的name属性(作为全体缓存的key)
    private String getCacheName(
            Class<? extends Annotation> annotationClass,
            JoinPoint joinpoint)
            throws NoSuchMethodException {
        Annotation annotation = getAnnotation(annotationClass, joinpoint);
        //根据目标方法获取目标注解
        String cacheName="";
        //如果是正确的注解就获取注解中的name属性
        if(annotation instanceof RequiredCache){
            cacheName = ((RequiredCache) annotation).name();
        }else if (annotation instanceof ClearCache){
            cacheName = ((ClearCache) annotation).name();
        }
        return cacheName;
    }

    //获取目标方法注解上的Key属性(作为模块缓存的key)
    private String getCacheKey(
            Class<? extends Annotation> annotationClass,JoinPoint joinpoint)
            throws NoSuchMethodException {
        Annotation annotation = getAnnotation(annotationClass, joinpoint);
        String cacheName="";
        //如果是正确的注解就获取注解中的name属性
        if(annotation instanceof RequiredCache){
            cacheName = ((RequiredCache) annotation).name();
        }else if (annotation instanceof ClearCache){
            cacheName = ((ClearCache) annotation).name();
        }
        return cacheName;
    }

    //根据目标方法获取目标注解
    private Annotation getAnnotation(
            Class<? extends Annotation> annotationClass,
            JoinPoint joinpoint) throws NoSuchMethodException {
        Class<?> targetClass = joinpoint.getTarget().getClass();
        MethodSignature ms = (MethodSignature)joinpoint.getSignature();
        Method method = targetClass.getMethod(ms.getName(), ms.getParameterTypes());
        Annotation annotation = method.getAnnotation(annotationClass);
        return annotation;
    }
}
