package com.cy.pj.sys.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysRoleMenus implements Serializable {

    private static final long serialVersionUID = -7172785211790409772L;

    private Integer id;
    private String name;
    private String note;
    private List<Integer> menuIds;
}
