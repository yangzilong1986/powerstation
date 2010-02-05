package com.hisun.ibs.comm;

import COM.ibm.eNetwork.cpic.*;

public class HiCPICComm {
    private String _destName;
    private String _tpName;
    private byte[] conversationId;
    private CPIC _cpic;
    private boolean _confirm;
    private int _flag;

    public HiCPICComm() {
        this.conversationId = new byte[8];
        this._cpic = new CPIC();
        this._confirm = false;
        this._flag = 0;
    }

    public int appcComm(String destName, String tpName, byte[] sendData, int sendLen, byte[] recvBuf) throws CPICReturnCode {
        this._destName = destName;
        this._tpName = tpName;
        this._flag = -1;

        appcInit();

        appcAllocate();

        this._flag = -2;
        appcSend(sendData, sendLen);
        this._flag = -3;

        int len = appcRecv(recvBuf);
        if (this._confirm) {
            appcConfirm();
        }
        appcEnd();
        return len;
    }

    public int appcComm(String destName, String tpName, byte[] sendData, byte[] recvBuf) throws CPICReturnCode {
        this._destName = destName;
        this._tpName = tpName;
        this._flag = 0;

        appcInit();

        appcAllocate();

        this._flag = -1;
        appcSend(sendData);
        this._flag = -2;

        int len = appcRecv(recvBuf);
        if (this._confirm) {
            appcConfirm();
        }
        appcEnd();
        return len;
    }

    private void appcInit() throws CPICReturnCode {
        CPICReturnCode cmRetCode = new CPICReturnCode(0);

        this._cpic.cminit(this.conversationId, this._destName, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }

        CPICConversationState cmState = new CPICConversationState();
        this._cpic.cmecs(this.conversationId, cmState, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }
        if (!(cmState.equals(2))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }

        if (this._tpName != null) {
            CPICLength length = new CPICLength(this._tpName.length());
            this._cpic.cmstpn(this.conversationId, this._tpName.getBytes(), length, cmRetCode);
            if (!(cmRetCode.equals(0))) {
                throw new CPICReturnCode(cmRetCode.intValue());
            }
        }

        CPICSyncLevel cmSyncLevel = new CPICSyncLevel(1);
        this._cpic.cmssl(this.conversationId, cmSyncLevel, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }

        CPICDeallocateType cmDealType = new CPICDeallocateType(3);

        this._cpic.cmsdt(this.conversationId, cmDealType, cmRetCode);
        if (!(cmRetCode.equals(0))) throw new CPICReturnCode(cmRetCode.intValue());
    }

    private void appcAllocate() throws CPICReturnCode {
        CPICReturnCode cmRetCode = new CPICReturnCode(0);
        CPICConversationState cmState = new CPICConversationState();

        this._cpic.cmallc(this.conversationId, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }

        this._cpic.cmecs(this.conversationId, cmState, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }
        if (!(cmState.equals(3))) throw new CPICReturnCode(cmRetCode.intValue());
    }

    private void appcSend(byte[] data, int len) throws CPICReturnCode {
        CPICReturnCode cmRetCode = new CPICReturnCode(0);
        CPICLength length = new CPICLength(len);
        CPICControlInformationReceived cmRequestToSendReceived = new CPICControlInformationReceived();
        this._cpic.cmsend(this.conversationId, data, length, cmRequestToSendReceived, cmRetCode);

        if (!(cmRetCode.equals(0))) throw new CPICReturnCode(cmRetCode.intValue());
    }

    private void appcSend(byte[] data) throws CPICReturnCode {
        appcSend(data, data.length);
    }

    private int appcRecv(byte[] buffer) throws CPICReturnCode {
        CPICReturnCode cmRetCode = new CPICReturnCode(0);

        this._cpic.cmptr(this.conversationId, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }

        CPICConversationState cmState = new CPICConversationState();
        this._cpic.cmecs(this.conversationId, cmState, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }
        if (!(cmState.equals(4))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }

        CPICReceiveType cmRecvType = new CPICReceiveType(0);

        this._cpic.cmsrt(this.conversationId, cmRecvType, cmRetCode);
        if (!(cmRetCode.equals(0))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }

        CPICDataReceivedType cmDataRecvType = new CPICDataReceivedType();
        CPICStatusReceived cmStatusRecv = new CPICStatusReceived();
        CPICControlInformationReceived cmRequestToSendReceived = new CPICControlInformationReceived();
        CPICLength cmRequestLen = new CPICLength(buffer.length);
        CPICLength cmRecvLen = new CPICLength();

        this._cpic.cmrcv(this.conversationId, buffer, cmRequestLen, cmDataRecvType, cmRecvLen, cmStatusRecv, cmRequestToSendReceived, cmRetCode);

        if ((!(cmRetCode.equals(0))) && (!(cmRetCode.equals(18)))) {
            throw new CPICReturnCode(cmRetCode.intValue());
        }
        if ((cmStatusRecv.equals(4)) || (cmStatusRecv.equals(3))) {
            this._confirm = true;
        }
        return cmRecvLen.intValue();
    }

    private void appcConfirm() throws CPICReturnCode {
        CPICReturnCode cmRetCode = new CPICReturnCode(0);
        this._cpic.cmcfmd(this.conversationId, cmRetCode);
        if (!(cmRetCode.equals(0))) throw new CPICReturnCode(cmRetCode.intValue());
    }

    private void appcEnd() throws CPICReturnCode {
        CPICReturnCode cmRetCode = new CPICReturnCode(0);
        this._cpic.cmdeal(this.conversationId, cmRetCode);
    }

    public int getFlag() {
        return this._flag;
    }

    public void setFlag(int flag) {
        this._flag = flag;
    }
}