/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb.gb376;

import pep.codec.utils.BcdDataBuffer;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class Packet376EventNormal extends PmPacket376EventBase{

    @Override
    protected void DecodeEventDetail(BcdDataBuffer eventData, int len) {
        switch(erc)
        {
            case 4:{  //开关量状态变位
                this.eventDetail = BcdUtils.bytesToBitSetString(eventData.getBytes(len));
                break;
            }
            case 14:{//停上电事件
                this.eventDetail = BcdUtils.binArrayToString(eventData.getBytes(len));
                break;
            }
        }
        
    }
}
