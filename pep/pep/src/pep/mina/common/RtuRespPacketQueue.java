/*
 * 允许多个线程从队列中读取
 */

package pep.mina.common;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author luxiaochung
 */
public class RtuRespPacketQueue {
    private  Queue<SequencedPmPacket> receiveRturespPacketQueue;  //接收到的报文或不在线/超时错误返回报文

    private static RtuRespPacketQueue theInstance=null;

    public static RtuRespPacketQueue instance(){
        if (theInstance==null){
            theInstance = new RtuRespPacketQueue();
        }
        return theInstance;
    }

    public int size(){
        return receiveRturespPacketQueue.size();
    }

    private RtuRespPacketQueue(){
        receiveRturespPacketQueue = new ConcurrentLinkedQueue<SequencedPmPacket>();
    }

    public synchronized void addPacket(SequencedPmPacket sequncedPmPacket){
        receiveRturespPacketQueue.add(sequncedPmPacket);
        notifyAll();
    }

    public synchronized SequencedPmPacket PollPacket() throws InterruptedException{
        while (true){
            if (!receiveRturespPacketQueue.isEmpty()){
                return receiveRturespPacketQueue.poll();
            }
            else
            {
                this.wait();
            }
        }
    }
}
