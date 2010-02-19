package com.hzjbbis.fk.fe.gprs.heartbeat;

import java.util.Calendar;

public class HeartQuartz {
    private HeartBeatMessage heartBeatMessage;

    public void initHeart() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int weekNO = c.get(3);
        this.heartBeatMessage.initHeart(weekNO + 1);
    }

    public void setHeartBeatMessage(HeartBeatMessage heartBeatMessage) {
        this.heartBeatMessage = heartBeatMessage;
    }
}