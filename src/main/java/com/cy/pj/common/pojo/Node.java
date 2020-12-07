package com.cy.pj.common.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Node implements Serializable {


    private static final long serialVersionUID = -1769062571801285140L;

    private Integer id;

    private String name;

    private Integer parentId;
}
