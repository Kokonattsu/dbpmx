package com.cy.pj.sys.dao;

import com.cy.pj.sys.pojo.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLogDao {

    /**查询数据的条数(根据用户) @Param表示执行SQL传入的参数名*/
    Integer getRowCount(@Param("username") String username);

    /**
     *根据用户名查询日志信息，并分页
     * @param username  用户名
     * @param startIndex 起始位置
     * @param pageSize  每页展现多少条信息
     * @return
     */
    List<SysLog> findPageObjects(
            @Param("username")String username,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize);

    Integer deleteObjects(Integer... ids);

    int insertObject(SysLog entity);
}
