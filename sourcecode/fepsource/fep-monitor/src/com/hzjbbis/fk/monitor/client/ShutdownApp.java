package com.hzjbbis.fk.monitor.client;

public class ShutdownApp {
    public static void main(String[] args) {
        int j;
        String ip = "127.0.0.1";
        int port = 10006;
        for (String arg : args) {
            if (arg.startsWith("-ip=")) {
                ip = arg.substring(4).trim();
            } else if (arg.startsWith("-port=")) {
                port = Integer.parseInt(arg.substring(6).trim());
            }
        }
        MonitorClient client = new MonitorClient();
        client.setHostIp(ip);
        client.setHostPort(port);
        client.connect();
        int j = 5;
        while ((!(client.isConnected())) && (j-- > 0)) try {
            Thread.sleep(1000L);
        } catch (Exception localException2) {
        }
        if (client.isConnected()) client.shutdownApplication();
        try {
            Thread.sleep(100L);
        } catch (Exception localException3) {
        }
        client.close();
    }
}