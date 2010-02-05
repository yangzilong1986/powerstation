package com.hisun.atmp.handler;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.hisun.pubinterface.IHandler;

import java.util.ArrayList;

public class HiDiaboMessageMatch implements IHandler {
    private ArrayList _codes;
    private String _respProcess;
    private String _reqProcess;

    public HiDiaboMessageMatch() {
        this._codes = new ArrayList();
        this._respProcess = "respProc";
        this._reqProcess = "reqProc";
    }

    public void process(HiMessageContext arg0) throws HiException {
        HiMessage msg = arg0.getCurrentMsg();
        String code = msg.getHeadItem("STC");
        if (this._codes.contains(code)) {
            msg.setHeadItem("SCH", "rp");
            arg0.setProperty("_SUBPROCESS", this._respProcess);
        } else {
            arg0.setProperty("_SUBPROCESS", this._reqProcess);
        }
    }

    public void setCode(String code) {
        this._codes.add(code);
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