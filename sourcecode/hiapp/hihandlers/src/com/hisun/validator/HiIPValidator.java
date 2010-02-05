package com.hisun.validator;

import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerDestroyListener;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiResource;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;

public class HiIPValidator implements IHandler, IServerInitListener, IServerDestroyListener {
    private ArrayList _fileList;
    private ArrayList _nodeList;

    public HiIPValidator() {
        this._fileList = new ArrayList();

        this._nodeList = new ArrayList();
    }

    public void setFile(String file) {
        this._fileList.add(file);
    }

    public boolean validate(HiMessageContext ctx) {
        Element node = null;
        String srcIp = ctx.getCurrentMsg().getHeadItem("SIP");
        if (StringUtils.isEmpty(srcIp)) {
            return true;
        }
        for (int i = 0; i < this._nodeList.size(); ++i) {
            node = (Element) this._nodeList.get(i);
            if (StringUtils.equals(node.attributeValue("ip"), srcIp)) {
                return true;
            }
        }
        return false;
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        SAXReader reader = new SAXReader();

        for (int i = 0; i < this._fileList.size(); ++i) {
            String file = (String) this._fileList.get(i);
            try {
                InputStream is = HiResource.getResourceAsStream(file);
                if (is == null) throw new HiException("212004", file);
                Document doc = reader.read(is);
                this._nodeList.add(doc.getRootElement());
            } catch (DocumentException e) {
                throw new HiException("340001", file, e.getMessage());
            }
        }
    }

    public void serverDestroy(ServerEvent arg0) throws HiException {
        this._fileList.clear();
        this._fileList = null;
        this._nodeList.clear();
        this._nodeList = null;
    }

    public void process(HiMessageContext arg0) throws HiException {
    }
}