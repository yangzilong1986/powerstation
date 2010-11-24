/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.common;

import pep.codec.protocol.gb.PmPacket;

/**
 *
 * @author luxiaochung
 */
public interface PepCommunicatorInterface {
    /**
     * 向终端发送报文，报文以sequence作为向下发送的存根，
     * 返回时，从RtuRespPacketQueue取得终端返回的报文，队列中的对象也包括这个存根
     * @param sequence
     * @param packet
     */
    public  void SendPacket(int sequence, PmPacket packet);

    /**
     * 主动上送时，报文放入RtuAutoUploadPacketQueue队列，包括不是主动上送的事件，
     * 只要不是SendPacket引起的都放入这个队列
     * 对业务层而言，无论是否配置3类数据主动上送任务，事件都可以看作是主动上送的。
     * @return
     */
    public RtuAutoUploadPacketQueue getRtuAutoUploadPacketQueueInstance();

    /**
     * 由SendPacket引发的终端上送报文帧放入RtuRespPacketQueue队列,
     * 如果是超时或者不在线，返回的PmPacket是发送的报文帧
     * @return
     */
    public RtuRespPacketQueue getRtuRespPacketQueueInstance();

    public void checkUndespPackets();
}
