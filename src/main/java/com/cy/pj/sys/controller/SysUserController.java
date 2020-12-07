package com.cy.pj.sys.controller;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("user/doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        JsonResult result = new JsonResult(
                sysUserService.findPageObjects(username, pageCurrent)
        );
        return result;
    }

    @PostMapping("user/doValidById")
    public JsonResult doValidById(Integer id,Integer valid,String modifiedUser){
        sysUserService.updateValidById(id,valid,modifiedUser);
        return new JsonResult("状态修改成功");
    }

    @GetMapping("user/doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(
                sysUserService.findObjectById(id)
        );
    }

    @RequestMapping("/user/doLogin")
    public JsonResult doLogin(
            String username,
            String password,
            boolean isRemenberMe){
        //获取Subject对象，此对象用来将验证信息传递给SecurityManager
        Subject subject=SecurityUtils.getSubject();
        //UsernamePasswordToken：shiro框架中用来储存和传递账号密码的实体类
        UsernamePasswordToken token=new UsernamePasswordToken(username, password);
        //把记住我状态传递给
        token.setRememberMe(isRemenberMe);
        //login方法传递登录验证的参数
        subject.login(token);
        return new JsonResult("登录成功");
    }

    //添加用户
    @PostMapping("user/doSaveObject")
    public JsonResult doSaveObject(SysUser sysUser,Integer[] roleIds){
        sysUserService.saveObject(sysUser, roleIds);
        return new JsonResult("添加成功");
    }

    //修改用户
    @PostMapping("user/doUpdateObject")
    public JsonResult doUpdateObject(SysUser sysUser,Integer[] roleIds){
        sysUserService.updateObjectById(sysUser, roleIds);
        return new JsonResult("修改成功");
    }

    //修改密码
    @PostMapping("user/doUpdatePassword")
    public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd){
        sysUserService.updatePassword(pwd, newPwd, cfgPwd);
        return new JsonResult("密码修改成功");
    }
}
