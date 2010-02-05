package com.hisun.dispatcher.servlet;

import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.startup.HiStartup;
import com.hisun.util.HiStringManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HiStartupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String serverName = null;
    private HiStartup startup = null;
    private static HiStringManager sm = HiStringManager.getManager();
    private static Logger log = HiLog.getErrorLogger("SYS.log");

    public void destroy() {
        super.destroy();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void init() throws ServletException {
        this.serverName = getInitParameter("serverName");
        if (this.serverName == null) {
            System.out.println(sm.getString("HiRouterInBean.initialize00"));
            log.error(sm.getString("HiRouterInBean.initialize00"));
            return;
        }
        try {
            this.startup = HiStartup.initialize(this.serverName);
        } catch (Throwable e) {
            HiLog.logSysError(sm.getString("HiRouterInBean.initialize01", this.serverName), e);
        }
    }
}