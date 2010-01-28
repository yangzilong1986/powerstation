package com.hisun.server.manage.servlet;


import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Socket;
import java.util.ArrayList;

public class HiIpCheck {
    private String[] _ipLst = null;

    public HiIpCheck() {
    }

    public HiIpCheck(String ipLst) {

        setIpCheck(ipLst);
    }

    public HiIpCheck(String[] ipLst) {

        setIpCheck(ipLst);
    }

    public HiIpCheck(ArrayList ipLst) {

        setIpCheck(ipLst);
    }

    public void setIpCheck(String ipLst) {

        if (ipLst == null) {

            return;
        }

        this._ipLst = ipLst.split("\\|");
    }

    public void setIpCheck(String[] ipLst) {

        if (ipLst == null) {

            return;
        }

        this._ipLst = ipLst;
    }

    public void setIpCheck(ArrayList ipLst) {

        if (ipLst == null) {

            return;
        }

        this._ipLst = ((String[]) (String[]) ipLst.toArray());
    }

    public boolean check(String ip) {

        return containsIp(ip);
    }

    public boolean check(HttpServletRequest request) {

        return containsIp(getIpAddr(request));
    }

    public boolean check(Socket socket) {

        return containsIp(socket.getInetAddress().getHostAddress());
    }

    public String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {

            ip = request.getHeader("Proxy-Client-IP");
        }

        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {

            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {

            ip = request.getRemoteAddr();
        }

        return ip;
    }

    private boolean containsIp(String ip) {

        if (this._ipLst == null) {

            return true;
        }


        for (int i = 0; i < this._ipLst.length; ++i) {

            if (StringUtils.equals(this._ipLst[i], ip)) {

                return true;
            }
        }

        return false;
    }
}