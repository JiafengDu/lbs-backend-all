package com.tarena.lbs.pojo.message.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketMessage implements Serializable {

    /**
     * 推送给谁
     */
    Integer userId;
    /**1:消息通知中的消息*/
    Integer msgType;
    /**
     * 消息描述
     */
    String msg;
    /**
     * 消息数据
     */
    Object data;

}
