/*
 * 实时任务（web交互）处理器
 */
package pep.bp.processor;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.bp.db.RTTaskService;
import pep.bp.model.RealTimeTask;
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuRespPacketQueue;
import pep.mina.common.SequencedPmPacket;
import pep.mina.protocolcodec.gb.PepGbCommunicator;

/**
 *
 * @author Thinkpad
 */
public class RealTimeTaskProcessor extends BaseProcessor {

    private final static Logger log = LoggerFactory.getLogger(RealTimeTaskProcessor.class);
    private RTTaskService taskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列


    public RealTimeTaskProcessor( PepCommunicatorInterface pepCommunicator) {
        super();
        taskService = (RTTaskService) cxt.getBean("taskService");
        respQueue = pepCommunicator.getRtuRespPacketQueueInstance();
        this.pepCommunicator = pepCommunicator;
    }

    public void run() {
        while (true) {
            //做发送操作
            List<RealTimeTask> tasks = taskService.getTasks();
            for (RealTimeTask task : tasks) {
                PmPacket packet = new PmPacket376();
                packet.setValue(task.getSendmsg().getBytes(), 0);
                pepCommunicator.SendPacket(task.getSequencecode(), packet);
            }

            //检查返回
            try {
                SequencedPmPacket packet = respQueue.PollPacket();
                taskService.insertRecvMsg(packet.sequence,packet.pack.getAddress().toString(), packet.pack.toString());
            } catch (InterruptedException ex) {
                log.error(ex.getMessage());
            }
        }

    }


}
