package com.example.bilibili.service;

import com.example.bilibili.entity.Message;
import com.example.bilibili.utils.ResponseUtil;

import java.util.List;

/**
 * 消息服务接口
 */
public interface MessageService {

    /**
     * 发送消息
     * @param message 消息信息
     * @return 发送结果
     */
    ResponseUtil<Message> sendMessage(Message message);

    /**
     * 根据接收者ID查询消息列表
     * @param receiverId 接收者ID
     * @param page 页码
     * @param size 每页大小
     * @return 消息列表
     */
    ResponseUtil<List<Message>> getMessagesByReceiverId(Long receiverId, Integer page, Integer size);

    /**
     * 根据发送者ID查询消息列表
     * @param senderId 发送者ID
     * @param page 页码
     * @param size 每页大小
     * @return 消息列表
     */
    ResponseUtil<List<Message>> getMessagesBySenderId(Long senderId, Integer page, Integer size);

    /**
     * 更新消息状态为已读
     * @param receiverId 接收者ID
     * @return 更新结果
     */
    ResponseUtil<Void> updateMessageStatus(Long receiverId);

}
