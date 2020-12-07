package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRolesDao {

    //根据角色id删除用户-角色关系
    @Delete("delete from sys_user_roles where role_id=#{roleId}")
    Integer deleteObjectByRoleId(Integer roleId);

    //根据用户id查询角色id
    @Select("select role_id from sys_user_roles where user_id=#{userId}")
    List<Integer>  findRolesByUserId(Integer userId);

    //根据userid添加角色id
    Integer saveObjectsByUserId(Integer userId,Integer[] RoleIds);

    //根据用户id删除用户-角色关系
    @Delete("delete from sys_user_roles where user_id=#{userId}")
    Integer deleteRoleIdsByUserId(Integer userId);
}
