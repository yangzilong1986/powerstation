package com.hisun.handler;

import com.hisun.listener.HiTimeListener;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HiJobRunShell extends TimerTask {
    private HiTimeListener listener = null;

    public HiJobRunShell(HiTimeListener listener) {
        this.listener = listener;
    }

    public void run() {
        try {
            Date currentDate = new Date(System.currentTimeMillis() + 60000L);
            Date startDate = DateUtils.truncate(currentDate, 12);

            HiMessage mess = this.listener.getHiMessage();
            HiMessageContext ctx = new HiMessageContext();
            ctx.setCurrentMsg(mess);
            HiMessageContext.setCurrentMessageContext(ctx);

            this.listener.getServer().process(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Timer getJobRunShell(int second, HiTimeListener listener) {
        try {
            Date currentDate = new Date(System.currentTimeMillis() + 60000L);
            Date startDate = DateUtils.truncate(currentDate, 12);

            Timer timer = new Timer();
            HiJobRunShell run = new HiJobRunShell(listener);
            timer.schedule(run, startDate, second * 1000);
            return timer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}