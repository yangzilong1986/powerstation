package com.hisun.bank.component;

import chinapay.PrivateKey;
import chinapay.SecureLink;
import com.hisun.atc.common.HiArgUtils;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;

public class ChinaPayVerifySigature {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        String merId = HiArgUtils.getStringNotNull(args, "MerId");
        String keyFile = HiArgUtils.getStringNotNull(args, "KeyFile");
        String signData = HiArgUtils.getStringNotNull(args, "SignData");
        String checkVal = HiArgUtils.getStringNotNull(args, "CheckVal");

        HiMessage msg = ctx.getCurrentMsg();
        Logger log = HiLog.getLogger(msg);

        PrivateKey key = new PrivateKey();
        if (!(key.buildKey(merId, 0, keyFile))) {
            log.error("[" + merId + "][" + keyFile + "] build key failure");
            return -1;
        }
        SecureLink secureLink = new SecureLink(key);
        if (secureLink.verifyAuthToken(signData, checkVal)) {
            return 0;
        }
        return 1;
    }
}