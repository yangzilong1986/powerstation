package com.hzjbbis.ws.logic;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public abstract interface WsFEManage {
    public abstract boolean addGprsGateChannel(@WebParam(name = "ip") String paramString1, @WebParam(name = "port") int paramInt, @WebParam(name = "gateName") String paramString2);

    public abstract boolean addUmsChannel(@WebParam(name = "appid") String paramString1, @WebParam(name = "password") String paramString2);

    public abstract void stopModule(@WebParam(name = "name") String paramString);

    public abstract void startModule(@WebParam(name = "name") String paramString);

    public abstract void updateFlow();
}