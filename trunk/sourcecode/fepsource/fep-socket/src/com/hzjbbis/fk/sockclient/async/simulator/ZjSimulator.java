package com.hzjbbis.fk.sockclient.async.simulator;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.sockclient.async.JAsyncSocket;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

public class ZjSimulator implements IRtuSimulator {
    public static final Logger log = Logger.getLogger(ZjSimulator.class);
    private JAsyncSocket client;
    private String strTask = "6899053806C11668811000010000000000000030805520211205047616";
    private MessageZj taskTemplate;
    private int rtua = 0;
    private String pwd = "123456";
    private byte fseq = 0;

    private byte getFseq() {
        synchronized (this) {
            ZjSimulator tmp5_4 = this;
            tmp5_4.fseq = (byte) (tmp5_4.fseq + 1);
            if ((((this.fseq > 127) ? 1 : 0) | ((this.fseq <= 0) ? 1 : 0)) != 0) this.fseq = 1;
            return this.fseq;
        }
    }

    public ZjSimulator() {
        this.taskTemplate = new MessageZj();
        try {
            this.taskTemplate.read(HexDump.toByteBuffer(this.strTask));
        } catch (Exception localException) {
        }
    }

    private MessageZj createHeart() {
        MessageZj msg = new MessageZj();
        msg.head.rtua = this.rtua;
        msg.head.c_func = 36;
        msg.head.c_dir = 1;
        msg.head.fseq = getFseq();
        return msg;
    }

    private MessageZj createLogin() {
        MessageZj msg = new MessageZj();
        msg.head.rtua = this.rtua;
        msg.head.c_func = 33;
        msg.head.c_dir = 1;
        msg.head.fseq = getFseq();
        msg.data = HexDump.toByteBuffer(this.pwd);
        return msg;
    }

    private MessageZj createTask() {
        MessageZj msg = new MessageZj();
        msg.head.rtua = this.rtua;
        msg.head.c_func = 2;
        msg.head.c_dir = 1;
        msg.head.fseq = getFseq();
        msg.data = this.taskTemplate.data;
        return msg;
    }

    public void onClose(JAsyncSocket client) {
        log.info("client closed. " + client);
        this.client = null;
    }

    public void onConnect(JAsyncSocket client) {
        this.client = client;
        sendLogin();
        log.info("client connected. " + client);
    }

    public void onReceive(JAsyncSocket client, IMessage message) {
        log.info("recv msg: " + message + " ,client:" + client);
    }

    public void onSend(JAsyncSocket client, IMessage message) {
        log.info("send msg: " + message + " ,client:" + client);
    }

    public void sendLogin() {
        if ((this.client != null) && (this.client.isConnected())) this.client.send(createLogin());
    }

    public void sendHeart() {
        if ((this.client != null) && (this.client.isConnected())) this.client.send(createHeart());
    }

    public void sendTask() {
        if ((this.client != null) && (this.client.isConnected())) this.client.send(createTask());
    }

    public int getRtua() {
        return this.rtua;
    }

    public void setRtua(int rtua) {
        this.rtua = rtua;
    }

    public String getState() {
        return ((this.client.isConnected()) ? "在线" : "断开");
    }
}