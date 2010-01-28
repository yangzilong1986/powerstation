package com.hisun.web.servlet;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.web.service.HiWebServiceImpl;
import com.hisun.web.service.IChannelService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HiWebDispatcherServlet extends HttpServlet {
    private Logger _log = HiLog.getLogger("SYS.trc");
    IChannelService channelService = new HiWebServiceImpl();

    public void destroy() {

        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            this._log.info("doPost:[" + request.getRequestURL().toString() + "]");

            this.channelService.execute(request, response);
        } catch (Throwable t) {

            Logger log = HiLog.getErrorLogger("SYS.log");

            log.error(t, t);

            if (t instanceof ServletException) {

                throw ((ServletException) t);
            }

            throw new ServletException(t);
        }
    }

    public void init() throws ServletException {

        this.channelService.init(getServletConfig());
    }
}