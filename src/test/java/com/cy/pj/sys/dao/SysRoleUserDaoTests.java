package com.cy.pj.sys.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysRoleUserDaoTests {
    @Autowired
    private SysUserRolesDao sysUserRolesDao;

    @Test
    void testSaveObjectsByUserId(){
        Integer[] roleIds={90,91,92};
        Integer integer = sysUserRolesDao.saveObjectsByUserId(
                1, roleIds);
        System.out.println(integer);
    }

}
