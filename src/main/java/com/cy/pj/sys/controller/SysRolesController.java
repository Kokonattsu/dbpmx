package com.cy.pj.sys.controller;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.sys.pojo.SysRoles;
import com.cy.pj.sys.service.SysRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysRolesController {

    @Autowired
    private SysRolesService sysRolesService;

    //查询所有角色
    @GetMapping("role/doFindPageObjects")
    public JsonResult doFindPageObjects(String name, Integer pageCurrent){
        return new JsonResult(
                sysRolesService.findPageObjects(name,pageCurrent)
        );
    }

    //根据id删除角色和角色关系
    @PostMapping("role/doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        return new JsonResult(
                "成功删除"+
                sysRolesService.deleteObjectByRoleId(id)
                +"条数据"
        );
    }

    //添加角色
    @PostMapping("role/doSaveObject")
    public JsonResult doSaveObject(SysRoles entity,Integer[] menuIds){
        return new JsonResult(
                "添加了"+
                sysRolesService.saveObject(entity, menuIds)
                +"条数据"
        );
    }

    //根据id查询角色
    @GetMapping("role/doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(sysRolesService.findObjectById(id));
    }

    //修改角色
    @PostMapping("role/doUpdateObject")
    public JsonResult doUpdateObject(SysRoles entity,Integer[] menuIds){
        return new JsonResult(
                "成功修改"+
                      sysRolesService.updateObject(entity, menuIds)
                        +"条数据"
        );
    }

    @GetMapping("role/doFindRoles")
    public JsonResult doFindRoles(){
        return new JsonResult(
                sysRolesService.findRoles()
        );
    }
}
