package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.pojo.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuService {

    //查询菜单（使用map接收）
    List<Map<String,Object>> findObject();

    //根据id删除菜单
    int deleteObject(Integer id);

    //查询菜单结构
    List<Node> findZtreeMenuNodes();

    //添加菜单
    Integer saveObject(SysMenu sysMenu);

    //修改菜单
    Integer updateObject(SysMenu sysMenu);
}
