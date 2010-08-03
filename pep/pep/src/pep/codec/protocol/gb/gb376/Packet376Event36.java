/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.protocol.gb.gb376;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pep.codec.utils.BcdDataBuffer;
import pep.codec.utils.BcdUtils;
import pep.meter645.Gb645Address;

/**
 *
 * @author luxiaochung
 */
public class Packet376Event36 extends PmPacket376EventBase {

    public class Meter {

        public String meterAddress;
        public boolean isClosed;
        public boolean isLocked;
        public String xiangwei;
        public byte status;
        public Date eventTime;
        public int actValue;

        public String statusString() {
            switch (this.status) {
                case 0:
                    return "漏电跳闸";
                case 1:
                    return "突变跳闸";
                case 2:
                    return "特波跳闸";
                case 3:
                    return "过载跳闸";
                case 4:
                    return "过压跳闸";
                case 5:
                    return "欠压跳闸";
                case 6:
                    return "短路跳闸";
                case 7:
                    return "手动跳闸";
                case 8:
                    return "停电跳闸";
                case 9:
                    return "互感器故障跳闸";
                case 10:
                    return "远程跳闸";
                case 11:
                    return "其它原因跳闸";
                case 12:
                    return "合闸过程中";
                case 13:
                    return "合闸失败";
                default:
                    return "未知";
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("meter address=").append(meterAddress).append(" ");
            if (this.isClosed) {
                sb.append("合闸状态 ");
            } else {
                sb.append("分闸状态 ");
            }
            if (this.isLocked) {
                sb.append("锁死状态 ");
            } else {
                sb.append("未锁死状态 ");
            }
            sb.append("相位").append(xiangwei).append(" ");
            sb.append(this.statusString());
            sb.append("发生时间").append(BcdUtils.dateToString(eventTime, "YY-MM-DD hh:mm:ss"));
            return sb.toString();
        }
    }
    
    public byte Tongxunduankou;
    public List<Meter> meters = new ArrayList<Meter>();

    @Override
    protected void DecodeEventDetail(BcdDataBuffer eventData, int len) {
        if (eventData.restBytes() < 2) {
            return;
        }
        this.Tongxunduankou = (byte) (eventData.getByte() & 0x3F);
        int count = eventData.getByte();
        len -= 2;

        while ((eventData.restBytes() >= 16) && (len > 0)) {
            try {
                Meter event = new Meter();
                event.meterAddress = Gb645Address.meterAddressToString(eventData.getBytes(6));
                byte s = (byte) eventData.getByte();
                event.isClosed = ((s & 0x80) == 0x80);
                event.isLocked = ((s & 0x40) == 0x40);
                switch ((s & 0x30) >> 4) {
                    case 0:
                        event.xiangwei = "无效";
                        break;
                    case 1:
                        event.xiangwei = "A相";
                        break;
                    case 2:
                        event.xiangwei = "B相";
                        break;
                    case 3:
                        event.xiangwei = "C相";
                        break;
                }
                event.status = (byte) (s & 0x0f);

                try {
                    event.actValue = (int) eventData.getBcdInt(2);
                } catch (Exception ex) {
                    event.actValue = 0;
                }

                event.eventTime = eventData.getDate("SSMIHHWWDDMMYY");

                meters.add(event);
            } finally {
                len -= 16;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Comm port=").append(this.Tongxunduankou).append(" ");
        sb.append("meters=(");
        for (Meter event : this.meters) {
            sb.append("(").append(event.toString()).append(")");
        }
        sb.append(")");
        this.eventDetail = sb.toString();
    }
}
