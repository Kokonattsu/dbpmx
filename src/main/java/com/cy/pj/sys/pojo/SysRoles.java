package com.cy.pj.sys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
//角色表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoles implements Serializable {

    private static final long serialVersionUID = 720805844420731030L;

    private Integer id;
    private String name;
    private String note;    //角色描述
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;

}
