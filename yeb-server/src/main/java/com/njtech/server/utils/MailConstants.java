package com.njtech.server.utils;

import io.swagger.models.auth.In;

/**
 * 消息常量
 *
 * @author chenxin
 * @date 2021/9/27 9:38
 */
public class MailConstants {
    /**
     * 消息投递中
     */
    public static final Integer DELIVERING = 0;

    /**
     * 消息投递成功
     */
    public static final Integer SUCCESS = 1;

    /**
     * 消息投递失败
     */
    public static final Integer FAILURE = 2;

    /**
     * 最大重试次数
     */
    public static final Integer MAX_TRY_COUNT = 3;

    /**
     * 消息超时时间
     */
    public static final Integer MSG_TIMEOUT = 1;

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "mail.queue";

    /**
     * 交换机
     */
    public static final String EXCHANGE_NAME = "mail.change";

    /**
     * routineKey
     */
    public static final String ROUTINE_KEY_NAME = "mail.routine.key";
}
