package com.hisun.bank.component;

import com.EasyLink.OpenVendorV34.NetTran;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;

import java.util.StringTokenizer;

public class NetGneteVerifySign {
    private Logger log1;

    public NetGneteVerifySign() {
        this.log1 = HiLog.getLogger("callproc.trc");
    }

    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        String EncodeMsg = args.get("EncodeMsg");
        String SignMsg = args.get("SignMsg");
        String SendCertFile = args.get("SendCertFile");
        String RcvCertFile = args.get("RcvCertFile");
        String Password = args.get("Password");

        String DecryptedMsg = "";

        String OrderNo = args.get("OrderNo");
        String PayNo = args.get("PayNo");
        String PayAmount = args.get("PayAmount");
        String CurrCode = args.get("CurrCode");
        String SystemSSN = args.get("SystemSSN");
        String RespCode = args.get("RespCode");
        String SettDate = args.get("SettDate");

        String OrderNoVar = "";
        String PayNoVar = "";
        String PayAmountVar = "";
        String CurrCodeVar = "";
        String SystemSSNVar = "";
        String RespCodeVar = "";
        String SettDateVar = "";

        HiMessage msg = ctx.getCurrentMsg();
        HiETF root = msg.getETFBody();

        NetTran obj = new NetTran();
        boolean ret = obj.DecryptMsg(EncodeMsg, SendCertFile, Password);
        if (ret == true) {
            DecryptedMsg = obj.getLastResult();
        } else {
            this.log1.info("DecryptMsg failed,errorMsg=" + obj.getLastErrMsg());
            return -1;
        }

        ret = obj.VerifyMsg(SignMsg, DecryptedMsg, RcvCertFile);
        if (ret != true) {
            this.log1.info("VerifyMsg failed,errorMsg=" + obj.getLastErrMsg());
            return -1;
        }
        OrderNoVar = getContent(DecryptedMsg, "OrderNo");
        this.log1.info("商户订单号不超过20位: " + OrderNoVar + "<br>");
        PayNoVar = getContent(DecryptedMsg, "PayNo");
        this.log1.info("支付单号: " + PayNoVar + "<br>");
        PayAmountVar = getContent(DecryptedMsg, "PayAmount");
        this.log1.info("支付金额: " + PayAmountVar + "<br>");
        CurrCodeVar = getContent(DecryptedMsg, "CurrCode");
        this.log1.info("货币代码: " + CurrCodeVar + "<br>");
        SystemSSNVar = getContent(DecryptedMsg, "SystemSSN");
        this.log1.info("系统参考号: " + SystemSSNVar + "<br>");
        RespCodeVar = getContent(DecryptedMsg, "RespCode");
        this.log1.info("响应码: " + RespCodeVar + "<br>");
        SettDateVar = getContent(DecryptedMsg, "SettDate");
        this.log1.info("清算日期: " + SettDateVar + "<br>");
        String Reserved01 = getContent(DecryptedMsg, "Reserved01");
        this.log1.info("保留域1: " + Reserved01 + "<br>");
        String Reserved02 = getContent(DecryptedMsg, "Reserved02");
        this.log1.info("保留域2: " + Reserved02 + "<br>");

        root.setChildValue(OrderNo, OrderNoVar);
        root.setChildValue(PayNo, PayNoVar);
        root.setChildValue(PayAmount, PayAmountVar);
        root.setChildValue(CurrCode, CurrCodeVar);
        root.setChildValue(SystemSSN, SystemSSNVar);
        root.setChildValue(RespCode, RespCodeVar);
        root.setChildValue(SettDate, SettDateVar);

        return 0;
    }

    public String getContent(String input, String para) {
        if ((input.equals("")) || (para.equals(""))) {
            return "";
        }
        String vv = "";
        StringTokenizer st = new StringTokenizer(input, "&");
        do {
            if (!(st.hasMoreElements())) break label92;
            vv = st.nextToken();
        } while ((vv.indexOf(para) == -1) || (!(vv.substring(0, vv.indexOf("=")).equals(para))));

        vv = vv.substring(vv.indexOf("=") + 1);
        return vv;

        label92:
        return "";
    }
}