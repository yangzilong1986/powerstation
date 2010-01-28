package com.hisun.mon.handler;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.mon.HiRunStatInfoPool;
import com.hisun.pubinterface.IHandler;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

public class HiTransStatHandler implements IHandler {
    private Logger log;
    private String[] fields;

    public HiTransStatHandler() {

        this.log = null;
    }

    public void process(HiMessageContext arg0) throws HiException {

        HashMap map = new HashMap();

        HiMessage msg = arg0.getCurrentMsg();

        HiETF root = msg.getETFBody();

        for (int i = 0; (this.fields != null) && (i < this.fields.length); ++i) {

            String tmp = root.getGrandChildValue(this.fields[i]);

            if (StringUtils.isBlank(tmp)) {
                continue;
            }

            map.put(this.fields[i], tmp);
        }

        long stm = ((Long) msg.getObjectHeadItem("STM")).longValue();

        int elapseTime = (int) (System.currentTimeMillis() - stm);

        String msgTyp = root.getChildValue("MSG_TYP");

        String rspCd = root.getChildValue("RSP_CD");

        String rspMsg = root.getChildValue("RSP_MSG");

        String code = msg.getHeadItem("STC");


        HiRunStatInfoPool.getInstance().once(msg.getRequestId(), code, elapseTime, System.currentTimeMillis(), msgTyp, rspCd, rspMsg, map);
    }

    public void setFields(String fields) {

        this.fields = fields.split("\\|");
    }

    public String getFields() {

        return "";
    }
}