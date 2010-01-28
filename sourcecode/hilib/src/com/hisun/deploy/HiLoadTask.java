package com.hisun.deploy;


import com.hisun.exception.HiException;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Task;

public class HiLoadTask extends Task {
    private String name;
    private String mngType;
    private String type;
    private String url;

    public void setUrl(String url) {

        this.url = url;
    }

    public void setMngType(String mngType) {

        this.mngType = mngType;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setType(String type) {

        this.type = type;
    }

    public void execute() {

        if ((StringUtils.isEmpty(this.url)) || (StringUtils.isEmpty(this.name)) || (StringUtils.isEmpty(this.type)) || (StringUtils.isEmpty(this.mngType))) {

            System.out.println("url, name, type, mngType can't be null");

            return;
        }


        String requestUrl = this.url + "?type=" + this.type + "&name=" + this.name + "&method=" + this.mngType;
        try {

            HiLoadHelper.execute(requestUrl);
        } catch (HiException he) {

            System.out.println("Operation [" + this.mngType + "] failure.");

            he.printStackTrace();
        }
    }
}