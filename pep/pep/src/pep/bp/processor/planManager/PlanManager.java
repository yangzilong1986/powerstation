/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.processor.planManager;

import pep.bp.processor.polling.*;
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
public class PlanManager implements Runnable{
    private final static Logger log = LoggerFactory.getLogger(PollingJob.class);
    //任务周期类型
    private final int CIRCLE_UNIT_MINUTE =0;
    private final int CIRCLE_UNIT_HOUR =1;
    private final int CIRCLE_UNIT_DAY =2;
    private final int CIRCLE_UNIT_MONTH =3;

    //日任务启动时间点
    private final int STARTUP_TIME = 23;

    private PepCommunicatorInterface pepCommunicator;
    private Trigger triggerHour;

    public PlanManager(PepCommunicatorInterface pepCommunicator){
        this.pepCommunicator = pepCommunicator;
    }

    @Override
    public void run() {
        try {

            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();

            //小时任务
            JobDetail jobDetailHour = new JobDetail("PlanJobHour", null, PlanJobProxy.class);
            jobDetailHour.getJobDataMap().put("PlanJob",new PlanJob(pepCommunicator,CIRCLE_UNIT_HOUR));
            //triggerHour = TriggerUtils.makeHourlyTrigger(); // 每一个小时触发一次
            triggerHour = TriggerUtils.makeMinutelyTrigger(3);
            triggerHour.setStartTime(TriggerUtils.getEvenMinuteDate(new Date())); //从下一个分钟开始
            triggerHour.setName("triggerHour_Plan");
            sched.scheduleJob(jobDetailHour, triggerHour);

        } catch (SchedulerException ex) {
            java.util.logging.Logger.getLogger(PlanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
