package com.hisun.deploy;

import com.hisun.exception.HiException;
import org.apache.tools.ant.Task;

public class HiDeploymentTask extends Task {
    String type;
    String serviceName;
    String destPath;
    boolean reload;

    public HiDeploymentTask() {
        this.destPath = "./";

        this.reload = false;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
        if ((this.destPath == null) || (this.destPath.endsWith("/"))) return;
        this.destPath += "/";
    }

    public void setReload(String reload) {
        if (reload != "true") return;
        this.reload = true;
    }

    public void execute() {
        if ((this.serviceName == null) || (this.serviceName == "")) {
            System.out.println("HiDeploymentTask: seriveName is null, failure!");
            return;
        }

        try {
            HiDeploymentHelper.init(this.type, this.reload);
            HiDeploymentHelper.createDescriptor(this.serviceName, this.destPath);
        } catch (HiException e) {
            e.printStackTrace();
        } catch (Exception t) {
            System.out.println("error ...");
            t.printStackTrace();
        }
    }
}