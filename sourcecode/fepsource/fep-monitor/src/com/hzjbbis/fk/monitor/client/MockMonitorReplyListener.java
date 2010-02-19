package com.hzjbbis.fk.monitor.client;

import org.apache.log4j.Logger;

public class MockMonitorReplyListener implements IMonitorReplyListener {
    private static final Logger log = Logger.getLogger(MockMonitorReplyListener.class);

    public void onClose() {
        log.debug("监控服务连接关闭。");
    }

    public void onConnect() {
        log.debug("监控服务连接成功。");
    }

    public void onEventHookProfile(String profile) {
        log.info(profile);
    }

    public void onGetFile() {
        log.info("下载文件成功");
    }

    public void onListConfig(String result) {
        log.info(result);
    }

    public void onListLog(String result) {
        log.info(result);
    }

    public void onMultiSysProfile(String profile) {
        log.info(profile);
    }

    public void onModuleProfile(String profile) {
        log.info(profile);
    }

    public void onPutFile() {
        log.info("上传文件成功");
    }

    public void onReplyFailed(String reason) {
        log.info(reason);
    }

    public void onReplyOK() {
        log.info("成功");
    }

    public void onSystemProfile(String profile) {
        log.info(profile);
    }

    public void onRtuMessageInd(String ind) {
        log.info(ind);
    }
}