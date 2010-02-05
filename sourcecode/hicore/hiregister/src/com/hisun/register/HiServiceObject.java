package com.hisun.register;

import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class HiServiceObject implements Serializable {
    private static final long serialVersionUID = -4493525725105236495L;
    private String serviceCode;
    private String appCode;
    private String appName;
    private String monSwitch = "0";
    private String serverName;
    private String serverType;
    private String logLevel = "0";

    private String running = "1";

    private HashMap extInfo = null;
    private String time;
    private String desc;
    private String bindType;
    private HiBind bind;

    public HiServiceObject(String serviceCode) {
        this.serviceCode = serviceCode;

        if (HiICSProperty.containsProperty("framework")) this.bindType = HiICSProperty.getProperty("framework");
        else this.bindType = "POJO";
    }

    public HiServiceObject(String serviceCode, String bindType) {
        this.serviceCode = serviceCode;
        this.bindType = bindType;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getExtValue(Object name) {
        return ((String) getExtObject(name));
    }

    public Object getExtObject(Object name) {
        if (this.extInfo == null) {
            return null;
        }
        return this.extInfo.get(name);
    }

    public void setExtInfo(Object name, Object value) {
        if (this.extInfo == null) {
            this.extInfo = new HashMap();
        }
        this.extInfo.put(name, value);
    }

    public void setExtInfo(HashMap extInfo) {
        this.extInfo = extInfo;
    }

    public String getBindType() {
        return this.bindType;
    }

    public String getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(String logLevel) {
        if (logLevel == null) this.logLevel = "";
        else this.logLevel = logLevel;
    }

    public boolean isMonSwitch() {
        return StringUtils.equals(this.monSwitch, "1");
    }

    public String getMonSwitch() {
        return this.monSwitch;
    }

    public void setMonSwitch(String monSwitch) {
        this.monSwitch = monSwitch;
    }

    public boolean isRunning() {
        return StringUtils.equals(this.running, "1");
    }

    public String getRunning() {
        return this.running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public String getServiceCode() {
        return this.serviceCode;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerType() {
        return this.serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public HiBind getBind() {
        return this.bind;
    }

    public void setBind(HiBind bind) {
        this.bind = bind;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.bindType);
        sb.append(":");
        sb.append(this.serviceCode);
        sb.append(":");
        sb.append(this.appCode);
        sb.append(":");
        sb.append(this.appName);
        sb.append(":");
        sb.append(this.serverName);
        sb.append(":");
        sb.append(this.serverType);
        sb.append(":");
        sb.append(this.monSwitch);
        sb.append(":");
        sb.append(this.logLevel);
        sb.append(":");
        sb.append(this.running);
        sb.append(":");
        sb.append(this.time);
        sb.append(":");
        sb.append(this.desc);
        sb.append(":");
        if (this.extInfo != null) {
            Collection c = this.extInfo.entrySet();
            Iterator iter = c.iterator();
            while (iter.hasNext()) {
                sb.append(iter.next());
                sb.append(":");
            }
        }
        if (this.bind != null) {
            sb.append(this.bind.toString());
        }
        return sb.toString();
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setTime() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
        this.time = df.format(new Date());
    }

    public String getTime() {
        return this.time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}