package com.hisun.atmp.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;

public class Hi8583MessageMatch implements IHandler {
    private String _respProcess;
    private String _reqProcess;

    public Hi8583MessageMatch() {
        this._respProcess = "respProc";
        this._reqProcess = "reqProc";
    }

    public void process(HiMessageContext arg0) throws HiException {
        HiMessage msg = arg0.getCurrentMsg();
        HiETF root = msg.getETFBody();
        String msgID = root.getChildValue("MsgID");

        if (msgID.charAt(2) % '\2' == 1) {
            msg.setHeadItem("SCH", "rp");
            arg0.setProperty("_SUBPROCESS", this._respProcess);
        } else {
            msg.setHeadItem("SCH", "rq");
            arg0.setProperty("_SUBPROCESS", this._reqProcess);
        }
    }

    public String getRespProcess() {
        return this._respProcess;
    }

    public void setRespProcess(String respProcess) {
        this._respProcess = respProcess;
    }

    public String getReqProcess() {
        return this._reqProcess;
    }

    public void setReqProcess(String reqProcess) {
        this._reqProcess = reqProcess;
    }
}