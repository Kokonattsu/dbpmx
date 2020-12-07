package com.cy.pj.common.pojo;

import lombok.Data;

import java.io.Serializable;
/**封装响应到客户端的数据和状态*/
@Data
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 4969235117782032972L;

    /**状态码：1表示ok，0表示error */
    private int state=1;

    /**状态信息*/
    private String message="ok";

    /**正常返回数据*/
    private Object data;

    public JsonResult() {
    }

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public JsonResult(Throwable throwable) {
        this.state=0;
        this.message = throwable.getMessage();
    }

}
