package com.hzjbbis.ws.logic;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;

@WebService
public abstract interface WsHeartbeatQuery {
    @WebMethod
    public abstract int heartCount(@WebParam(name = "rtua") int paramInt);

    public abstract long lastHeartbeatTime(@WebParam(name = "rtua") int paramInt);

    public abstract int totalRtuWithHeartByA1(@WebParam(name = "a1") byte paramByte);

    public abstract int totalRtuWithHeartByA1Time(@WebParam(name = "a1") byte paramByte, @WebParam(name = "beginTime") Date paramDate);

    public abstract String queryHeartbeatInfo(@WebParam(name = "rtua") int paramInt);

    public abstract String queryHeartbeatInfoByDate(@WebParam(name = "rtua") int paramInt1, @WebParam(name = "date") int paramInt2);
}