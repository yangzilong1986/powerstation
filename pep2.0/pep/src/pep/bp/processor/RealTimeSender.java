/*
 * 实时任务（web交互）发送器
 */
package pep.bp.processor;

import java.util.List;
import java.util.logging.Level;
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

    public RealTimeSender(PepCommunicatorInterface pepCommunicator) {
        super();
        taskService = (RTTaskService) cxt.getBean(SystemConst.REALTIMETASK_BEAN);
        respQueue = pepCommunicator.getRtuRespPacketQueueInstance();
        this.pepCommunicator = pepCommunicator;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(RealTimeSender.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<RealTimeTaskDAO> tasks = taskService.getTasks();
            //log.info("读到任务："+tasks.size());
            if (null != tasks) {
                for (RealTimeTaskDAO task : tasks) {
                    PmPacket packet = new PmPacket376();
                    packet.setValue(BcdUtils.stringToByteArray(task.getSendmsg()), 0);
                //    log.info("开始往下发队列中发送报文："+task.getSendmsg());
                    pepCommunicator.SendPacket(task.getTaskId(), packet,0);
                    log.info("往下发队列中发送("+packet.getAddress().getRtua()+")报文："+task.getSendmsg());
                }
            }
            else
              log.info("tasks为NULL");  
        }

    }
}
