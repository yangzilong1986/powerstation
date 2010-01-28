package com.hisun.parse8583;


import com.hisun.engine.invoke.impl.HiItemHelper;
import com.hisun.engine.invoke.impl.HiMethodItem;
import com.hisun.exception.HiException;
import com.hisun.framework.event.IServerInitListener;
import com.hisun.framework.event.ServerEvent;
import com.hisun.hilog4j.Logger;
import com.hisun.message.*;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;


public class HiParser8583Handler implements IHandler, IServerInitListener {
    final String C8583_TLV = "Tlv";
    private final String HEADER = "Header";
    private String version;
    private String cfgFile;
    private Element cfgRoot;
    private int[] tlvFldList;
    private String pcfgFile;
    int _offset;
    final Logger log;
    final HiStringManager sm;
    protected HashMap cfgRootMap;


    public HiParser8583Handler() {

        this.C8583_TLV = "Tlv";

        this.HEADER = "Header";


        this.version = "1";

        this.cfgFile = null;

        this.cfgRoot = null;

        this.tlvFldList = null;


        this.pcfgFile = null;

        this._offset = 0;

        this.log = ((Logger) HiContext.getCurrentContext().getProperty("SVR.log"));


        this.sm = HiStringManager.getManager();


        this.cfgRootMap = new HashMap();
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


        if (this.log.isDebugEnabled()) {

            this.log.debug(this.sm.getString("HiParser8583Handler.parser0"));

            this.log.debug(this.sm.getString("HiParser8583Handler.parser1", plainBytes.toString()));


            this.log.debug(this.sm.getString("HiParser8583Handler.parser2", HiConvHelper.bcd2AscStr(plainBytes.getBytes())));

        }


        HiETF etfBody = HiETFFactory.createXmlETF();


        HiByteArrayInputStream in = new HiByteArrayInputStream(plainBytes.getBytes());


        Element headRoot = this.cfgRoot.element("Header");


        if (StringUtils.equals(this.version, "2")) if ((headRoot != null) && (plainBytes.charAt(0) != 48)) {

            convertHeaderToETF(in, etfBody, headRoot);

        } else if (headRoot != null) {

            convertHeaderToETF(in, etfBody, headRoot);

        }


        convertToETF(in, etfBody, this.cfgRoot);


        msg.setBody(etfBody);


        if (this.log.isDebugEnabled()) this.log.debug(this.sm.getString("HiParser8583Handler.parsered"));

    }


