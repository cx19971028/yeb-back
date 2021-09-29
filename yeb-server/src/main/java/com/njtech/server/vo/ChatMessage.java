package com.njtech.server.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author chenxin
 * @date 2021/9/29 11:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
// 开启链式编程
@Accessors(chain = true)
public class ChatMessage {

    private String from;
    private String to;
    private String content;
    private LocalDateTime dateTime;
    private String fromNickName;
}
