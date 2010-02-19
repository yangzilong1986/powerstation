package com.hzjbbis.fas.startup;

import com.hzjbbis.fas.model.FaalGWNoParamRequest;
import com.hzjbbis.fas.model.FaalRequestRtuParam;
import com.hzjbbis.fas.protocol.handler.ProtocolHandler;
import com.hzjbbis.fas.protocol.handler.ProtocolHandlerFactory;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gw.MessageGw;

public class Application {
    public static void main(String[] args) {
        initialize();
        IMessage msg = new MessageGw();
        ProtocolHandlerFactory factory = ProtocolHandlerFactory.getInstance();
        ProtocolHandler handler = factory.getProtocolHandler(MessageGw.class);

        FaalGWNoParamRequest request = new FaalGWNoParamRequest();
        FaalRequestRtuParam rtuParam = new FaalRequestRtuParam();
        int[] tn = {9};
        rtuParam.setTn(tn);
        rtuParam.addParam("04F003", "10.137.253.8:8101#127.0.0.1:10000#zjdl.zj");
        request.setType(4);
        request.addRtuParam(rtuParam);
        IMessage[] messages = handler.createMessage(request);
    }

    public static void initialize() {
        ClassLoaderUtil.initializeClassPath();
    }
}