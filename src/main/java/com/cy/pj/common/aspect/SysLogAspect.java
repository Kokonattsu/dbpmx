package com.cy.pj.common.aspect;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.pojo.util.IPUtils;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect     //切面
@Component  //组件
@Slf4j
public class SysLogAspect {

    /**
     *切入点：织入扩展功能的连接点的集合
     */
    @Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
    public void doLog(){}

    //
    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        try {
            //正常操作记录日志
            long t1=System.currentTimeMillis();
            //核心业务
            Object result = joinPoint.proceed();
            long t2=System.currentTimeMillis();
            Long runTime=(t2-t1);
            log.info(joinPoint
                    .getSignature().getName()+" Runtime is {} ms",runTime);
            //写入日志
            saveSysLog(joinPoint, runTime);
            return result;
        }catch (Throwable e){
            logError(joinPoint,e.getMessage());
            throw e;
        }

        //exceptionMsg
    }

    //异常操作
    private void logError(ProceedingJoinPoint joinPoint,String exceptionMsg)throws JsonProcessingException{
        Class<?> targetClass = joinPoint.getTarget().getClass();
        MethodSignature ms=(MethodSignature) joinPoint.getSignature();

        String className = targetClass.getName();
        String MethodName = ms.getMethod().getName();
        String classAndMethodName=className+"."+MethodName;
        String parameterNames = new ObjectMapper()
                .writeValueAsString(joinPoint.getArgs());

        //类-方法->参数->错误信息
        log.error("error.msg:-->{}-->{}-->{}",classAndMethodName,parameterNames,exceptionMsg);
    }
    //ProceedingJoinPoint代表运行时
    //ProceedingJoinPoint中的方法
    //getSignature()获取方法签名
    //getTarget()获取执行目标方法的对象（service实现类对象）
    //getArgs()获取运行时传递的参数列表

    @Autowired
    private SysLogService sysLogService;
    private void saveSysLog(
            ProceedingJoinPoint joinPoint,
            Long time) throws NoSuchMethodException, JsonProcessingException {
        //用来获取全类名和方法名
        Class<?> targetClass = joinPoint.getTarget().getClass();
        //用来获取方法名和参数类型（MethodSignature才能获取参数类型）
        MethodSignature ms=(MethodSignature) joinPoint.getSignature();
        //获取运行时方法名，用于获取注解()
        Method method = targetClass.
                getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        //获取操作名（注解中的值）
        RequiredLog RequiredLog = method.getAnnotation(RequiredLog.class);

//        System.out.println(joinPoint.getTarget().getClass().getName());
//        System.out.println(ms.getMethod().getName());
//        System.out.println(method.getName());


        SysLog log =new SysLog();
        log.setUsername("user");
        log.setIp(IPUtils.getIpAddr());
        log.setOperation(RequiredLog.value());
        log.setMethod(targetClass.getName()+"."+method.getName());
        //log.setParams(Arrays.toString(joinPoint.getArgs()));
        log.setParams(new ObjectMapper().writeValueAsString(joinPoint.getArgs()));
        log.setTime(time);
        log.setCreatedTime(new Date());

        //sysLogService.saveObject(log);
        //异步写日志

        new Thread(){
            @Override
            public void run() {
                sysLogService.saveObject(log);
            }
        }.start();




    }
}
