/*
 * 主动上报报文处理器
 */
package pep.bp.processor;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.db.DataService;
import pep.bp.model.Dto;
import pep.bp.utils.Converter;
import pep.bp.utils.decoder.ClassTwoDataDecoder;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.gb376.Packet376Event36;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;
import pep.codec.protocol.gb.gb376.PmPacket376EventBase;
import pep.codec.protocol.gb.gb376.PmPacket376EventDecoder;
import pep.codec.utils.BcdDataBuffer;
import pep.codec.utils.BcdUtils;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuAutoUploadPacketQueue;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class UpLoadProcessor extends BaseProcessor {

    private final static Logger log = LoggerFactory.getLogger(ResponseDealer.class);
    private DataService dataService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuAutoUploadPacketQueue upLoadQueue;//主动上报报文队列
    private Converter converter;

    public UpLoadProcessor(PepCommunicatorInterface pepCommunicator) {
        super();
        dataService = (DataService) cxt.getBean(SystemConst.DATASERVICE_BEAN);
        upLoadQueue = pepCommunicator.getRtuAutoUploadPacketQueueInstance();
        this.pepCommunicator = pepCommunicator;
        this.converter = (Converter) cxt.getBean("converter");
    }

    @Override
    public void run() {
        while (true) {
            try {
                PmPacket376 packet = (PmPacket376) upLoadQueue.PollPacket();
                if (packet.getAfn() == 0x0C) {
                    decodeAndSaveClassOneData(packet);
                    //log.info("对报文： " + BcdUtils.binArrayToString(packet.getValue())+" 做入库处理成功");
                } else if(packet.getAfn() == 0x10){
                    decodeAndSaveClasTransMitData(packet);
                   // log.info("对报文： " + BcdUtils.binArrayToString(packet.getValue())+" 做入库处理成功");
                } else if (packet.getAfn() == 0x0D) {
                    decodeAndSaveClassTwoData(packet);
                   // log.info("对报文： " + BcdUtils.binArrayToString(packet.getValue())+" 做入库处理成功");
                } else if (packet.getAfn() == 0x0E) {
                    DecodeEventAndSave(packet);
                    log.info("对报文： " + BcdUtils.binArrayToString(packet.getValue())+" 做入库处理成功");
                } else {
                    log.error("收到不支持的主动上送报文类" + BcdUtils.binArrayToString(packet.getValue()));
                }
            } catch (InterruptedException ex) {
                break;
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private void decodeAndSaveClassOneData(PmPacket376 packet) {
        Dto dto = new Dto(packet.getAddress().getRtua(), packet.getAfn());
        this.converter.decodeDataDB(packet, dto);
        dataService.insertRecvData(dto);
    }

    private void decodeAndSaveClasTransMitData(PmPacket376 packet) {
        Dto dto = new Dto(packet.getAddress().getRtua(), packet.getAfn());
        this.converter.decodeData_TransMit(packet, dto);
        dataService.insertRecvData(dto);
    }

    private void decodeAndSaveClassTwoData(PmPacket376 packet) {
        Dto classTwoDto = ClassTwoDataDecoder.Decode(packet);
        dataService.insertRecvData(classTwoDto);
    }

    private void DecodeEventAndSave(PmPacket376 packet) {
        String rtua = packet.getAddress().getRtua();
        PmPacketData data = packet.getDataBuffer();
        data.rewind();
        PmPacket376DA da = new PmPacket376DA();
        PmPacket376DT dt = new PmPacket376DT();
        while (data.restBytes() >= 4) {
            data.getDA(da);
            data.getDT(dt);
            List<PmPacket376EventBase> events = PmPacket376EventDecoder.decode(new BcdDataBuffer(data.getRowIoBuffer()));

            for (PmPacket376EventBase event : events) {
                if (event.GetEventCode() == 36) {//漏保事件
                    Packet376Event36 event36 = (Packet376Event36) event;
                    saveLoubaoEvent(rtua, event36);
                } else {
                    saveEvent(rtua, dt.getFn(), da.getPn(), event);
                }
            }
        }
    }

    private void saveLoubaoEvent(String rtua, Packet376Event36 event36) {
        this.dataService.insertLBEvent(rtua, event36);
    }

    private void saveEvent(String rtua, int fn, int pn, PmPacket376EventBase event) {
        this.dataService.insertEvent(rtua, event);
    }
}
