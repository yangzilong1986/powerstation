package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.message.HiContext;
import com.hisun.util.HiComponentParaParser;
import com.hisun.util.HiXmlHelper;
import org.dom4j.Element;

public class HiInitComponentParamHandler implements IServerInitListener {
    public void serverInit(ServerEvent arg0) throws HiException {
        Element element = null;

        String componentConfig = HiContext.getRootContext().getStrProp("@SYS", "component.config");

        if (componentConfig == null) {
            return;
        }
        try {
            element = HiXmlHelper.getRootElement(componentConfig);
        } catch (Exception e) {
            arg0.getLog().error("load component config file:[ " + componentConfig + "] failure:[" + e + "]", e);

            return;
        }

        if (element == null) {
            return;
        }

        HiContext ctx1 = null;
        ctx1 = arg0.getServerContext().getFirstChild();

        for (; ctx1 != null; ctx1 = ctx1.getNextBrother()) {
            String strAppName = ctx1.getStrProp("app_name");
            HiComponentParaParser.setAppParam(ctx1, strAppName, element);
            HiContext ctx2 = ctx1.getFirstChild();
            for (; ctx2 != null; ctx2 = ctx2.getNextBrother()) {
                String code = ctx2.getStrProp("trans_code");
                HiComponentParaParser.setTrnParam(ctx2, strAppName, code, element);

                if (arg0.getLog().isDebugEnabled()) {
                    arg0.getLog().debug("交易:[" + code + "] " + ctx2);
                }
            }
            if (arg0.getLog().isDebugEnabled()) arg0.getLog().debug("应用:[" + strAppName + "] " + ctx1);
        }
    }
}