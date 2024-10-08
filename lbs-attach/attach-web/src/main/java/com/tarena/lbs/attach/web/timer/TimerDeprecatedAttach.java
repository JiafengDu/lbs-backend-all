package com.tarena.lbs.attach.web.timer;

import com.tarena.lbs.attach.web.service.AttachService;
import com.tarena.lbs.pojo.attach.dos.Attach;
import com.tarena.lbs.pojo.attach.query.AttachDeprecatedQuery;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TimerDeprecatedAttach {
    @Autowired
    private AttachService attachService;
    @XxlJob("deleteAttach")
    public void deleteAttachDeprecated() {
        log.info("定时任务开始执行");
        //定义删除过期图片 定义过期的含义 30分钟
        AttachDeprecatedQuery query=new AttachDeprecatedQuery();
        query.setExpired(new Date().getTime()-1000*60*30);
        attachService.deleteAttachDeprecated(query);
        log.info("定时任务执行完毕，删除了{}条数据");
    }
}
