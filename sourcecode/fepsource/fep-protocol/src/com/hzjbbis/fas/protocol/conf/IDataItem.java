package com.hzjbbis.fas.protocol.conf;

import java.util.List;

public abstract interface IDataItem {
    public abstract List getStandardDatas();

    public abstract String getSdRobot();

    public abstract boolean isMe(String paramString);
}