    public HiETF convertHeaderToETF(HiByteArrayInputStream in, HiETF etfBody, Element headRoot) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug("Parser 8583 Header－Start");

        }


        String value = makeEtfItem(in, etfBody, 300, headRoot);

        int size = Integer.parseInt(value, 16);


        if (size != 0) {

            size = headRoot.elements().size();

            for (int i = 1; i < size; ++i) {

                makeEtfItem(in, etfBody, 300 + i, headRoot);

            }

        }


        if (this.log.isDebugEnabled()) {

            this.log.debug("Parser 8583 Header－End");

        }

        return etfBody;

    }


    public HiETF convertToETF(HiByteArrayInputStream in, HiETF etfBody, Element cfgRoot) throws HiException {

        makeEtfItem(in, etfBody, 0, cfgRoot);


        String bitMap = getBitMap(in, etfBody, 1);


        for (int i = 1; i < bitMap.length(); ++i) {

            if (bitMap.charAt(i) == '1') {

                makeEtfItem(in, etfBody, i + 1, cfgRoot);

            }

        }

        return etfBody;

    }


    private String getBitMap(HiByteArrayInputStream in, HiETF etfBody, int idx) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug(this.sm.getString("HiParser8583Handler.getBitMap0"));

        }


        Element item = (Element) this.cfgRootMap.get(String.valueOf(idx));

        if (item == null) {

            throw new HiException("231409", String.valueOf(idx));

        }


        byte[] retBytes = new byte[8];

        if (in.read(retBytes, 0, 8) < 8) {

            throw new HiException("231410", "");

        }


        String hexStr = HiConvHelper.bcd2AscStr(retBytes);


        String bitMap = HiConvHelper.hex2Binary(hexStr);


        if (bitMap.charAt(0) == '1') {

            retBytes = new byte[8];

            if (in.read(retBytes, 0, 8) < 8) {

                throw new HiException("231410", "报文长度不足");

            }


            String extHexStr = HiConvHelper.bcd2AscStr(retBytes);


            bitMap = bitMap + HiConvHelper.hex2Binary(extHexStr);

            hexStr = hexStr + extHexStr;

        }


        if (this.log.isInfoEnabled()) {

            this.log.info("<BITMAP> [" + hexStr + "]");

        }

        return bitMap;

    }


    private String makeEtfItem(HiByteArrayInputStream in, HiETF etfBody, int idx, Element cfgRoot) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug(this.sm.getString("HiParser8583Handler.parserItem0", String.valueOf(idx)));

        }


        Element item = (Element) this.cfgRootMap.get(String.valueOf(idx));


        if (item == null) {

            throw new HiException("231409", String.valueOf(idx));

        }


        int length = 0;


        String data_value = "";


        String data_type = item.attributeValue("data_type");

        String length_type = item.attributeValue("length_type");

        if (length_type.equals("CONST")) {

            length = Integer.parseInt(item.attributeValue("length"));

        } else if (length_type.equals("VAR2")) length = getHeadLen(in, 2, item);

        else if (length_type.equals("VAR3")) length = getHeadLen(in, 3, item);

        else {

            throw new HiException("231404", item.attributeValue("field_id"));

        }


        if ((data_type.equals("CharASCII")) || (data_type.equals("NumASCII"))) {

            byte[] retBytes = null;

            retBytes = new byte[length];

            if (in.read(retBytes, 0, length) < length) {

                throw new HiException("231410", "报文长度不足");

            }


            retBytes = specProc(item, retBytes);


            if (StringUtils.equals(item.attributeValue("convert"), "hex")) {

                data_value = HiConvHelper.bcd2AscStr(retBytes);

            } else data_value = new String(retBytes);

        } else if ((data_type.equals("ASCBCD")) || (data_type.equals("NumBCD")) || (data_type.equals("BIT"))) {

            data_value = readBcd(in, length, 2, item);

        } else {

            new HiException("231406", item.attributeValue("field_id"));

        }


        if (ArrayUtils.indexOf(this.tlvFldList, idx) >= 0) {

            parserTlv(etfBody, item, data_value.getBytes(), String.valueOf(idx));

        } else {

            String itemName = item.attributeValue("etf_name");


            etfBody.setGrandChildNode(itemName, data_value);


            if (this.log.isInfoEnabled()) {

                this.log.info("[" + (in.getPos() * 2) + "][" + String.valueOf(idx) + "][" + itemName + "][" + String.valueOf(length) + "][" + data_value + "]");

            }

        }


        return data_value;

    }


    private String readBcd(HiByteArrayInputStream in, int length, int radix, Element itemNode) throws HiException {

        int bcdLen = length / radix;

        int rsvLen = length % radix;

        byte[] retBytes = null;


        if (rsvLen != 0) {

            bcdLen += 1;


            retBytes = new byte[bcdLen];

            if (in.read(retBytes, 0, bcdLen) < bcdLen) {

                throw new HiException("231410", "报文长度不足");

            }


            retBytes = specProc(itemNode, retBytes);


            if ((itemNode != null) && (StringUtils.equals(itemNode.attributeValue("align_mode"), "left"))) {

                return StringUtils.substring(HiConvHelper.bcd2AscStr(retBytes), 0, length);

            }


            return StringUtils.substring(HiConvHelper.bcd2AscStr(retBytes), radix - rsvLen);

        }


        retBytes = new byte[bcdLen];

        if (in.read(retBytes, 0, bcdLen) < bcdLen) {

            throw new HiException("231410", "报文长度不足");

        }


        specProc(itemNode, retBytes);


        return HiConvHelper.bcd2AscStr(retBytes);

    }


    private int getHeadLen(HiByteArrayInputStream in, int varLen, Element itemNode) throws HiException {

        if (StringUtils.equals(itemNode.attributeValue("var_type"), "bin")) {

            return Integer.parseInt(readBcd(in, varLen, 2, null));

        }

        byte[] len = new byte[varLen];

        if (in.read(len, 0, varLen) < varLen) {

            throw new HiException("231410", "解析前置长度,不足");

        }


        return Integer.parseInt(new String(len));

    }


    private void checkCfg(Element cfgRoot, boolean isPutTlvList) throws HiException {

        if ((cfgRoot == null) || (cfgRoot.elements().size() < 129)) {

            throw new HiException("231400", this.cfgFile);

        }


        Element item = cfgRoot.element("Header");

        if (item != null) {

            checkItemCfg(item, false);

        }


        checkItemCfg(cfgRoot, isPutTlvList);


        item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "0");


        if (item == null) {

            throw new HiException("231407", "0");

        }


        item = HiXmlHelper.selectSingleNode(cfgRoot, "Item", "field_id", "1");


        if (item == null) throw new HiException("231407", "1");

    }


    private void checkItemCfg(Element cfgRoot, boolean isPutTlvList) throws HiException {

        Iterator it = cfgRoot.elementIterator("Item");


        int i = 0;


        while (it.hasNext()) {

            int id;

            Element item = (Element) it.next();

            String itemAttr = item.attributeValue("field_id");

            try {

                id = Integer.parseInt(itemAttr);

            } catch (NumberFormatException ne) {

                throw new HiException("231424", String.valueOf(i));

            }


            if ((isPutTlvList) && (item.elements("Tlv").size() > 0)) {

                this.tlvFldList = ArrayUtils.add(this.tlvFldList, id);

            }


            if (StringUtils.isBlank(item.attributeValue("etf_name"))) {

                throw new HiException("231401", item.attributeValue("field_id"));

            }


            itemAttr = item.attributeValue("length_type");


            if (StringUtils.isBlank(itemAttr)) {

                throw new HiException("231402", item.attributeValue("field_id"));

            }


            if (itemAttr.equals("CONST")) {

                String lenAttr = item.attributeValue("length");


                if ((StringUtils.isBlank(lenAttr)) || (!(StringUtils.isNumeric(lenAttr)))) {

                    throw new HiException("231403", item.attributeValue("field_id"));

                }

            } else if ((itemAttr.equals("VAR2")) || (itemAttr.equals("VAR3"))) {

                String len_type = item.attributeValue("var_type");


                if ((!(StringUtils.isEmpty(len_type))) && (!(StringUtils.equals(len_type, "bin"))))
                    if (!(StringUtils.equals(len_type, "char"))) {

                        throw new HiException("231429", item.attributeValue("field_id"));

                    }

            } else {

                throw new HiException("231404", item.attributeValue("field_id"));

            }


            itemAttr = item.attributeValue("data_type");

            if (StringUtils.isBlank(itemAttr)) {

                throw new HiException("231405", item.attributeValue("field_id"));

            }


            if ((!(itemAttr.equals("CharASCII"))) && (!(itemAttr.equals("NumASCII"))) && (!(itemAttr.equals("ASCBCD"))) && (!(itemAttr.equals("NumBCD"))))
                if (!(itemAttr.equals("BIT"))) {

                    throw new HiException("231406", item.attributeValue("field_id"));

                }


            itemAttr = item.attributeValue("align_mode");

            if ((!(StringUtils.isEmpty(itemAttr))) && (!(StringUtils.equals(itemAttr, "left"))))
                if (!(StringUtils.equals(itemAttr, "right"))) {

                    throw new HiException("231428", item.attributeValue("field_id"));

                }


            ++i;

        }

    }


    private void parserTlv(HiETF etfBody, Element itemNode, byte[] dataBytes, String field_id) throws HiException {

        if (dataBytes == null) {

            return;

        }

        if (this.log.isInfoEnabled()) {

            this.log.info(this.sm.getString("HiParser8583Handler.parserTlv0", field_id, new String(dataBytes)));

        }


        HiByteArrayInputStream bIn = new HiByteArrayInputStream(dataBytes);

        while (bIn.available() > 0) {

            parserTlvItem(etfBody, itemNode, bIn);

        }

        bIn = null;


        if (this.log.isInfoEnabled()) this.log.info(this.sm.getString("HiParser8583Handler.parserTlvOk", field_id));

    }


    private void parserTlvItem(HiETF etfBody, Element itemNode, HiByteArrayInputStream bIn) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug("parserTlvItem() start");

        }


        byte[] bytes = new byte[1];

        if (bIn.read(bytes, 0, 1) < 1) {

            return;

        }


        String tag = HiConvHelper.bcd2AscStr(bytes);

        if ((tag.endsWith("F")) || (tag.endsWith("f"))) {

            if (bIn.read(bytes, 0, 1) < 1) {

                throw new HiException("231410", "TLV: 取tag标签有误");

            }


            tag = tag + HiConvHelper.bcd2AscStr(bytes);

        }


        if (this.log.isInfoEnabled()) {

            this.log.info("Tlv: tag=[" + tag + "]");

        }


        int length = 0;

        if (bIn.read(bytes, 0, 1) < 1) {

            throw new HiException("231410", "TLV: 取子域长度有误");

        }


        if (bytes[0] >= 0) {

            length = bytes[0];

        } else {

            int preLen = 0;

            if (bytes[0] == -127) {

                preLen = 1;

            } else if (bytes[0] == -126) {

                preLen = 2;

            } else throw new HiException("231426", String.valueOf(bytes[0]));


            for (int i = 0; i < preLen; ++i) {

                int len = bIn.read();

                if (len == -1) {

                    throw new HiException("231410", "TLV: 取子域长度有误");

                }


                length = length * 256 + len;

            }


        }


        if (length == 0) {

            return;

        }


        if (this.log.isDebugEnabled()) {

            this.log.debug("Tlv: length=[" + length + "]");

        }


        bytes = new byte[length];

        if (bIn.read(bytes, 0, length) < length) {

            throw new HiException("231410", "TLV: 取子域值有误, 需长度[" + length + "]");

        }


        Element tlvNode = HiXmlHelper.selectSingleNode(itemNode, "Tlv", "tag", tag);


        if (tlvNode == null) {

            throw new HiException("231427", itemNode.attributeValue("field_id"), tag);

        }


        String data_type = tlvNode.attributeValue("data_type");


        String data_value = "";


        if ((data_type.equals("CharASCII")) || (data_type.equals("NumASCII"))) {

            data_value = new String(bytes);

        } else if ((data_type.equals("BIT")) || (data_type.equals("ASCBCD")) || (data_type.equals("NumBCD"))) {

            data_value = HiConvHelper.bcd2AscStr(bytes);


            length *= 2;

        } else {

            new HiException("231425", tag);

        }


        String itemName = tlvNode.attributeValue("etf_name");


        etfBody.setGrandChildNode(itemName, data_value);


        if (this.log.isInfoEnabled())

            this.log.info(this.sm.getString("HiParser8583Handler.parserTlv1", tag, itemName, String.valueOf(length), data_value));

    }


    public void serverInit(ServerEvent arg0) throws HiException {

        if (this.log.isDebugEnabled()) {

            this.log.debug(this.sm.getString("HiParser8583Handler.init1", this.cfgFile));

        }


        Element pcfgRoot = null;


        URL fileUrl = HiResource.getResource(this.cfgFile);

        SAXReader saxReader = new SAXReader();

        try {

            this.cfgRoot = saxReader.read(fileUrl).getRootElement();

            cacheItem(this.cfgRoot);


            if (!(StringUtils.isEmpty(this.pcfgFile))) {

                fileUrl = HiResource.getResource(this.pcfgFile);


                pcfgRoot = saxReader.read(fileUrl).getRootElement();

            }

        } catch (DocumentException e) {

            throw new HiException("213319", fileUrl.getFile(), e);

        }


        checkCfg(this.cfgRoot, true);


        if (pcfgRoot != null) {

            checkCfg(pcfgRoot, false);


            HiContext.getCurrentContext().setProperty("8583_CFG_NODE", pcfgRoot);

        } else {

            HiContext.getCurrentContext().setProperty("8583_CFG_NODE", this.cfgRoot);

        }


        if (this.log.isDebugEnabled()) this.log.debug(this.sm.getString("HiParser8583Handler.init2", this.cfgFile));

    }


    public String getVersion() {

        return this.version;

    }


    public void setVersion(String version) {

        this.version = version;

    }


    public byte[] specProc(Element element, byte[] values) throws HiException {

        if (element == null) return values;

        String pro_dll1 = element.attributeValue("pro_dll");

        String pro_func1 = element.attributeValue("pro_func");


        if ((StringUtils.isNotBlank(pro_dll1)) && (StringUtils.isNotBlank(pro_func1))) {

            HiMethodItem pro_method1 = HiItemHelper.getMethod(pro_dll1, pro_func1);


            values = HiItemHelper.execMethod(pro_method1, values, this.log);

        }

        return values;

    }


    public void cacheItem(Element root) {

        Iterator iter = root.elementIterator();

        while (iter.hasNext()) {

            Element item = (Element) iter.next();

            this.cfgRootMap.put(item.attributeValue("field_id"), item);

        }


        Element header = root.element("Header");

        if (header == null) {

            return;

        }


        iter = header.elementIterator();

        while (iter.hasNext()) {

            Element item = (Element) iter.next();

            this.cfgRootMap.put(item.attributeValue("field_id"), item);

        }

    }

}