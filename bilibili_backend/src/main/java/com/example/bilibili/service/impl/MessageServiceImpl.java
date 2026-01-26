package com.example.bilibili.service.impl;

import com.example.bilibili.entity.Message;
import com.example.bilibili.service.MessageService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 消息服务实现类
 */
@Service
public class MessageServiceImpl implements MessageService {

    // 模拟消息数据存储
    private static final ConcurrentHashMap<Long, Message> messages = new ConcurrentHashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1);

    @Override
    public ResponseUtil<Message> sendMessage(Message message) {
        // 设置默认值
        message.setId(nextId.getAndIncrement());
        message.setStatus(0); // 默认未读

        // 保存消息
        messages.put(message.getId(), message);

        return ResponseUtil.success("发送成功", message);
    }

    @Override
    public ResponseUtil<List<Message>> getMessagesByReceiverId(Long receiverId, Integer page, Integer size) {
        List<Message> receiverMessages = new ArrayList<>();
        for (Message message : messages.values()) {
            if (message.getReceiverId().equals(receiverId)) {
                receiverMessages.add(message);
            }
        }
        return ResponseUtil.success(receiverMessages);
    }

    @Override
    public ResponseUtil<List<Message>> getMessagesBySenderId(Long senderId, Integer page, Integer size) {
        List<Message> senderMessages = new ArrayList<>();
        for (Message message : messages.values()) {
            if (message.getSenderId().equals(senderId)) {
                senderMessages.add(message);
            }
        }
        return ResponseUtil.success(senderMessages);
    }

    @Override
    public ResponseUtil<Void> updateMessageStatus(Long receiverId) {
        for (Message message : messages.values()) {
            if (message.getReceiverId().equals(receiverId)) {
                message.setStatus(1); // 标记为已读
            }
        }
        return ResponseUtil.success(null);
    }

}
