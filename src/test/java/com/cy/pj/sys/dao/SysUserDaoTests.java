package com.cy.pj.sys.dao;

import com.cy.pj.sys.pojo.SysUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class SysUserDaoTests {
    @Autowired
    private SysUserDao sysUserDao;

    @Test
    void testFindObjects(){
        List<SysUser> userList = sysUserDao.findPageObjects(null, 1, 4);
        for (SysUser u:userList){
            System.out.println(u);
        }

    }

    @Test
    void testGetRowCount(){
        Integer rowCount = sysUserDao.getRowCount(null);
        System.out.println(rowCount);
    }

    @Test
    void testfindObjectById() throws JsonProcessingException {
        SysUser user = sysUserDao.findObjectById(7);
        ObjectMapper mapper=new ObjectMapper();
        String s = mapper.writeValueAsString(user);
        System.out.println(s);
        user.setUsername("superUser");
        Integer row = sysUserDao.updateObject(user);
        System.out.println(row);

    }

    @Test
    public void testInsertObject(){
        SysUser sysUser=new SysUser();
        sysUser.setUsername("本子魔");
        sysUser.setPassword("66666");
        sysUser.setModifiedUser("idea");
        sysUser.setEmail("23445345345@qq.com");
        Integer integer = sysUserDao.insertObject(sysUser);
        System.out.println(integer);
    }


        //密码加密
    @Test
    void getPasword(){
        String password = "123";
        //String salt = UUID.randomUUID().toString();
        String salt = "8cf79c38-d2bc-45bb-a68b-b36bc2769bee";
        System.out.println("salt：");
        System.out.println(salt);
        SimpleHash sh = new SimpleHash("MD5", password, salt, 1);
        String newpassword = sh.toHex();
        System.out.println("newpassword：");
        System.out.println(newpassword);
    }


    @Test
    void testUpdatePasswordByUserName(){
        String username="本子神";
        String password="666";
        String salt = UUID.randomUUID().toString();
        SimpleHash sh = new SimpleHash("MD5", password, salt, 1);
        String newpassword = sh.toHex();

        Integer row = sysUserDao.updatePasswordByUserName(username, newpassword, salt);


    }
}
