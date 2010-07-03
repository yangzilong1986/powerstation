/*
 * 主站轮召处理器
 */

package pep.bp.processor;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pep.bp.db.RTTaskService;
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuRespPacketQueue;
import pep.mina.common.SequencedPmPacket;
import pep.mina.protocolcodec.gb.PepGbCommunicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.bp.db.TaskService;
import pep.bp.model.TermTaskDAO;
import pep.common.timer.ITimerFunctor;
import pep.common.timer.TimerData;
import pep.common.timer.TimerScheduler;
/**
 *
 * @author Thinkpad
 */
public class PollingJob implements Job {
    private final static Logger log = LoggerFactory.getLogger(PollingJob.class);
    private TaskService taskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列
    private ApplicationContext cxt;
    public  PollingJob(int IntervalHour){
        cxt = new ClassPathXmlApplicationContext("beans.xml");
        taskService = (TaskService) cxt.getBean("TaskService");
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        int circleUnit = dataMap.getInt("circleUnit");
        List<TermTaskDAO> TermTaskList = taskService.getPollingTask(circleUnit);
        for(TermTaskDAO task:TermTaskList){

        }

    }

}
