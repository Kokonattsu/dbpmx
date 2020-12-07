package com.cy.pj.sys.controller;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log/")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    //根据用户名查询数据并分页
    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(
            @RequestParam(required = false) String username,
            Integer pageCurrent,
            Integer pageSize){
        PageObject<SysLog> pageObject =
                sysLogService.findPageObject(username, pageCurrent,pageSize);
        return new JsonResult(pageObject);
    }

    //根据id删除一或多条数据
    @RequestMapping("doDeleteLogs")
    public JsonResult doDeleteLogs(Integer... id){
        Integer deleteLogs = sysLogService.deleteLogs(id);
        JsonResult json=new JsonResult();
        json.setData("删除成功,有"+deleteLogs+"条数据受影响");
        return  json;
    }
}
