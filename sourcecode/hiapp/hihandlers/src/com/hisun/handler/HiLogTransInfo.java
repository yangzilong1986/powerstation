package com.hisun.handler;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerDestroyListener;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiByteBuffer;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiResource;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HiLogTransInfo implements IHandler, IServerInitListener, IServerDestroyListener {
    private String _fmtFile;
    private ArrayList _fmtInfos;
    private String _logFile;
    private Logger log;
    private static HiStringManager sm = HiStringManager.getManager();
    private StringBuffer _headInfo;
    private Logger log1;

    public HiLogTransInfo() {
        this._fmtFile = "etc/TXNTRC_FMT.XML";
        this._fmtInfos = new ArrayList();
        this._logFile = "TXNTRC.lst";
        this.log = null;

        this._headInfo = new StringBuffer();
        this.log1 = null;
    }

    public void process(HiMessageContext arg0) throws HiException {
        HiETF root = arg0.getCurrentMsg().getETFBody();
        try {
            HiByteBuffer buf = new HiByteBuffer(100);
            for (int i = 0; i < this._fmtInfos.size(); ++i) {
                HiLogFmtInfo info = (HiLogFmtInfo) this._fmtInfos.get(i);
                String value = getValue(arg0, info.name);
                if (value == null) value = "";
                buf.append(StringUtils.rightPad(value, info.length));
                buf.append(info.deli);
            }
            buf.append(SystemUtils.LINE_SEPARATOR);
            this.log1.info(buf);
        } catch (Throwable e) {
        }
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        Document doc;
        HiLogFmtInfo info;
        URL url = HiResource.getResource(this._fmtFile);
        if (url == null) {
            throw new HiException("212004", this._fmtFile);
        }
        SAXReader reader = new SAXReader();
        try {
            doc = reader.read(url);
        } catch (DocumentException e) {
            throw new HiException("212005", this._fmtFile, e);
        }
        Element root = doc.getRootElement();
        root = root.element("Format");
        List items = root.elements("Item");
        for (int i = 0; i < items.size(); ++i) {
            Element node = (Element) items.get(i);
            info = new HiLogFmtInfo();
            info.name = node.attributeValue("name");
            if (info.name == null) continue;
            info.desc = node.attributeValue("desc");
            if (info.desc == null) {
                info.desc = info.name;
            }
            info.length = NumberUtils.toInt(node.attributeValue("length"));
            if (info.length < 0) {
                info.length = 0;
            }
            info.deli = (char) NumberUtils.toInt(node.attributeValue("deli"));
            this._fmtInfos.add(info);
        }

        FileWriter _fw = null;
        try {
            _fw = new FileWriter(HiICSProperty.getTrcDir() + this._logFile, true);
            this._headInfo.append('#');
            for (int i = 0; i < this._fmtInfos.size(); ++i) {
                info = (HiLogFmtInfo) this._fmtInfos.get(i);
                this._headInfo.append(StringUtils.rightPad(info.desc, info.length));
                this._headInfo.append(info.deli);
            }
            this._headInfo.append(SystemUtils.LINE_SEPARATOR);
            _fw.write(this._headInfo.toString());
            _fw.close();
        } catch (Throwable e) {
            if (_fw != null) try {
                _fw.close();
            } catch (IOException e1) {
            }
            throw new HiException("220079", HiICSProperty.getTrcDir() + this._logFile, e);
        }
        this.log = arg0.getLog();
        this.log1 = Logger.getLogger(this._logFile);
        this.log1.setHasOfHead(false);
    }

    public String getFmtFile() {
        return this._fmtFile;
    }

    public void setFmtFile(String fmtFile) {
        this._fmtFile = fmtFile;
    }

    public String getLogFile() {
        return this._logFile;
    }

    public void setLogFile(String file) {
        this._logFile = file;
    }

    public void serverDestroy(ServerEvent arg0) throws HiException {
    }

    private String getValue(HiMessageContext ctx, String name) {
        HiMessage msg = ctx.getCurrentMsg();
        HiETF etf = msg.getETFBody();

        if (!(name.startsWith("@"))) {
            return name;
        }

        int idx = name.indexOf(46);
        if (idx == -1) {
            return name;
        }
        String id = name.substring(0, idx);
        String key = name.substring(idx + 1);
        if ("@MSG".equalsIgnoreCase(id)) {
            Long l;
            if ("SID".equalsIgnoreCase(key)) return msg.getRequestId();
            if ("ECT".equalsIgnoreCase(key)) return msg.getType();
            if ("ETM".equalsIgnoreCase(key)) {
                l = (Long) msg.getObjectHeadItem(key);
                if (l == null) return null;
                return DateFormatUtils.format(l.longValue(), "hh:mm:ss");
            }
            if ("STM".equalsIgnoreCase(key)) {
                l = (Long) msg.getObjectHeadItem(key);
                if (l == null) return null;
                return DateFormatUtils.format(l.longValue(), "hh:mm:ss");
            }
            Object o = msg.getObjectHeadItem(key);
            if (o == null) return null;
            return o.toString();
        }

        if ("@ETF".equalsIgnoreCase(id)) {
            return etf.getChildValue(key);
        }
        return ctx.getStrProp(key);
    }
}