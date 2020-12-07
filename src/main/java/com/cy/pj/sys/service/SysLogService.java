package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysLog;

public interface SysLogService {
    /**
     *
     * @param username      基于条件查询时的参数名
     * @param pageCurrent   当前页数
     * @return              当前页数据和页面数据封装成的对象
     *(返回值对象封装了当前页值，页长度，总长度，总页数，查询到的数据)
     */
    PageObject<SysLog> findPageObject(String username,
                                      Integer pageCurrent,
                                      Integer pageSize);

    Integer deleteLogs(Integer... ids);


    void saveObject(SysLog entity);


}
