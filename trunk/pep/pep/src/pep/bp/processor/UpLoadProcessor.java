/*
 * 主动上报报文处理器
 */

package pep.bp.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.db.DataService;
import pep.bp.db.RTTaskService;
import pep.bp.model.Dto;
import pep.bp.utils.Converter;
import pep.codec.protocol.gb.PmPacket;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuAutoUploadPacketQueue;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class UpLoadProcessor extends BaseProcessor{
    private final static Logger log = LoggerFactory.getLogger(ResponseDealer.class);
    private DataService dataService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuAutoUploadPacketQueue upLoadQueue;//主动上报报文队列
    private Converter converter;
    private Dto data;

    public  UpLoadProcessor(PepCommunicatorInterface pepCommunicator){
        super();
        dataService = (DataService) cxt.getBean(SystemConst.REALTIMETASK_BEAN);
        upLoadQueue = pepCommunicator.getRtuAutoUploadPacketQueueInstance();
        this.pepCommunicator = pepCommunicator;
        this.converter = new Converter();
        this.data = new Dto();
    }
    @Override
    public void run(){
        while (true) {
            try {
                PmPacket376 packet = (PmPacket376)upLoadQueue.PollPacket();
                this.converter.decodeDataDB(packet, data);
                dataService.insertRecvData(data);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
