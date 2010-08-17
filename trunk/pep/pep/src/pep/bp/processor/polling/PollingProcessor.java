/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.processor.polling;

import java.util.Date;
import java.util.logging.Level;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.mina.common.PepCommunicatorInterface;

/**
 *
 * @author Thinkpad
 */
public class PollingProcessor implements Runnable{
    private final static Logger log = LoggerFactory.getLogger(PollingJob.class);
    //任务周期类型
    private final int CIRCLE_UNIT_MINUTE =0;
    private final int CIRCLE_UNIT_HOUR =1;
    private final int CIRCLE_UNIT_DAY =2;
    private final int CIRCLE_UNIT_MONTH =3;

    //日任务启动时间点
    private final int STARTUP_TIME = 23;

    private PepCommunicatorInterface pepCommunicator;
    private Trigger triggerHour,triggerDay;

    public PollingProcessor(PepCommunicatorInterface pepCommunicator){
        this.pepCommunicator = pepCommunicator;
    }

    @Override
    public void run() {
        try {

            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();

            //小时任务
            JobDetail jobDetailHour = new JobDetail("PollingJobHour", null, PollingJobProxy.class);
            jobDetailHour.getJobDataMap().put("PollingJob",new PollingJob(pepCommunicator,CIRCLE_UNIT_HOUR));
            //triggerHour = TriggerUtils.makeHourlyTrigger(); // 每一个小时触发一次
            triggerHour = TriggerUtils.makeMinutelyTrigger(3);
            triggerHour.setStartTime(TriggerUtils.getEvenMinuteDate(new Date())); //从下一个分钟开始
            triggerHour.setName("triggerHour");
//            JobDetail jobDetailDay = new JobDetail("PollingJobDay", null, PollingJob.class);
//            jobDetailDay.getJobDataMap().put("circleUnit", CIRCLE_UNIT_DAY);
//            triggerDay = TriggerUtils.makeDailyTrigger(STARTUP_TIME, 0); // 每天23：00
//            triggerDay.setStartTime(TriggerUtils.getEvenSecondDate(new Date())); //从下一个秒开始
//            triggerDay.setName("triggerDay");
            sched.scheduleJob(jobDetailHour, triggerHour);

            //sched.scheduleJob(jobDetailDay, triggerDay);
        } catch (SchedulerException ex) {
            java.util.logging.Logger.getLogger(PollingProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
