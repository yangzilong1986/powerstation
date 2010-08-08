/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.processor;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.bp.db.DataService;
import pep.bp.model.Dto;
import pep.bp.utils.Converter;
import pep.codec.protocol.gb.PmPacketData;
import pep.codec.protocol.gb.gb376.Packet376Event36;
import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.codec.protocol.gb.gb376.PmPacket376DA;
import pep.codec.protocol.gb.gb376.PmPacket376DT;
import pep.codec.protocol.gb.gb376.PmPacket376EventBase;
import pep.codec.protocol.gb.gb376.PmPacket376EventDecoder;
import pep.codec.utils.BcdDataBuffer;
import pep.codec.utils.BcdUtils;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class UpLoadProcessorTest {
    private DataService dataService;
    private ApplicationContext cxt;
    private Converter converter;

    public UpLoadProcessorTest() {
        super();
        cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
        dataService = (DataService) cxt.getBean(SystemConst.DATASERVICE_BEAN);
        converter = (Converter) cxt.getBean("converter");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    //@Test
    public void testRun() {
        //String Msg = "68, A6, 00, A6, 00, 68, C4, 12, 96, 56, 34, 00, 0E, 71, 00, 00, 01, 00, 03, 00, 00, 01, 24, 17, 18, 14, 02, 08, 10, 00, 01, 01, 00, 00, 00, 00, 00, 88, FF, FF, 54, 17, 14, 01, 02, 08, 10, 1D, 16";
        String Msg = "687200720068C412965634000E7000000100010000010E0A401431071031110308108816";

        byte[] msg = BcdUtils.stringToByteArray(Msg);

        PmPacket376 packet = new PmPacket376();
        packet.setValue(msg, 0);
        DecodeEventAndSave(packet);
    }

    @Test
    public void testData1() {
        //String Msg = "68, A6, 00, A6, 00, 68, C4, 12, 96, 56, 34, 00, 0E, 71, 00, 00, 01, 00, 03, 00, 00, 01, 24, 17, 18, 14, 02, 08, 10, 00, 01, 01, 00, 00, 00, 00, 00, 88, FF, FF, 54, 17, 14, 01, 02, 08, 10, 1D, 16";
        String Msg = "683E013E01689812965634040C630101010330140808104711011637001737001537000001000000000000000000009909990999099909002290219021001700901600901600EFEFEFEFEFEFEFEFEFEFEFEFEFEFEF5816";

        byte[] msg = BcdUtils.stringToByteArray(Msg);

        PmPacket376 packet = new PmPacket376();
        packet.setValue(msg, 0);
        decodeAndSaveClassOneData(packet);
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
                if (event.GetEventCode() == 36) {
                    Packet376Event36 event36 = (Packet376Event36) event;
                    saveLoubaoEvent(rtua, event36);
                } else {
                    saveEvent(rtua, dt.getFn(), da.getPn(), event);
                }
            }
        }
    }

    private void saveLoubaoEvent(String rtua, Packet376Event36 event36) {
        for (Packet376Event36.Meter meter : event36.meters) {
            this.dataService.insertLBEvent(rtua,event36);
        }
    }

    private void saveEvent(String rtua, int fn, int pn, PmPacket376EventBase event){
        this.dataService.insertEvent(rtua,event);
    }

    private void decodeAndSaveClassOneData(PmPacket376 packet) {
        Dto dto = new Dto(packet.getAddress().getRtua(),packet.getAfn());
        this.converter.decodeDataDB(packet,dto);
        dataService.insertRecvData(dto);
    }


}