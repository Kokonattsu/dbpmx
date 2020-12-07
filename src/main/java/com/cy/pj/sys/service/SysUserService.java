package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysUser;

import java.util.Map;

public interface SysUserService {

    //查询全部数据并分页
    PageObject<SysUser> findPageObjects(String username,Integer pageCurrent);

    //根据id修改valid值
    Integer updateValidById(Integer id,Integer valid,String modifiedUser);

    Map<String, Object> findObjectById(Integer id);

    Integer saveObject(SysUser sysUser,Integer[] RoleIds);

    Integer updateObjectById(SysUser sysUser,Integer[] RoleIds);

    Integer updatePassword(String password,String newPassword,String cfgPassword);
}
