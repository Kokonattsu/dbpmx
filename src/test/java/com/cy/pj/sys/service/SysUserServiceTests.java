package com.cy.pj.sys.service;

import com.cy.pj.sys.pojo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserServiceTests {
    @Autowired
    private SysUserService sysUserService;

    @Test
    void testSaveObject(){
        SysUser user=new SysUser();
        user.setUsername("wangdachui");
        user.setPassword("123456");
        Integer[] RoleIds={48,49};
        Integer row = sysUserService.saveObject(user, RoleIds);
        System.out.println(row);
    }
    @Test
    void testUpObjectById(){
        SysUser user=new SysUser();
        user.setId(22);
        user.setUsername("www");
        Integer[] RoleIds={58,59,60};
        sysUserService.updateObjectById(user,RoleIds);


    }
}
