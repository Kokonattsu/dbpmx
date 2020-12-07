package com.cy.pj.sys.dao;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.pojo.SysMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuDao {

    //查询所有的菜单，和父菜单名
    List<Map<String,Object>> findObject();

    //查询菜单的子菜单数量
    @Select("select count(*) from sys_menus where parentId=#{id}")
    int getChildCount(Integer id);

    //根据id删除菜单
    @Delete("delete from sys_menus where id=#{id}")
    int deleteObject(Integer id);

    //查询菜单名字和结构
    @Select("select id,name,parentId from sys_menus")
    List<Node> findZtreeMenuNodes();

    //添加菜单
    int insertObject(SysMenu entity);

    //修改菜单
    int updateObject(SysMenu entity);

    //根据多个部门id查询权限标识
    List<String> findPermissionsByMenuIds(List<Integer> MenuIds);
}
