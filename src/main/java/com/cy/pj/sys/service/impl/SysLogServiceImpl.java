package com.cy.pj.sys.service.impl;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    //@RequiredLog("日志查询")    //为描述的方法添加切面
    @Override
    public PageObject<SysLog> findPageObject(
            String username, Integer pageCurrent,Integer pageSize) {
        //验证参数  ||短路或
        if(pageCurrent==null||pageCurrent<0){
            throw new ServiceException("当前页参数不正确");};
        //查询总页数并校验
        Integer rowCount = sysLogDao.getRowCount(username);
        if(rowCount==0) {
            throw new ServiceException("查询数据不存在");
        }else {
            //查询结果并返回结果对象
            //求出当前页的起始位置
            int starIndex=(pageCurrent-1)*pageSize;
            //显示的一页数据
            List<SysLog> records =
                    sysLogDao.findPageObjects(
                            username, starIndex, pageSize);
            //返回值对象封装了当前页值，页长度，总长度，查询到的数据
            return new PageObject<SysLog>(pageCurrent,pageSize,rowCount,records);
        }
    }

    //RequiresPermissions注解描述的方法为权限切入点方法，登录用户访问此方法时需要授权
    @RequiresPermissions("sys:log:delete")
    @RequiredLog("删除日志")
    @Override
    public Integer deleteLogs(Integer... ids) {
        //验证参数，执行业务，验证结果
        if (ids==null||ids.length==0){
            throw new ServiceException("删除数据必须提供正确的id值");
        };

        Integer rows = sysLogDao.deleteObjects(ids);

        if(rows==0){
            throw new ServiceException("找不到需要删除的数据");
        }else {
            return rows;
        }

    }

    @Async
    @Override
    public void saveObject(SysLog entity) {
        sysLogDao.insertObject(entity);
    }


}
