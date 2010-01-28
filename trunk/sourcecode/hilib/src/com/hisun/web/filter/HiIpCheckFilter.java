package com.hisun.web.filter;


import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class HiIpCheckFilter implements Filter {
    private HashMap ipMap;


    public HiIpCheckFilter() {

        this.ipMap = new HashMap();

    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Iterator iter = this.ipMap.keySet().iterator();


        while (iter.hasNext()) {

            String url = (String) iter.next();

            if (!(request.getServletPath().matches(url))) {

                continue;

            }

            ArrayList ipLst = (ArrayList) this.ipMap.get(url);

            if (!(ipLst.contains(getIpAddr(request)))) {

                response.getWriter().write("client ip:[" + getIpAddr(request) + "] deny");


                return;

            }

        }


        filterChain.doFilter(request, response);

    }


    public void destroy() {

        this.ipMap.clear();

    }


    public void init(FilterConfig filterConfig) throws ServletException {

        String ipLst = filterConfig.getInitParameter("ipLst");

        if (StringUtils.isNotBlank(ipLst)) parseIpLst(ipLst);

    }


    private String getIpAddr(HttpServletRequest request) {

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


    private void parseIpLst(String ipLst) {

        int state = 0;

        StringBuffer tmp = new StringBuffer();

        String url = null;

        ArrayList tmpIpLst = null;

        for (int i = 0; i < ipLst.length(); ++i)

            switch (state) {

                case 0:

                    if (ipLst.charAt(i) == ':') {

                        state = 1;

                        url = tmp.toString();

                        tmp.setLength(0);

                    } else {

                        tmp.append(ipLst.charAt(i));
                    }

                    break;

                case 1:

                    if (ipLst.charAt(i) == '{') {

                        tmpIpLst = new ArrayList();

                    } else if (ipLst.charAt(i) == '}') {

                        state = 2;

                    } else if (ipLst.charAt(i) == '|') {

                        tmpIpLst.add(tmp.toString());

                        tmp.setLength(0);

                    } else {

                        tmp.append(ipLst.charAt(i));
                    }

                    break;

                case 2:

                    if ((i == ipLst.length() - 1) || (ipLst.charAt(i) == '|')) {

                        this.ipMap.put(url, tmpIpLst);

                        state = 0;

                    }

            }

    }


    public static void main(String[] args) {

        HiIpCheckFilter ipCheck = new HiIpCheckFilter();

        ipCheck.parseIpLst("url1:{ip1|ip2|ip3|ip4|}|url2:{ip1|ip2|ip3|ip4|}|");

        System.out.println(ipCheck.ipMap);

    }

}