package com.hisun.deploy;


import com.hisun.exception.HiException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HiLoadHelper {
    public static void execute(String requestUrl) throws HiException {

        HttpURLConnection conn = null;
        try {

            URL url = new URL(requestUrl);

            System.out.println("URL:" + requestUrl);

            conn = (HttpURLConnection) url.openConnection();


            System.out.println("openConnection .....");

            conn.setDoOutput(true);


            conn.setRequestMethod("POST");

            System.out.println("code:" + conn.getResponseCode());
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {

            if (conn != null) conn.disconnect();
        }
    }
}