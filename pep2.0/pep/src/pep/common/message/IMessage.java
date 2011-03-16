/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.common.message;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import pep.common.exception.MessageParseException;

/**
 *
 * @author Thinkpad
 */
public interface IMessage {
    // 消息传输方向常量

    static final Integer DIRECTION_UP = new Integer(0);
    static final Integer DIRECTION_DOWN = new Integer(1);
    // 消息优先级常量
    static final int PRIORITY_LOW = 0;
    static final int PRIORITY_NORMAL = 1;
    static final int PRIORITY_HIGH = 2;
    static final int PRIORITY_VIP = 3;
    static final int PRIORITY_MAX = 5;
    static final int STATE_INVALID = -1;
    static final int STATE_READ_HEAD = 0x01;
    static final int STATE_READ_DATA = 0x02;
    static final int STATE_READ_TAIL = 0x03;
    static final int STATE_READ_DONE = 0x0F;
    static final int STATE_SEND_HEAD = 0x11;
    static final int STATE_SEND_DATA = 0x12;
    static final int STATE_SEND_TAIL = 0x13;
    static final int STATE_SEND_DONE = 0x2F;

    /**
     * 消息产生的源，一般为AsyncSocketClient对象
     * @return
     */
    IoSession getSource();

    void setSource(IoSession src);

    /**
     * 返回消息的类型
     * @return
     */
    MessageType getMessageType();

    /**
     * 消息自识别技术。
     * 特定消息对象从缓冲区readBuffer读取数据，并判断是否完整报文。
     * @param readBuffer ：服务模块提供的读缓冲区
     * @return
     *   true : 成功读取完整报文；
     *   false：缓冲区中的数据不足以组成完整报文
     * @throws MessageParseException
     * 	 如果消息读取过程中，发现数据格式不正确，则throw 异常
     */
    boolean read(IoBuffer readBuffer) throws MessageParseException;

    /**
     * 消息对象把要发送的数据放到发送缓冲区writeBuffer
     * @param writeBuffer
     * @return
     * @throws MessageParseException
     */
    boolean write(IoBuffer writeBuffer);

    /**
     * 消息的通讯时间。例如网关完整收到报文的时间。
     * @return
     */
    long getIoTime();

    void setIoTime(long time);

    /**
     * 对方通讯地址 ip:port 形式
     * @return
     */
    String getPeerAddr();

    void setPeerAddr(String peer);

    /**
     * 对于网关来说，需要知道终端实际连接的外网IP：PORT地址。
     * @return
     */
    String getServerAddress();

    void setServerAddress(String serverAddress);

    /**
     * 取该消息的通信方式
     * @return
     */
    String getTxfs();

    void setTxfs(String fs);

    int getRtua();

    /**
     * 取该消息的组帧结果
     * @return
     */
    public String getStatus();

    void setStatus(String status);

    /**
     * 取该消息的下行命令ID
     * @return
     */
    public Long getCmdId();

    /**
     * 返回消息优先级。
     * @return
     */
    int getPriority();

    void setPriority(int priority);

    byte[] getRawPacket();

    String getRawPacketString();

    /**
     * 判断是否心跳报文(心跳请求或者应答)
     * @return
     */
    boolean isHeartbeat();

    boolean isTask();

    void setTask(boolean isTask);

    int length();
}
