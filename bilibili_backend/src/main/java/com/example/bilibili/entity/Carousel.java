package com.example.bilibili.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图表
 */
@Data
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String image;

    /**
     * 链接
     */
    private String url;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
