package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**关系表，储存父菜单和子菜单的关系*/
@Mapper
public interface SysRoleMenusDao {

    /**基于菜单id执行关系数据的删除*/
    @Delete("delete from sys_role_menus where menu_id=#{menuId}")
    int deleteObjectsByMenuId(Integer menuId);


    //根据角色id删除角色-菜单关系
    @Delete("delete from sys_role_menus where role_id=#{roleId}")
    Integer deleteObjectByRoleId(Integer roleId);

    /**添加*/
    Integer insertObjects(Integer roleId,Integer[] menuIds);

    //根据角色id查询菜单id
    @Select("select menu_id from sys_role_menus where role_id=#{roleId}")
    List<Integer> findMenusByRoleId(Integer roleId);

    //根据多个角色id查询菜单id
    List<Integer> findMenuIdsByRoleIds(List<Integer> roleIds);



}
