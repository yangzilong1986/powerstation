/*
 * 主站轮召处理器
 */

package pep.bp.processor;
import pep.bp.db.RTTaskService;
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuRespPacketQueue;
import pep.mina.common.SequencedPmPacket;
import pep.mina.protocolcodec.gb.PepGbCommunicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Thinkpad
 */
public class PollingProcessor extends BaseProcessor {
    private final static Logger log = LoggerFactory.getLogger(PollingProcessor.class);
    private RTTaskService taskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列
    

    public void run() {

    }
}
