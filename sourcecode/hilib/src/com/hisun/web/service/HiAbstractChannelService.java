package com.hisun.web.service;


import com.hisun.common.HiSecurityFilter;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.web.filter.HiDataConvert;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;


public abstract class HiAbstractChannelService {
    protected IInvokeService _serviceBean;
    protected HiDataConvert _dataConvert;
    protected String _msgType;
    protected String _serverName;
    protected Logger _log;
    protected ServletConfig _config;
    protected HiSecurityFilter _securityFilter;
    protected String _redirectURL;
    protected String _encoding;


    public HiAbstractChannelService() {

        this._dataConvert = new HiDataConvert();

        this._msgType = "PLTIN0";

        this._serverName = "CAPPWEB1";

        this._log = HiLog.getLogger("SYS.trc");

        this._config = null;

        this._securityFilter = null;

        this._redirectURL = null;

        this._encoding = null;

    }


    public abstract void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException;


    protected void bindsRqData(HiMessage msg, HttpServletRequest request, HttpServletResponse response) {

        msg.setHeadItem("_WEB_REQUEST", request);

        msg.setHeadItem("_WEB_RESPONSE", response);

        msg.setHeadItem("_WEB_SESSION", request.getSession());

        msg.setHeadItem("_WEB_APPLICATION", this._config.getServletContext());


        HttpSession session = request.getSession();

        Enumeration en = session.getAttributeNames();

        HiETF root = msg.getETFBody();

        HiETF sessionRoot = null;

        while (en.hasMoreElements()) {

            String name = (String) en.nextElement();

            Object value = session.getAttribute(name);

            if (value instanceof HashMap) {

                HashMap valueMap = (HashMap) value;

                this._log.debug("[" + name + "][" + valueMap + "]");

                Iterator iter = valueMap.keySet().iterator();

                HiETF group = null;

                if (iter.hasNext()) {

                    group = root.addNode(name);

                }

                while (iter.hasNext()) {

                    String name1 = (String) iter.next();

                    String value1 = (String) valueMap.get(name1);

                    this._log.debug("[" + name1 + "][" + value1 + "]");

                    if (value1 == null) {

                        continue;

                    }

                    group.setChildValue(name1, value1);

                }

            } else {

                if (value == null) {

                    continue;

                }

                if (sessionRoot == null) {

                    sessionRoot = root.addNode("SESSION");

                }

                sessionRoot.setChildValue(name, value.toString());

            }

        }


        if (request.getQueryString() == null) root.setChildValue("REQ_URL", request.getRequestURL().toString());

        else {

            root.setChildValue("REQ_URL", request.getRequestURL() + "?" + request.getQueryString());

        }


        msg.setHeadItem("SIP", request.getRemoteAddr());

    }


    public void init(ServletConfig config) throws ServletException {

        this._config = config;

        String serviceName = config.getInitParameter("service");

        if (StringUtils.isBlank(serviceName)) {

            throw new ServletException("service is empty");

        }

        try {

            Class clazz = Class.forName(serviceName);

            this._serviceBean = ((IInvokeService) clazz.newInstance());

        } catch (Exception e) {

            throw new ServletException(serviceName, e);

        }

        String ip = config.getInitParameter("ip");

        if (StringUtils.isNotBlank(ip)) try {

            BeanUtils.setProperty(this._serviceBean, "ip", ip);

        } catch (Exception e1) {

        }

        String port = config.getInitParameter("port");

        if (StringUtils.isNotBlank(port)) {

            try {

                BeanUtils.setProperty(this._serviceBean, "port", port);

            } catch (Exception e1) {

            }

        }

        String logSwitch = config.getInitParameter("logSwitch");

        if (StringUtils.isNotBlank(logSwitch)) try {

            BeanUtils.setProperty(this._serviceBean, "logSwitch", logSwitch);

        } catch (Exception e1) {

        }

        this._msgType = config.getInitParameter("msgtype");

        if (StringUtils.isBlank(this._msgType)) {

            this._msgType = "PLTIN0";

        }


        this._serverName = config.getInitParameter("server");

        if (StringUtils.isBlank(this._serverName)) {

            this._serverName = "CAPPWEB1";

        }


        if (config.getInitParameter("encoding") != null) {

            this._encoding = config.getInitParameter("encoding");

        }


        Enumeration en = config.getInitParameterNames();

        while (en.hasMoreElements()) {

            String name = (String) en.nextElement();

            if (name.equalsIgnoreCase("service")) continue;

            if (name.equalsIgnoreCase("msgtype")) {

                continue;

            }

            String value = config.getInitParameter(name);

            this._dataConvert.add(name.toUpperCase(), value);

        }


        String file = config.getInitParameter("securityFile");

        this._log.info(config.getServletName() + " init param:[securityFile=" + file + "]");


        if (StringUtils.isNotBlank(file)) {

            if (!(file.startsWith("/"))) {

                file = "/" + file;

            }

            this._redirectURL = config.getInitParameter("redirectURL");

            this._log.info(config.getServletName() + " init param:[redirectURL=" + this._redirectURL + "]");


            if (StringUtils.isBlank(this._redirectURL)) throw new ServletException("redirectURL not config");

            try {

                this._securityFilter = new HiSecurityFilter();

                this._securityFilter.setUrl(config.getServletContext().getResource(file));


                this._securityFilter.setFile(config.getServletContext().getRealPath(file));


                this._securityFilter.load();

            } catch (Exception e) {

                throw new ServletException("load:[" + file + "] failure", e);

            }

        }

    }

}