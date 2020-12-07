package com.cy.pj.sys.controller;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class SysMenuControllrt {

    @Autowired
    private SysMenuService sysMenuService;

    //查询全部的菜单
    @GetMapping("/menu/doFindObjects")
    public JsonResult doFindObjects(){
        return new JsonResult(sysMenuService.findObject());
    }

    //删除菜单
    @RequestMapping("/menu/doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        int i = sysMenuService.deleteObject(id);
        JsonResult jsonresult=new JsonResult();
        jsonresult.setMessage("删除成功，共有"+i+"条记录受影响");
        return jsonresult;
    }

    //查询菜单结构
    @GetMapping("menu/doFindZtreeMenuNodes")
    public JsonResult doFindZtreeMenuNode(){
        return new JsonResult(sysMenuService.findZtreeMenuNodes());
    }

    //添加菜单
    @PostMapping("menu/doSaveObject")
    public JsonResult doSaveObject(SysMenu sysMenu){
        JsonResult jsonresult=new JsonResult();
        Integer saveObject = sysMenuService.saveObject(sysMenu);
        jsonresult.setMessage("添加了"+saveObject+"条数据");
        return jsonresult;
    }

    //修改菜单
    @PostMapping("menu/doUpdateObject")
    public JsonResult doUpdateObject(SysMenu sysMenu){
        return new JsonResult(
                        "修改成功，"
                        +sysMenuService.updateObject(sysMenu)
                        +"条数据受影响");
    }
}
