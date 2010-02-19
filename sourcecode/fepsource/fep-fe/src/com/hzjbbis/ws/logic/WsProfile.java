package com.hzjbbis.ws.logic;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public abstract interface WsProfile {
    @WebMethod
    public abstract String allProfile();

    public abstract String modulesProfile();

    public abstract ModuleSimpleProfile[] getAllModuleProfile();

    public abstract boolean updateRtuSimNum(@WebParam(name = "rtuSimList") String paramString);

    public abstract boolean updateRemoteUpdateRtuaList(@WebParam(name = "rtuaList") String paramString);
}