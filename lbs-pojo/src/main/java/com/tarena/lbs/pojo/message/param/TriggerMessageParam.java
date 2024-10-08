package com.tarena.lbs.pojo.message.param;

import java.io.Serializable;

/**
 * 用户注册,关注作者等操作后，发给消息模块的数据
 * 子类有RegisterTriggerMessageParam,等
 */

public abstract class TriggerMessageParam implements Serializable {
    /**
     * 用户编号,消息模块通过websocket往用户编号推送消息
     */
    private String userId;

    /**
     * 用户动作 如：注册，关注
     */
    private String userAction;

}
