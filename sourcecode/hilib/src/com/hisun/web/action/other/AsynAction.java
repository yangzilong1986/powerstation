package com.hisun.web.action.other;


import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.web.action.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AsynAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    public String execute() throws Exception {

        Logger _log = this.logFactory.getLogger();

        if (_log.isDebugEnabled()) _log.debug("AsynAction execute starting");

        HttpServletRequest request = ServletActionContext.getRequest();

        HttpServletResponse response = ServletActionContext.getResponse();

        ServletContext context = ServletActionContext.getServletContext();


        HiETF etf = beforeProcess(request, _log);


        HiETF rspetf = null;

        if (!(StringUtils.isEmpty(this.txncode))) {

            rspetf = getCallHostService().callhost(this.txncode, etf, context);
        } else {

            rspetf = etf;

            rspetf.setChildValue("RSP_CD", "000000");
        }


        boolean flag = endProcess(request, rspetf, _log);

        request.setAttribute("etfsrc", this.source);

        request.setAttribute("group", this.group);

        request.setAttribute("etf", rspetf);

        if (flag) {

            response.setContentType("text/xml");

            response.setCharacterEncoding("gb2312");

            response.getWriter().write("<?xml version=\"1.0\" encoding=\"gb2312\" ?>" + rspetf.toString());

            response.getWriter().flush();


            if (this.output != null) {

                int index = StringUtils.indexOf(this.output, "action");

                if (index >= 0) {

                    response.sendRedirect(this.output);
                } else {

                    ServletActionContext.getServletContext().getRequestDispatcher(this.output).forward(request, response);
                }

                return "";
            }

            return "success";
        }


        return "";
    }
}