package com.cy.pj.sys.dao;

import com.cy.pj.sys.pojo.SysRoleMenus;
import com.cy.pj.sys.pojo.SysRoles;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRolesDao {

    //查询所有角色并分页
    List<SysRoles> findPageObjects(
            String name,Integer startIndex,Integer pageSize);

    //根据id查询角色
    @Select("select * from sys_roles where id=#{id}")
    SysRoles findObjectById(Integer id);

    @Select("select id,name from sys_roles")
    List<Map<String,Object>> findRoles();

    //根据id查询修改name，note，角色-菜单关系
    SysRoleMenus findById(Integer id);

    //查询角色数据的条数
    Integer getRowCount(String name);

    //根据角色id删除角色
    @Delete("delete from sys_roles where id=#{id}")
    Integer deleteObjectByRoleId(Integer id);

    //添加角色
    Integer insertObject(SysRoles sysRoles);

    //修改角色
    Integer updateObject(SysRoles sysRoles);

}
