package com.cy.pj.sys.dao;

import com.cy.pj.sys.pojo.SysLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysLogDaoTests {
    @Autowired
    private SysLogDao sysLogDao;

    @Test
    void testgetRowCount(){
        Integer admin = sysLogDao.getRowCount("jjj");
        System.out.println(admin);
    }

    @Test
    void testFindPageObjects(){
        List<SysLog> logsList =
                sysLogDao.findPageObjects("admin", 0, 5);
        for (SysLog logs:logsList){
            System.out.println(logs);
        }
        //logsList.forEach(log-> System.out.println(log));
    }

    @Test
    public void testdeletObjects(){
        Integer deleteObjects = sysLogDao.deleteObjects(11,12,13);
        System.out.println("删除数据："+deleteObjects);
    }

}
