package com.hzjbbis.fk.model;

import java.util.ArrayList;
import java.util.List;

public class RtuAlertCode {
    private String code;
    private List<String> args = new ArrayList();

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getArgs() {
        return this.args;
    }

    public void setArgs(List<String> args) {
        if (args != null) this.args = args;
    }
}