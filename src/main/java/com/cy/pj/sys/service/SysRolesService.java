package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysRoleMenus;
import com.cy.pj.sys.pojo.SysRoles;

import java.util.List;
import java.util.Map;

public interface SysRolesService {
    //查询所有角色
    PageObject<SysRoles> findPageObjects(String name, Integer pageCurrent);

    List<Map<String,Object>> findRoles();

    //根据id查询角色
    SysRoleMenus findObjectById(Integer id);

    //根据id删除角色
    Integer deleteObjectByRoleId(Integer roleId);

    /**添加角色*/
    Integer saveObject(SysRoles sysRoles,Integer[] menuIds);

    //修改角色
    Integer updateObject(SysRoles sysRoles,Integer[] menuIds);


}
