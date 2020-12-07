package com.cy.pj.sys.controller;

import com.cy.pj.sys.pojo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
    //返回主页
    @CrossOrigin
    @GetMapping("doIndexUI")
    public String doIndexUI(Model model){
        SysUser user=(SysUser) SecurityUtils.
                getSubject().getPrincipal();
        model.addAttribute("username", user.getUsername());
        return "starter";
    }

//    //返回日志页
//    @CrossOrigin
//    @GetMapping("log/log_list")
//    public String doLogUI(){
//        return "sys/log_list";
//    }

    //返回分页页面
   // @CrossOrigin
    @GetMapping("doPageUI")
    public String doPageUI(){
        return "common/page";
    }


    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }

//    //部门管理
//    @GetMapping("dept/dept_list")
//    public String doDeptUI(){
//        return "sys/dept_list";
//    }
//
//    //菜单管理
//    @GetMapping("menu/menu_list")
//    public String doMenuUI(){
//        return "sys/menu_list";
//    }

    //页面呈现
    @CrossOrigin
    @GetMapping("/{moudule}/{mouduleUI}")
    public String doMouduleUI(@PathVariable String mouduleUI){
        return "sys/"+mouduleUI;
    }

}
