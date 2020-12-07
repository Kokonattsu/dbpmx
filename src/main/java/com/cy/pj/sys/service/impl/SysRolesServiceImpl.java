package com.cy.pj.sys.service.impl;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysRoleMenusDao;
import com.cy.pj.sys.dao.SysRolesDao;
import com.cy.pj.sys.pojo.SysRoleMenus;
import com.cy.pj.sys.pojo.SysRoles;
import com.cy.pj.sys.service.SysRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class SysRolesServiceImpl implements SysRolesService {

    @Autowired
    private SysRolesDao sysRolesDao;

    @Autowired
    private SysRoleMenusDao sysRoleMenusDao;

    //查询所有角色
    @Override
    public PageObject<SysRoles> findPageObjects(String name,Integer pageCurrent) {
        if (pageCurrent==null||pageCurrent<0){
            throw new ServiceException("当前页码值无效");
        }

        Integer rowCount = sysRolesDao.getRowCount(name);
        if (rowCount==null){
            throw new ServiceException("查询结果为空");
        }
        int pageSize=7;
        int startIndex=(pageCurrent-1)*pageSize;
        //查询角色并分页
        List<SysRoles> pageObjects =
                sysRolesDao.findPageObjects(name, startIndex, pageSize);
        //将返回值封装到分页对象


        PageObject pageObject=new PageObject(
                pageCurrent,pageSize,rowCount,pageObjects);


        return  pageObject;
    }

    //根据id查询
    @RequiredLog("查询角色")
    @Override
    public SysRoleMenus findObjectById(Integer id) {
        SysRoleMenus rolemenus=new SysRoleMenus();
        List<Integer> menuIds = sysRoleMenusDao.findMenusByRoleId(id);
        SysRoles role = sysRolesDao.findObjectById(id);
        rolemenus.setId(role.getId());
        rolemenus.setName(role.getName());
        rolemenus.setNote(role.getNote());
        rolemenus.setMenuIds(menuIds);

        return rolemenus;
    }

    //根据id删除角色和角色关系
    @RequiredLog("删除角色")
    @Override
    public Integer deleteObjectByRoleId(Integer roleId) {
        if (roleId==null||roleId<0){
            throw new ServiceException("需要传递正确的id");
        }
        Integer result= sysRolesDao.deleteObjectByRoleId(roleId);
        sysRoleMenusDao.deleteObjectByRoleId(roleId);
        if (result==0){
            throw new ServiceException("需要删除的数据可能已经不存在了");
        }
        return result;
    }

    @RequiredLog("添加角色")
    @Override
    public Integer saveObject(SysRoles sysRoles,Integer[] menuIds) {

        if (sysRoles==null||menuIds==null){
            throw new ServiceException("传递的参数为不正确");
        }
        //添加角色和角色-菜单关系
        Integer rows = sysRolesDao.insertObject(sysRoles);
        sysRoleMenusDao.insertObjects(sysRoles.getId(), menuIds);//此处传递的id必须在添加角色后返回主键
        return rows;
    }

    @RequiredLog("修改角色")
    @Override
    public Integer updateObject(SysRoles sysRoles, Integer[] menuIds) {
        //校验

        Integer rows = sysRolesDao.updateObject(sysRoles);
        sysRoleMenusDao.deleteObjectByRoleId(sysRoles.getId());
        sysRoleMenusDao.insertObjects(sysRoles.getId(), menuIds);

        return rows;

    }

    @Override
    public List<Map<String, Object>> findRoles() {
        return sysRolesDao.findRoles();
    }
}
