package com.cy.pj.common.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCut {

    @Pointcut("bean(sysUserServiceImpl)")
    public void doTime(){}

    @Pointcut("@annotation(com.cy.pj.common.annotation.RequiredCache)")
    public void doCache(){}

    @Pointcut("@annotation(com.cy.pj.common.annotation.ClearCache)")
    public void doClearCache(){}
}
