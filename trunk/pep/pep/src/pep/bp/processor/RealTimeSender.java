/*
 * 实时任务（web交互）发送器
 */
package pep.bp.processor;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.bp.db.RTTaskService;
import pep.bp.model.RealTimeTaskDAO;
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.utils.BcdUtils;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuRespPacketQueue;

import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class RealTimeSender extends BaseProcessor {

    private final static Logger log = LoggerFactory.getLogger(RealTimeSender.class);
    private RTTaskService taskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列


    public RealTimeSender( PepCommunicatorInterface pepCommunicator) {
        super();
        taskService = (RTTaskService) cxt.getBean(SystemConst.REALTIMETASK_BEAN);
        respQueue = pepCommunicator.getRtuRespPacketQueueInstance();
        this.pepCommunicator = pepCommunicator;
    }

    @Override
    public void run() {
        while (true) {
            //做发送操作
            List<RealTimeTaskDAO> tasks = taskService.getTasks();
            for (RealTimeTaskDAO task : tasks) {
                PmPacket packet = new PmPacket376();
                packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()), 0);
                pepCommunicator.SendPacket(task.getSequencecode(), packet);
            }
        }

    }


}
