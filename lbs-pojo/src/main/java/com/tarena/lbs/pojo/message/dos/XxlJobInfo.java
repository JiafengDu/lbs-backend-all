package com.tarena.lbs.pojo.message.dos;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName xxl_job_info
 */
@Data
public class XxlJobInfo implements Serializable {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 执行器主键ID
     */
    private Integer jobGroup;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 作者
     */
    private String author;

    /**
     * 报警邮件
     */
    private String alarmEmail;

    /**
     * 调度类型
     */
    private String scheduleType;

    /**
     * 调度配置，值含义取决于调度类型
     */
    private String scheduleConf;

    /**
     * 调度过期策略
     */
    private String misfireStrategy;

    /**
     * 执行器路由策略
     */
    private String executorRouteStrategy;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    private String executorParam;

    /**
     * 阻塞处理策略
     */
    private String executorBlockStrategy;

    /**
     * 任务执行超时时间，单位秒
     */
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * GLUE类型
     */
    private String glueType;

    /**
     * GLUE源代码
     */
    private String glueSource;

    /**
     * GLUE备注
     */
    private String glueRemark;

    /**
     * GLUE更新时间
     */
    private Date glueUpdatetime;

    /**
     * 子任务ID，多个逗号分隔
     */
    private String childJobid;

    /**
     * 调度状态：0-停止，1-运行
     */
    private Integer triggerStatus;

    /**
     * 上次调度时间
     */
    private Long triggerLastTime;

