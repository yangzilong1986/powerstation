package com.hisun.dispatcher.ejb;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.startup.HiStartup;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.InitialContext;


public class HiASyncProcess implements MessageDrivenBean, MessageListener {
    private static boolean started = false;
    private static Object lock = new Object();
    private MessageDrivenContext context;
    private static Logger log = HiLog.getErrorLogger("SYS.log");
    private HiStartup startup = null;

    private String serverName = null;

    private static HiStringManager sm = HiStringManager.getManager();


    public void ejbRemove() throws EJBException {

        if (this.startup == null) return;

        try {

            this.startup.setFailured(false);

            this.startup.destory();

        } catch (HiException e) {

            System.out.println(sm.getString("HiRouterInBean.remove00", this.startup.getServerName()));

        }


        this.startup = null;

    }


    public void setMessageDrivenContext(MessageDrivenContext newContext) throws EJBException {

        this.context = newContext;

    }


    public void onMessage(Message arg0) {

        Logger msgLog = null;

        try {

            HiMessage message = (HiMessage) ((ObjectMessage) arg0).getObject();

            if (message == null) {

                System.out.println("throw out invalid message");

                return;

            }


            msgLog = HiLog.getLogger(message);

            if (msgLog.isDebugEnabled()) {

                msgLog.debug("HiASyncProcess.onMessage start");

            }

            if (msgLog.isDebugEnabled()) {

                msgLog.debug(sm.getString("HiASyncProcess.onMessage", message));

            }


            String cmd = message.getHeadItem("CMD");

            if (!(StringUtils.isEmpty(cmd))) manage(message);

            else process(message);

        } catch (Throwable e) {

            if (e instanceof HiException) {

                String msgCode = ((HiException) e).getCode();

                if ((StringUtils.equals(msgCode, "212010")) || (StringUtils.equals(msgCode, "212101"))) {

                    log.error(e, e);


                    Thread.yield();

                    try {

                        Thread.currentThread();
                        Thread.sleep(1000L);

                    } catch (InterruptedException e1) {

                        log.error(e1, e1);

                    }

                    throw new RuntimeException("Server not start", e);

                }

            }

            log.error(e, e);

        }

    }


    public HiMessage process(HiMessage message) throws HiException {

        if (this.startup == null) {

            message.setStatus("E");

            message.setHeadItem("SSC", "212010");


            return message;

        }


        return this.startup.process(message);

    }


    public HiMessage manage(HiMessage message) throws EJBException {

        if (this.startup == null) {

            message.setStatus("E");

            message.setHeadItem("SSC", "211007");

            message.setHeadItem("SEM", sm.getString("211007"));

            return message;

        }

        try {

            return this.startup.manage(message);

        } catch (Throwable e) {

            message.setStatus("E");

            if (e instanceof HiException) {

                message.setHeadItem("SSC", ((HiException) e).getCode());

                message.setHeadItem("SEM", ((HiException) e).getAppMessage());

            } else {

                message.setHeadItem("SSC", "211007");

                message.setHeadItem("SEM", sm.getString("211007"));

            }


            HiLog.logSysError("initializ00", e);
        }

        return message;

    }


    public void ejbCreate() {

        initialize();

    }


    private synchronized void initialize() {

        try {

            InitialContext ic = new InitialContext();

            this.serverName = ((String) ic.lookup("java:comp/env/ServerName"));


            if (StringUtils.isEmpty(this.serverName)) {

                System.out.println(sm.getString("HiASyncProcess.initialize00"));


                log.error(sm.getString("HiASyncProcess.initialize00"));

                return;

            }

            this.startup = HiStartup.getInstance(this.serverName);

            synchronized (lock) {

                if (!(started)) {

                    System.out.println("ejbCreate");

                    this.startup = HiStartup.initialize(this.serverName, true);

                    started = true;

                } else {

                    this.startup = HiStartup.initialize(this.serverName);

                }

            }

        } catch (Throwable t) {

            HiLog.logSysError(sm.getString("HiASyncProcess.initialize01", this.serverName), t);


            System.out.println(sm.getString("HiASyncProcess.initialize01", this.serverName));

        }

    }

}