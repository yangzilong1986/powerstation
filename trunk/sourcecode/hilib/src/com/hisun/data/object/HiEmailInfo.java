package com.hisun.data.object;

public class HiEmailInfo {
    private String host;
    private int port;
    private String user;
    private String from;
    private String name;
    private String passwd;

    public String getHost() {

        return this.host;
    }

    public void setHost(String host) {

        this.host = host;
    }

    public int getPort() {

        return this.port;
    }

    public void setPort(int port) {

        this.port = port;
    }

    public String getUser() {

        return this.user;
    }

    public void setUser(String user) {

        this.user = user;
    }

    public String getFrom() {

        return this.from;
    }

    public void setFrom(String from) {

        this.from = from;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPasswd() {

        return this.passwd;
    }

    public void setPasswd(String passwd) {

        this.passwd = passwd;
    }

    public String toString() {

        return String.format("[%s][%d][%s][%s][%s][%s]", new Object[]{this.host, Integer.valueOf(this.port), this.user, this.from, this.name, this.passwd});
    }
}