    /**
     * 下次调度时间
     */
    private Long triggerNextTime;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        XxlJobInfo other = (XxlJobInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getJobGroup() == null ? other.getJobGroup() == null : this.getJobGroup().equals(other.getJobGroup()))
            && (this.getJobDesc() == null ? other.getJobDesc() == null : this.getJobDesc().equals(other.getJobDesc()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getAlarmEmail() == null ? other.getAlarmEmail() == null : this.getAlarmEmail().equals(other.getAlarmEmail()))
            && (this.getScheduleType() == null ? other.getScheduleType() == null : this.getScheduleType().equals(other.getScheduleType()))
            && (this.getScheduleConf() == null ? other.getScheduleConf() == null : this.getScheduleConf().equals(other.getScheduleConf()))
            && (this.getMisfireStrategy() == null ? other.getMisfireStrategy() == null : this.getMisfireStrategy().equals(other.getMisfireStrategy()))
            && (this.getExecutorRouteStrategy() == null ? other.getExecutorRouteStrategy() == null : this.getExecutorRouteStrategy().equals(other.getExecutorRouteStrategy()))
            && (this.getExecutorHandler() == null ? other.getExecutorHandler() == null : this.getExecutorHandler().equals(other.getExecutorHandler()))
            && (this.getExecutorParam() == null ? other.getExecutorParam() == null : this.getExecutorParam().equals(other.getExecutorParam()))
            && (this.getExecutorBlockStrategy() == null ? other.getExecutorBlockStrategy() == null : this.getExecutorBlockStrategy().equals(other.getExecutorBlockStrategy()))
            && (this.getExecutorTimeout() == null ? other.getExecutorTimeout() == null : this.getExecutorTimeout().equals(other.getExecutorTimeout()))
            && (this.getExecutorFailRetryCount() == null ? other.getExecutorFailRetryCount() == null : this.getExecutorFailRetryCount().equals(other.getExecutorFailRetryCount()))
            && (this.getGlueType() == null ? other.getGlueType() == null : this.getGlueType().equals(other.getGlueType()))
            && (this.getGlueSource() == null ? other.getGlueSource() == null : this.getGlueSource().equals(other.getGlueSource()))
            && (this.getGlueRemark() == null ? other.getGlueRemark() == null : this.getGlueRemark().equals(other.getGlueRemark()))
            && (this.getGlueUpdatetime() == null ? other.getGlueUpdatetime() == null : this.getGlueUpdatetime().equals(other.getGlueUpdatetime()))
            && (this.getChildJobid() == null ? other.getChildJobid() == null : this.getChildJobid().equals(other.getChildJobid()))
            && (this.getTriggerStatus() == null ? other.getTriggerStatus() == null : this.getTriggerStatus().equals(other.getTriggerStatus()))
            && (this.getTriggerLastTime() == null ? other.getTriggerLastTime() == null : this.getTriggerLastTime().equals(other.getTriggerLastTime()))
            && (this.getTriggerNextTime() == null ? other.getTriggerNextTime() == null : this.getTriggerNextTime().equals(other.getTriggerNextTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getJobGroup() == null) ? 0 : getJobGroup().hashCode());
        result = prime * result + ((getJobDesc() == null) ? 0 : getJobDesc().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getAlarmEmail() == null) ? 0 : getAlarmEmail().hashCode());
        result = prime * result + ((getScheduleType() == null) ? 0 : getScheduleType().hashCode());
        result = prime * result + ((getScheduleConf() == null) ? 0 : getScheduleConf().hashCode());
        result = prime * result + ((getMisfireStrategy() == null) ? 0 : getMisfireStrategy().hashCode());
        result = prime * result + ((getExecutorRouteStrategy() == null) ? 0 : getExecutorRouteStrategy().hashCode());
        result = prime * result + ((getExecutorHandler() == null) ? 0 : getExecutorHandler().hashCode());
        result = prime * result + ((getExecutorParam() == null) ? 0 : getExecutorParam().hashCode());
        result = prime * result + ((getExecutorBlockStrategy() == null) ? 0 : getExecutorBlockStrategy().hashCode());
        result = prime * result + ((getExecutorTimeout() == null) ? 0 : getExecutorTimeout().hashCode());
        result = prime * result + ((getExecutorFailRetryCount() == null) ? 0 : getExecutorFailRetryCount().hashCode());
        result = prime * result + ((getGlueType() == null) ? 0 : getGlueType().hashCode());
        result = prime * result + ((getGlueSource() == null) ? 0 : getGlueSource().hashCode());
        result = prime * result + ((getGlueRemark() == null) ? 0 : getGlueRemark().hashCode());
        result = prime * result + ((getGlueUpdatetime() == null) ? 0 : getGlueUpdatetime().hashCode());
        result = prime * result + ((getChildJobid() == null) ? 0 : getChildJobid().hashCode());
        result = prime * result + ((getTriggerStatus() == null) ? 0 : getTriggerStatus().hashCode());
        result = prime * result + ((getTriggerLastTime() == null) ? 0 : getTriggerLastTime().hashCode());
        result = prime * result + ((getTriggerNextTime() == null) ? 0 : getTriggerNextTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", jobGroup=").append(jobGroup);
        sb.append(", jobDesc=").append(jobDesc);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", author=").append(author);
        sb.append(", alarmEmail=").append(alarmEmail);
        sb.append(", scheduleType=").append(scheduleType);
        sb.append(", scheduleConf=").append(scheduleConf);
        sb.append(", misfireStrategy=").append(misfireStrategy);
        sb.append(", executorRouteStrategy=").append(executorRouteStrategy);
        sb.append(", executorHandler=").append(executorHandler);
        sb.append(", executorParam=").append(executorParam);
        sb.append(", executorBlockStrategy=").append(executorBlockStrategy);
        sb.append(", executorTimeout=").append(executorTimeout);
        sb.append(", executorFailRetryCount=").append(executorFailRetryCount);
        sb.append(", glueType=").append(glueType);
        sb.append(", glueSource=").append(glueSource);
        sb.append(", glueRemark=").append(glueRemark);
        sb.append(", glueUpdatetime=").append(glueUpdatetime);
        sb.append(", childJobid=").append(childJobid);
        sb.append(", triggerStatus=").append(triggerStatus);
        sb.append(", triggerLastTime=").append(triggerLastTime);
        sb.append(", triggerNextTime=").append(triggerNextTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}