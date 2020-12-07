package com.cy.pj.sys.dao;

import com.cy.pj.sys.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserDao {

    Integer getRowCount(String username);

    List<SysUser> findPageObjects(String username,
                                  Integer startIndex,
                                  Integer pageSize);

    //修改禁用状态
    @Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser} where id=#{id}")
    Integer updateValidById(Integer id,Integer valid,String modifiedUser);

    //根据id查询用户
    SysUser findObjectById(Integer id);
    //添加用户
    Integer insertObject(SysUser sysUser);

    Integer updateObject(SysUser sysUser);

    //根据用户名查询用户
    @Select("select * from sys_users where username=#{username}")
    SysUser findUserByUserName(String username);

    //根据用户名修改密码
    @Update("update sys_users set " +
            "password=#{newPassword},salt=#{newSalt},modifiedTime=now() " +
            "where username=#{username}")
    Integer updatePasswordByUserName(String username,String newPassword,String newSalt);
}
