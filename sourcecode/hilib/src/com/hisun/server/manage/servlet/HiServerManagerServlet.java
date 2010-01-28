package com.hisun.server.manage.servlet;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class HiServerManagerServlet extends HttpServlet {
    private String _config;
    private HiIpCheck _ipCheck;

    public HiServerManagerServlet() {

        this._config = null;

        this._ipCheck = new HiIpCheck();
    }

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        this._config = config.getInitParameter("config");

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


        Logger log = HiLog.getErrorLogger("SYS.log");
        try {

            StringBuffer buffer = new StringBuffer();

            int num = NumberUtils.toInt(request.getParameter("arg_num"));

            String[] args = new String[num + 1];

            for (int i = 0; i < num; ++i) {

                args[(i + 1)] = request.getParameter("arg_" + (i + 1));
            }

            args[0] = this._config;

            boolean successed = true;

            if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
                successed = HiServerManager.invoke(args, buffer);
            else {

                successed = HiServerManagerPOJO.invoke(args, buffer);
            }


            OutputStream os = response.getOutputStream();


            if (!(successed)) {

                buffer.append("\n");

                buffer.append("## one and more servers failed! see SYS.log file for complete information");

                buffer.append("\n");
            }

            os.write(buffer.toString().getBytes());
        } catch (Throwable t) {

            log.error(t, t);

            throw new ServletException(t.getMessage());
        }
    }
}