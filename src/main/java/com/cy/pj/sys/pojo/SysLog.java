package com.cy.pj.sys.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**用户名*/
    private String username;
    /**操作名*/
    private String operation;
    /**全类名+方法名*/
    private String method;
    /**参数类型*/
    private String params;
    /**运行时长*/
    private Long time;
    /**ip地址*/
    private String ip;
    /**创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
}
