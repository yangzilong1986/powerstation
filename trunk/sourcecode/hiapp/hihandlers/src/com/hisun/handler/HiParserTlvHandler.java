package com.hisun.handler;

import com.hisun.cfg.HiLoadTlvCfg;
import com.hisun.cfg.HiTlvHelper;
import com.hisun.cfg.HiTlvItem;
import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.Logger;
import com.hisun.message.*;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;
import com.hisun.util.HiResource;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HiParserTlvHandler implements IHandler, IServerInitListener {
    private String cfgFile;
    private String pcfgFile;
    private Map cfgMap;
    private HiTlvItem rootTlv;
    final Logger log;
    final HiStringManager sm;

    public HiParserTlvHandler() {
        this.cfgFile = null;

        this.pcfgFile = null;

        this.cfgMap = new HashMap();
        this.rootTlv = new HiTlvItem();

        this.log = ((Logger) HiContext.getCurrentContext().getProperty("SVR.log"));

        this.sm = HiStringManager.getManager();
    }

    public void setCFG(String cfgFile) {
        this.cfgFile = cfgFile;
    }

    public void setPCFG(String pcfgFile) {
        this.pcfgFile = pcfgFile;
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        HiByteBuffer plainBytes = (HiByteBuffer) msg.getBody();

        if (this.log.isInfoEnabled()) {
            this.log.info(this.sm.getString("HiParserTlvHandler.parser0"));
            this.log.info(this.sm.getString("HiParserTlvHandler.parser1", plainBytes.toString()));
        }

        HiETF etfBody = HiETFFactory.createXmlETF();

        ByteArrayInputStream in = new ByteArrayInputStream(plainBytes.getBytes());

        convertToETF(ctx, in, etfBody, this.cfgMap);

        msg.setBody(etfBody);

        if (!(this.log.isInfoEnabled())) return;
        this.log.info(this.sm.getString("HiParserTlvHandler.parsered"));
    }

    private void convertToETF(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, Map cfgMap) throws HiException {
        byte[] retBytes = null;
        retBytes = new byte[this.rootTlv.tag_len];

        while ((ret = in.read(retBytes, 0, this.rootTlv.tag_len)) != -1) {
            int ret;
            if (ret < this.rootTlv.tag_len) {
                throw new HiException("231506", String.valueOf(this.rootTlv.tag_len));
            }

            makeEtfItem(ctx, in, etfBody, HiTlvHelper.getTag(retBytes, this.rootTlv.tag_type), cfgMap);
        }
    }

    private void makeEtfItem(HiMessageContext ctx, ByteArrayInputStream in, HiETF etfBody, String tag, Map cfgMap) throws HiException {
        if (this.log.isInfoEnabled()) {
            this.log.info(this.sm.getString("HiParserTlvHandler.parserItem0", tag));
        }

        HiTlvItem item = (HiTlvItem) cfgMap.get(tag);
        if (item == null) {
            throw new HiException("231507", tag);
        }

        item.unPack(ctx, cfgMap, in, etfBody, this.log);
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        if (this.log.isInfoEnabled()) {
            this.log.info(this.sm.getString("HiParserTlvHandler.init1", this.cfgFile));
        }

        Element pcfgRoot = null;
        Element cfgRoot = null;
        URL fileUrl = HiResource.getResource(this.cfgFile);
        SAXReader saxReader = new SAXReader();
        try {
            cfgRoot = saxReader.read(fileUrl).getRootElement();

            if (!(StringUtils.isEmpty(this.pcfgFile))) {
                fileUrl = HiResource.getResource(this.pcfgFile);

                pcfgRoot = saxReader.read(fileUrl).getRootElement();
            }
        } catch (DocumentException e) {
            throw new HiException("213319", fileUrl.getFile(), e);
        }

        HiLoadTlvCfg.load(cfgRoot, this.cfgMap, this.rootTlv);

        if (this.rootTlv.tag_len <= 0) {
            throw new HiException("231504", "tag_len");
        }

        if (pcfgRoot != null) {
            Map pcfgMap = new HashMap();
            HiTlvItem prootTlv = new HiTlvItem();

            HiLoadTlvCfg.load(pcfgRoot, pcfgMap, prootTlv);

            HiContext.getCurrentContext().setProperty("TLV_CFG_NODE", pcfgMap);
        } else {
            HiContext.getCurrentContext().setProperty("TLV_CFG_NODE", this.cfgMap);
        }

        if (this.log.isInfoEnabled()) this.log.info(this.sm.getString("HiParserTlvHandler.init2", this.cfgFile));
    }
}