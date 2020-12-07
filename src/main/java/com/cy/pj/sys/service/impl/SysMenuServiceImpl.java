package com.cy.pj.sys.service.impl;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenusDao;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysRoleMenusDao sysRoleMenuDao;

    //查询所有的菜单，和父菜单名
    @Cacheable("menuCache")
    @Override
    public List<Map<String, Object>> findObject() {

        return sysMenuDao.findObject();
    }

    /**根据id删除菜单和菜单-角色关系*/
    @CacheEvict(value = "menuCache",allEntries = true,beforeInvocation = false)
    @Override
    public int deleteObject(Integer id) {
        //1.参数校验
        if(id==null||id<1){

            throw new IllegalArgumentException("id值无效");
        }
        //2.判定菜单是否有子菜单,有则不允许删除
        int childCount=sysMenuDao.getChildCount(id);
        if(childCount>0){
            throw new ServiceException("请先删除子菜单");

        }
        //3.删除关系数据
        sysRoleMenuDao.deleteObjectsByMenuId(id);
        int rows=sysMenuDao.deleteObject(id);
        //4.删除自身信息
        if(rows==0){

            throw new ServiceException("记录可能已经不存在");
        }
        return rows;
    }

    /**查询菜单结构（id，pid，name）*/
    @Override
    public List<Node> findZtreeMenuNodes() {
        return sysMenuDao.findZtreeMenuNodes();
    }

    //添加菜单
    @Override
    public Integer saveObject(SysMenu sysMenu) {
        if (sysMenu==null){
            throw new ServiceException("提交的菜单为空");
        }

        if (sysMenu.getName()==null ||sysMenu.getUrl()==null
                ||sysMenu.getPermission()==null ||sysMenu.getSort()==null){
            throw new ServiceException("提交的菜单不完整");
        }
        int rows = sysMenuDao.insertObject(sysMenu);
        return rows;
    }

    //修改菜单
    @Override
    public Integer updateObject(SysMenu sysMenu) {
        if (sysMenu==null){
            throw new ServiceException("提交的菜单为空");
        }
        int rows = sysMenuDao.updateObject(sysMenu);
        if (rows==0){
            throw new ServiceException("需要修改的菜单可能已经不存在了");
        }
        return rows;
    }


}
