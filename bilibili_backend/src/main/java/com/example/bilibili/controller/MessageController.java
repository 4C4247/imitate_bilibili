package com.example.bilibili.controller;

import com.example.bilibili.entity.Message;
import com.example.bilibili.service.MessageService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息控制器
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送消息
     * @param message 消息信息
     * @return 发送结果
     */
    @PostMapping
    public ResponseUtil<Message> sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    /**
     * 根据接收者ID查询消息列表
     * @param receiverId 接收者ID
     * @param page 页码
     * @param size 每页大小
     * @return 消息列表
     */
    @GetMapping("/receiver/{receiverId}")
    public ResponseUtil<List<Message>> getMessagesByReceiverId(@PathVariable Long receiverId, 
                                                             @RequestParam(defaultValue = "1") Integer page, 
                                                             @RequestParam(defaultValue = "10") Integer size) {
        return messageService.getMessagesByReceiverId(receiverId, page, size);
    }

    /**
     * 根据发送者ID查询消息列表
     * @param senderId 发送者ID
     * @param page 页码
     * @param size 每页大小
     * @return 消息列表
     */
    @GetMapping("/sender/{senderId}")
    public ResponseUtil<List<Message>> getMessagesBySenderId(@PathVariable Long senderId, 
                                                           @RequestParam(defaultValue = "1") Integer page, 
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return messageService.getMessagesBySenderId(senderId, page, size);
    }

    /**
     * 更新消息状态为已读
     * @param receiverId 接收者ID
     * @return 更新结果
     */
    @PutMapping("/status/{receiverId}")
    public ResponseUtil<Void> updateMessageStatus(@PathVariable Long receiverId) {
        return messageService.updateMessageStatus(receiverId);
    }

}
