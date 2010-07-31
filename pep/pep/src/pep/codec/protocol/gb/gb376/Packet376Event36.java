/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb.gb376;

import java.util.ArrayList;
import java.util.List;
import pep.codec.utils.BcdDataBuffer;
import pep.meter645.Gb645Address;

/**
 *
 * @author luxiaochung
 */
public class Packet376Event36 extends PmPacket376EventBase {
    public class Event376{
        public String meterAddress;
        public byte xiangweiHeTongxunPinzhi;
        public byte meterProtocol;

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append("meter address=").append(meterAddress).append(" ");
            sb.append("protocol=").append(meterProtocol);
            return sb.toString();
        }
    }

    public byte Tongxunduankou;
    public List<Event376> meters = new ArrayList<Event376>();

    @Override
    protected void DecodeEventDetail(BcdDataBuffer eventData, int len) {
        if (eventData.restBytes()<2) return;
        this.Tongxunduankou = (byte) (eventData.getByte() & 0x3F);
        len -= 2;

        while ((eventData.restBytes()>=8) && (len>0)){
            Event376 event = new Event376();
            event.meterAddress = Gb645Address.meterAddressToString(eventData.getBytes(6));
            event.xiangweiHeTongxunPinzhi = (byte) eventData.getByte();
            event.meterProtocol = (byte) eventData.getByte();
            meters.add(event);
            len -= 8;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Comm port=").append(this.Tongxunduankou).append(" ");
        sb.append("meters=(");
        for(Event376 event: this.meters){
            sb.append("(").append(event.toString()).append(")");
        }
        sb.append(")");
        this.eventDetail = sb.toString();
    }
}
