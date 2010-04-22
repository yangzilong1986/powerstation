/*
 * 主动上送队列，允许多个线程从队列中取，（线程应该扑捉InterruptedException，以终止线程——线程的基本形式）
 */

package pep.mina.common;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import pep.codec.protocol.gb.PmPacket;

/**
 *
 * @author luxiaochung
 */
public class RtuAutoUploadPacketQueue {
    private  Queue<PmPacket> receiveRtuAutoUploadPacketQueue;  //接收到的报文或不在线/超时错误返回报文

    private static RtuAutoUploadPacketQueue theInstance=null;
    
    public static RtuAutoUploadPacketQueue instance(){
        if (theInstance==null){
            theInstance = new RtuAutoUploadPacketQueue();
        }
        return theInstance;
    }
    
    private RtuAutoUploadPacketQueue(){
        receiveRtuAutoUploadPacketQueue = new ConcurrentLinkedQueue<PmPacket>();
    }

    public synchronized void addPacket(PmPacket pack){
        receiveRtuAutoUploadPacketQueue.add(pack);
        this.notifyAll();
    }
    
    public synchronized PmPacket PollPacket() throws InterruptedException{
        while (true){
            if (!receiveRtuAutoUploadPacketQueue.isEmpty()){
                return receiveRtuAutoUploadPacketQueue.poll();
            }
            else
            {
                this.wait();
            }
        }
    }
}
