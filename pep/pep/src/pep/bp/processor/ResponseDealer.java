/*
 * 主动下发返回处理器
 */
package pep.bp.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.db.RTTaskService;
import pep.codec.utils.BcdUtils;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuRespPacketQueue;
import pep.mina.common.SequencedPmPacket;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class ResponseDealer extends BaseProcessor {

    private final static Logger log = LoggerFactory.getLogger(ResponseDealer.class);
    private RTTaskService taskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列


    public ResponseDealer( PepCommunicatorInterface pepCommunicator) {
        super();
        taskService = (RTTaskService) cxt.getBean(SystemConst.REALTIMETASK_BEAN);
        respQueue = pepCommunicator.getRtuRespPacketQueueInstance();
        this.pepCommunicator = pepCommunicator;
    }

    @Override
    public void run() {
        while (true) {
            //检查返回
            try {
                SequencedPmPacket packet = respQueue.PollPacket();
                if(packet.sequence == -1)//主动轮召任务返回处理
                    ;
                else  //实时召测任务返回处理
                    taskService.insertRecvMsg(packet.sequence,packet.pack.getAddress().getRtua(), BcdUtils.binArrayToString(packet.pack.getValue()));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

    }


}
