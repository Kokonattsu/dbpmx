package com.cy.pj.sys.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SysRoleMenusDaoTests {

    @Autowired
    private SysRoleMenusDao sysRoleMenusDao;

    @Test
    void testSysRoleMenusDaoInsertObjects(){
        Integer[] menus={8};
        Integer rows = sysRoleMenusDao.insertObjects(49, menus);
        System.out.println(rows);
    }

    @Test
    void testFindByRoleId(){

    }

    @Test
    void testFindMenuIdsByRoleIds(){
        List<Integer> roleIds=new ArrayList<>();
        roleIds.add(1);
        roleIds.add(2);
        List<Integer> menuIds =
                sysRoleMenusDao.findMenuIdsByRoleIds(roleIds);
        for (Integer id:menuIds){
            System.out.println(id);
        }
    }
}
