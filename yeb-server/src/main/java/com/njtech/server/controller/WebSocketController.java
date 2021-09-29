package com.njtech.server.controller;

import com.njtech.server.pojo.Admin;
import com.njtech.server.vo.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * @author chenxin
 * @date 2021/9/29 11:42
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMessage chatMessage){
        Admin admin = (Admin)authentication.getPrincipal();
        chatMessage.setFrom(admin.getUsername());
        chatMessage.setDateTime(LocalDateTime.now());
        chatMessage.setFromNickName(admin.getName());
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getTo(),"/queue/chat",chatMessage);
    }
}
