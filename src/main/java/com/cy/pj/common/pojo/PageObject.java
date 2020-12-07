package com.cy.pj.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PageObject<T> implements Serializable {
    private static final long serialVersionUID = 7390339305403444365L;
    /**当前页码值*/
    private Integer pageCurrent;
    /**页面大小/行数*/
    private Integer pageSize;
    /**总行数(通过查询获得)*/
    private Integer rowCount;
    /**总页数(通过计算获得)*/
    private Integer pageCount;
    /**当前页数据*/
    private List<T> records;

    public PageObject(Integer pageCurrent,
                      Integer pageSize,
                      Integer rowCount,
                      List<T> records) {
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.records = records;
        //总页数：总行数/页数，再取余如果有余再加一
        this.pageCount=rowCount/pageSize;
        if(this.rowCount%this.pageSize!=0) {
            this.pageCount++;
        }
    }
}
