package com.hisun.server.manage.servlet;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.SystemUtils;

public class HiServerManagerClient {
    public static void main(String[] args) throws Exception {

        if (args.length < 3) {

            System.out.println("url {start|restart|stop|pause|resume|list} {-s|-a|-g} [name]");


            System.exit(-1);
        }


        HttpClient httpclient = new HttpClient();

        PostMethod method = new PostMethod(args[0]);

        method.addParameter("arg_num", String.valueOf(args.length - 1));

        for (int i = 1; i < args.length; ++i)

            method.addParameter("arg_" + i, args[i]);
        try {

            httpclient.executeMethod(method);
        } catch (Exception e) {

            System.out.println(e.getMessage());

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

    public static void printHex(byte[] bs) {

        for (int i = 0; i < bs.length; ++i) {

            if (i % 8 == 0) System.out.println();

            System.out.print(Integer.toHexString(bs[i] & 0xFF) + " ");
        }
    }
}