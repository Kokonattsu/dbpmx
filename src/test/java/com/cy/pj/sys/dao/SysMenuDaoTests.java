package com.cy.pj.sys.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SysMenuDaoTests {
    @Autowired
    private SysMenuDao sysMenuDao;

    @Test
    void testFindPermissionsByMenuIds(){
        List<Integer> menuIds=new ArrayList<>();
        menuIds.add(8);
        menuIds.add(25);
        List<String> permissions =
                sysMenuDao.findPermissionsByMenuIds(menuIds);
        for (String p:permissions){
            System.out.println(p);
        }

    }
}
