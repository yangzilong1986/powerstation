package com.hisun.framework.decorator;

import com.hisun.framework.HiDefaultServer;
import com.hisun.framework.filter.LogFilter;
import com.hisun.handler.HiInitComponentParamHandler;
import com.hisun.handler.HiInitServerParamHandler;
import com.hisun.handler.HiInitTransParamHandler;
import com.hisun.service.IObjectDecorator;

public class ServerDecorator implements IObjectDecorator {
    public Object decorate(Object object) {
        return object;
    }

    public Object decorate(Object object, String style) throws Exception {
        if (style.equals("addDeclare")) addDeclare((HiDefaultServer) object);
        else if (style.equals("addFilter")) {
            addFilter((HiDefaultServer) object);
        }
        return object;
    }

    private void addDeclare(HiDefaultServer server) throws Exception {
        server.addDeclare("InitServerParam", new HiInitServerParamHandler());
    }

    private void addFilter(HiDefaultServer server) throws Exception {
        server.addDeclare("InitComponentParam", new HiInitComponentParamHandler());
        server.addDeclare("InitTransParam", new HiInitTransParamHandler());
        server.addFilter(new LogFilter("-----server [" + server.getName() + "] process()", server.getLog()));

        server.buildProcHandler();
    }
}