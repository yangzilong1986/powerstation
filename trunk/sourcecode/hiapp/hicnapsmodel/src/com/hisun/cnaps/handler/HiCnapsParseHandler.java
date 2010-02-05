package com.hisun.cnaps.handler;

import com.hisun.cnaps.HiCnapsCodeTable;
import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiResource;
import com.hisun.util.HiStringManager;
import org.dom4j.DocumentException;

import java.io.File;
import java.net.URL;

public class HiCnapsParseHandler implements IHandler, IServerInitListener {
    final Logger log = (Logger) HiContext.getCurrentContext().getProperty("SVR.log");
    HiCnapsCodeTable codeTable;
    private String cfgFile;
    final HiStringManager sm = HiStringManager.getManager();

    public HiCnapsParseHandler() {
        this.cfgFile = null;
    }

    public void process(HiMessageContext context) throws HiException {
        HiMessage msg = context.getCurrentMsg();
        this.log.info(this.sm.getString("HiCnapsHandler.process"));
    }

    public void setCFG(String cfgFile) {
        if (this.log.isInfoEnabled()) this.log.info(this.sm.getString("HiCnapsHandler.cfgFile"), cfgFile);
        this.cfgFile = cfgFile;
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        if (this.log.isInfoEnabled()) this.log.info("HiCnapsHandler.init");
        URL fileUrl = HiResource.getResource(this.cfgFile);
        if (this.log.isInfoEnabled()) {
            this.log.info(this.sm.getString("HiCnapsHandler.cfgFile"), fileUrl.toString());
            this.log.info("HiCnapsHandler.init.after");
        }
        if (fileUrl != null) {
            try {
                this.codeTable = HiCnapsCodeTable.load(fileUrl);
            } catch (DocumentException e) {
                throw new HiException("213319", new File(this.cfgFile).getAbsolutePath(), e);
            }
        }

        throw new HiException("213302", new File(this.cfgFile).getAbsolutePath());

        arg0.getServerContext().setProperty("cnaps_code", this.codeTable);
    }

    public HiCnapsCodeTable getCodeTable() {
        return this.codeTable;
    }

    public static void main(String[] args) throws HiException {
    }
}