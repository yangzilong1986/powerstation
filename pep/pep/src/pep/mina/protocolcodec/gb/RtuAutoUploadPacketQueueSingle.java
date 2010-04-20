/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.protocolcodec.gb;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import pep.codec.protocol.gb.PmPacket;

/**
 *
 * @author luxiaochung
 */
public class RtuAutoUploadPacketQueueSingle {
    private static Queue<PmPacket> receiveRtuAutoUploadPacketQueue;  //接收到的报文或不在线/超时错误返回报文

    static{
        receiveRtuAutoUploadPacketQueue = new ConcurrentLinkedQueue<PmPacket>();
    }
    
    private RtuAutoUploadPacketQueueSingle(){
    }

    public static Queue<PmPacket> getReceiveRtuAutoUploadPacketQueue(){
        return RtuAutoUploadPacketQueueSingle.receiveRtuAutoUploadPacketQueue;
    }

    
}
