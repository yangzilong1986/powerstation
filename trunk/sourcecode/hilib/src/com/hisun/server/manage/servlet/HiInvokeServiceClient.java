package com.hisun.server.manage.servlet;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class HiInvokeServiceClient {
    public static void main(String[] args) throws Exception {

        HttpClient httpclient = new HttpClient();

        PostMethod method = new PostMethod(args[0]);
        try {

            for (int i = 1; i < args.length; ++i) {

                String[] nameValuePair = StringUtils.split(args[i], "=");

                if (nameValuePair.length != 2) {
                    continue;
                }

                method.addParameter(nameValuePair[0], nameValuePair[1]);
            }

            httpclient.executeMethod(method);
        } catch (Exception e) {

            System.out.println(e);

            System.exit(-1);
        }

        int ret = 0;

        if (method.getStatusCode() == 200) {

            byte[] bs = method.getResponseBody();

            System.out.println(new String(bs));
        } else {
            String tmp;

            String response = method.getResponseBodyAsString();


            int i = response.indexOf("<pre>");

            int j = response.indexOf("</pre>");


            if ((i != -1) && (j != -1) && (i < j)) tmp = response.substring(i + 5, j - 5);
            else {

                tmp = response;
            }

            int k = tmp.indexOf(SystemUtils.LINE_SEPARATOR);

            if (k != -1) System.out.println(tmp.substring(0, k));
            else {

                System.out.println(tmp);
            }

            System.out.println("see SYS.log file for complete information");

            ret = -1;
        }

        method.releaseConnection();

        System.exit(ret);
    }
}