package com.example.bilibili.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分类表
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 父分类id，null表示主分类
     */
    private Long parentId;

    /**
     * 分类类型：0-主分类，1-子分类
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 子分类列表
     */
    private List<Category> subcategories;

}
