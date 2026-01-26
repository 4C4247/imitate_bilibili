package com.example.bilibili.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息表
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 发送者id
     */
    private Long senderId;

    /**
     * 接收者id
     */
    private Long receiverId;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态：0-未读，1-已读
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

}
