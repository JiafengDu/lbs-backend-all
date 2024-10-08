package com.tarena.lbs.pojo.message.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TriggerMsgEvent {
    private Integer userId;//发起行为的人
    private String username;
    private Integer ariticleUserId;//文章发布者
    private String articleUsername;
    private String articleTitle;
    private Integer userAction;//用户行为
    private String actionName;
    private Integer articleId;
    private Integer activityId;
    private Integer source;
}
