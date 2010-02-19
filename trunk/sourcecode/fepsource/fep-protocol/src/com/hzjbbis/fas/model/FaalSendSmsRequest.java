package com.hzjbbis.fas.model;

import java.util.List;

public class FaalSendSmsRequest extends FaalRequest {
    private static final long serialVersionUID = 1559576885378604677L;
    private String[] mobiles;
    private String content;
    private int ctype;
    private List<String> smsids;

    public FaalSendSmsRequest() {
        this.type = 40;
    }

    public String[] getMobiles() {
        return this.mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCtype() {
        return this.ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public List<String> getSmsids() {
        return this.smsids;
    }

    public void setSmsids(List<String> smsids) {
        this.smsids = smsids;
    }
}