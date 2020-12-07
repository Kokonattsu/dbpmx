package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysLogServiceTests {

    @Autowired
    private SysLogService sysLogService;

    @Test
    public void testFindPageObject(){
        PageObject<SysLog> adminpage =
                sysLogService.findPageObject("admin", 1,1);
        System.out.println(adminpage);
    }

    @Test
    public void testDeletLogs(){
        Integer logs = sysLogService.deleteLogs(14);
        System.out.println("删除："+logs);
    }
}
