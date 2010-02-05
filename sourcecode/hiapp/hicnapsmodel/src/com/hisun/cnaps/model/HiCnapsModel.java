package com.hisun.cnaps.model;

import com.hisun.cnaps.HiCnapsCodeTable;
import com.hisun.cnaps.messages.HiCnapsMessage;
import com.hisun.cnaps.messages.HiCnapsMessageArea;
import com.hisun.engine.HiEngineModel;
import com.hisun.engine.invoke.impl.HiItemHelper;
import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.message.*;
import com.hisun.util.HiByteBuffer;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;

import java.util.StringTokenizer;

public class HiCnapsModel extends HiEngineModel {
    static final String NAME = "Tag";
    private String mustField;
    private String optFiled;
    private HiCnapsCodeTable codeTable;
    final Logger log = (Logger) HiContext.getCurrentContext().getProperty("SVR.log");

    final HiStringManager sm = HiStringManager.getManager();

    public String getNodeName() {
        return "Tag";
    }

    public void process(HiMessageContext ctx) throws HiException {
        HiMessage msg = ctx.getCurrentMsg();
        StringTokenizer m_st = new StringTokenizer(this.mustField, "\\|");
        String[] packFields = new String[m_st.countTokens()];
        String[] optFileds = null;
        for (int i = 0; m_st.hasMoreTokens(); ++i)
            packFields[i] = m_st.nextToken();
        if (StringUtils.isNotBlank(this.optFiled)) {
            StringTokenizer o_st = new StringTokenizer(this.optFiled, "\\|");
            for (int p = 0; o_st.hasMoreTokens(); ++p) {
                optFileds[p] = o_st.nextToken();
            }
        }
        if (!(HiMessageHelper.isInnerMessage(msg))) {
            String plianText = HiItemHelper.getPlainText(msg).toString();
            if (this.log.isInfoEnabled()) {
                this.log.info(this.sm.getString("HiCnapsHandler.unpack"));
            }
            HiCnapsMessage cnapsMessage = (HiCnapsMessage) HiContext.getCurrentContext().getProperty("cnaps_message");

            int batchNo = 1;
            if (cnapsMessage == null) {
                cnapsMessage = new HiCnapsMessage(this.codeTable);
                cnapsMessage.unpack(plianText);
            } else {
                batchNo = ((Integer) HiContext.getCurrentContext().getProperty("cnpas_batch")).intValue() + 1;
            }

            HiCnapsMessageArea body = cnapsMessage.getMessageBusArea(batchNo);
            if (body == null) {
                throw new HiException("241102");
            }
            HiETF etf = getCurrLevelETF(msg);
            body.unpack2Etf(packFields, etf, true);
            if (optFileds != null) {
                body.unpack2Etf(optFileds, etf, false);
            }

            HiContext.getCurrentContext().setProperty("cnpas_batch", new Integer(batchNo));
        } else {
            if (this.log.isInfoEnabled()) this.log.info(this.sm.getString("HiCnapsHandler.unpack"));
            HiCnapsMessage cnapsMessage = (HiCnapsMessage) HiContext.getCurrentContext().getProperty("cnaps_message");

            if (cnapsMessage == null) {
                cnapsMessage = new HiCnapsMessage(this.codeTable);
            }
            HiETF etf = getCurrLevelETF(msg);
            HiCnapsMessageArea area = cnapsMessage.createMessageBodyArea(packFields, optFileds, etf);

            HiItemHelper.getPlainText(msg).append(area.getString());
            HiContext.getCurrentContext().setProperty("cnaps_message", cnapsMessage);
        }
    }

    public HiETF getCurrLevelETF(HiMessage msg) {
        String currLevel = HiItemHelper.getCurEtfLevel(msg);
        if (StringUtils.isEmpty(currLevel)) {
            return msg.getETFBody();
        }
        return msg.getETFBody().getGrandChildNode(currLevel.substring(0, currLevel.length() - 1));
    }

    public void initCodeTable(HiCnapsCodeTable codeTable) {
        this.codeTable = codeTable;
    }

    public void setMust_fields(String fields) {
        this.mustField = fields;
    }

    public void setOpt_fileds(String fields) {
        this.optFiled = fields;
    }

    public void loadAfter() throws HiException {
        if (this.log.isInfoEnabled()) this.log.info(this.sm.getString("HiCnapsModell.loadAfter"));
        if (StringUtils.isBlank(this.mustField)) {
            throw new HiException("241097", "");
        }
        this.codeTable = ((HiCnapsCodeTable) HiContext.getCurrentContext().getProperty("cnaps_code"));
    }

    public static void main(String[] args1) {
    }
}