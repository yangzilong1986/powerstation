package com.hisun.bank.component;

import com.EasyLink.OpenVendorV34.NetTran;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;

public class NetGnetePaySign {
    private Logger log1;

    public NetGnetePaySign() {
        this.log1 = HiLog.getLogger("callproc.trc");
    }

    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        try {
            String MerId = args.get("MerId");
            String OrderNo = args.get("OrderNo");
            String OrderAmount = args.get("OrderAmount");
            String CallBackUrl = args.get("CallBackUrl");
            String BankCode = args.get("BankCode");
            String SendCertFile = args.get("SendCertFile");
            String RcvCertFile = args.get("RcvCertFile");
            String Password = args.get("Password");

            String EncryptedMsg = args.get("EncryptedMsg");
            String SignedMsg = args.get("SignedMsg");

            this.log1.info("MerId=" + MerId);
            this.log1.info("OrderNo=" + OrderNo);
            this.log1.info("OrderAmount=" + OrderAmount);
            this.log1.info("CallBackUrl=" + CallBackUrl);
            this.log1.info("BankCode=" + BankCode);
            this.log1.info("SendCertFile=" + SendCertFile);
            this.log1.info("RcvCertFile=" + RcvCertFile);
            this.log1.info("Password=" + Password);
            this.log1.info("EncryptedMsg=" + EncryptedMsg);
            this.log1.info("SignedMsg=" + SignedMsg);

            String EncryptedMsgVar = "";
            String SignedMsgVar = "";

            HiMessage msg = ctx.getCurrentMsg();
            HiETF root = msg.getETFBody();

            String SourceText = "MerId=" + MerId + "&" + "OrderNo=" + OrderNo + "&" + "OrderAmount=" + OrderAmount + "&" + "CurrCode=CNY&" + "CallBackUrl=" + CallBackUrl + "&" + "ResultMode=0&" + "Reserved01=&" + "Reserved02=";

            NetTran obj = new NetTran();
            this.log1.info("SourceText=" + SourceText);

            boolean ret = obj.EncryptMsg(SourceText, RcvCertFile);
            if (ret == true) {
                EncryptedMsgVar = obj.getLastResult();
                this.log1.info("EncryptedMsgVar=" + EncryptedMsgVar);
            } else {
                this.log1.info("EncryptMsg() Return:" + obj.getLastErrMsg() + "<br>");
                return -1;
            }

            ret = obj.SignMsg(SourceText, SendCertFile, Password);
            if (ret == true) {
                SignedMsgVar = obj.getLastResult();
                this.log1.info("SignedMsgVar=" + SignedMsgVar);
            } else {
                this.log1.info("SignMsg() Return:" + obj.getLastErrMsg() + "<br>");
                return -1;
            }

            root.setChildValue(EncryptedMsg, EncryptedMsgVar);
            root.setChildValue(SignedMsg, SignedMsgVar);

            return 0;
        } catch (Exception e) {
            this.log1.error(e.getMessage());
        }
        return -1;
    }
}