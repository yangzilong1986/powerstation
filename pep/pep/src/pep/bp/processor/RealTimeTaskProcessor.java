/*
 * 实时任务（web交互）处理器
 */
package pep.bp.processor;

import java.lang.*;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.db.RTTaskService;
import pep.bp.db.RTTaskServiceIMP;
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
public class RealTimeTaskProcessor implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(RealTimeTaskProcessor.class);
    private RTTaskService taskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列

    public RealTimeTaskProcessor() {
        taskService = new RTTaskServiceIMP();
        pepCommunicator = new PepGbCommunicator();
        respQueue = pepCommunicator.getRtuRespPacketQueueInstance();

    }

    public void run() {
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
            taskService.insertRecvMsg(packet.sequence, packet.pack.toString());
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }
    }
}
