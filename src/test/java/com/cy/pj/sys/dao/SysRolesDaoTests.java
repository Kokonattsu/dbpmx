package com.cy.pj.sys.dao;

import com.cy.pj.sys.pojo.SysRoleMenus;
import com.cy.pj.sys.pojo.SysRoles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysRolesDaoTests {
    @Autowired
    private SysRolesDao sysRolesDao;



    @Test
    void testSysRolesDaoInsertObject(){
        SysRoles sysRoles =new SysRoles();
        Integer object = sysRolesDao.insertObject(sysRoles);
        Assertions.assertNotNull(object);
    }


    @Test
    void testFindById(){
        SysRoleMenus roleMenus = sysRolesDao.findById(49);
        System.out.println(roleMenus);
    }
}
