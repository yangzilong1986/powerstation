package com.hisun.server.manage.servlet;


import com.hisun.dispatcher.HiRouterOut;
import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


public class HiInvokeServiceServlet extends HttpServlet {
    private String _server;
    private String _msgType;
    private HiIpCheck _ipCheck;


    public HiInvokeServiceServlet() {

        this._ipCheck = new HiIpCheck();

    }


    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        this._server = config.getInitParameter("server");

        this._msgType = config.getInitParameter("msgType");

        String tmp = config.getInitParameter("ipLst");

        if (tmp == null) {

            tmp = getServletContext().getInitParameter("ipLst");

        }

        this._ipCheck.setIpCheck(tmp);

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!(this._ipCheck.check(request))) {

            response.getWriter().write("client ip:[" + this._ipCheck.getIpAddr(request) + "] deny");

            return;

        }


        String service = null;

        String objSvr = null;

        HiETF etf = HiETFFactory.createETF();

        Enumeration en = request.getParameterNames();

        while (en.hasMoreElements()) {

            String name = (String) en.nextElement();

            String value = request.getParameter(name);

            if (name.equalsIgnoreCase("Service")) {

                service = value;

            }


            if (name.equalsIgnoreCase("ObjSvr")) {

                objSvr = value;

            }


            etf.setChildValue(name, value);

        }

        if (service == null) {

            throw new ServletException("service is null");

        }


        HiMessage msg = null;

        if (StringUtils.isNotBlank(objSvr)) msg = new HiMessage(objSvr, this._msgType);

        else {

            msg = new HiMessage(this._server, this._msgType);

        }

        msg.setBody(etf);


        msg.setHeadItem("STC", service);

        if (StringUtils.isNotBlank(objSvr)) {

            msg.setHeadItem("SDT", objSvr);

        }

        msg.setHeadItem("SCH", "rq");

        long curtime = System.currentTimeMillis();

        msg.setHeadItem("STM", new Long(curtime));

        HiMessageContext ctx = new HiMessageContext();

        ctx.setCurrentMsg(msg);

        HiMessageContext.setCurrentMessageContext(ctx);

        try {

            msg = HiRouterOut.syncProcess(msg);

        } catch (HiException e) {

        } finally {

            HiLog.close(msg);

        }

        etf = msg.getETFBody();

    }

}