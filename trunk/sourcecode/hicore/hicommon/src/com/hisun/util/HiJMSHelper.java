package com.hisun.util;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;

import javax.jms.*;

public class HiJMSHelper {
    private QueueConnection qc = null;
    private QueueSession qs = null;
    private QueueSender qsend = null;

    public void initialize(String factoryName, String queueName) throws HiException {
        try {
            HiServiceLocator locator = HiServiceLocator.getInstance();
            QueueConnectionFactory qcf = locator.getQueueConnectionFactory(factoryName);
            this.qc = qcf.createQueueConnection();
            this.qs = this.qc.createQueueSession(false, 1);
            Queue queue = locator.getQueue(queueName);
            this.qsend = this.qs.createSender(queue);
            this.qc.start();
        } catch (JMSException e) {
            try {
                this.qsend.close();
            } catch (Exception ex) {
            }
            try {
                this.qs.close();
            } catch (Exception ex) {
            }
            try {
                this.qc.close();
            } catch (Exception ex) {
            }
            throw new HiException("212009", "JMS", e);
        }
    }

    public void destory() {
        try {
            this.qsend.close();
        } catch (Exception ex) {
        }
        try {
            this.qs.close();
        } catch (Exception ex) {
        }
        try {
            this.qc.close();
        } catch (Exception ex) {
        }
    }

    public void sendMessage(HiMessageContext ctx) throws HiException {
        try {
            ObjectMessage msg = this.qs.createObjectMessage(ctx.getCurrentMsg());
            this.qsend.send(msg);
        } catch (Exception e) {
            throw new HiException("212006", "JMS", e);
        }
    }

    public void sendMessage(HiMessageContext ctx, int priority) throws HiException {
        try {
            ObjectMessage msg = this.qs.createObjectMessage(ctx.getCurrentMsg());
            msg.setJMSPriority(priority);
            this.qsend.send(msg);
        } catch (Exception e) {
            throw new HiException("212006", "JMS", e);
        }
    }

    public void sendMessage(HiMessage sendMsg) throws HiException {
        try {
            ObjectMessage msg = this.qs.createObjectMessage(sendMsg);
            this.qsend.send(msg);
        } catch (Exception e) {
            throw new HiException("212006", "JMS", e);
        }
    }

    public void sendMessage(HiMessage sendMsg, int priority) throws HiException {
        try {
            ObjectMessage msg = this.qs.createObjectMessage(sendMsg);
            msg.setJMSPriority(priority);
            this.qsend.send(msg);
        } catch (Exception e) {
            throw new HiException("212006", "JMS", e);
        }
    }
}