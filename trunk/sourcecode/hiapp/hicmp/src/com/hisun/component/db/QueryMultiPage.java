package com.hisun.component.db;

import com.hisun.atc.common.HiArgUtils;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessageContext;
import com.hisun.util.HiStringUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class QueryMultiPage {
    public int execute(HiATLParam args, HiMessageContext ctx) throws HiException {
        return 0;
    }

    private String getDynSentence(HiMessageContext ctx, String alias, ArrayList list) throws HiException {
        String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);

        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String strFields = (String) ctx.getProperty("FIELDS." + alias);

        if (StringUtils.isEmpty(strFields)) return sqlSentence;
        HiETF curEtf = ctx.getCurrentMsg().getETFBody();
        String[] fields = StringUtils.split(strFields, "|");
        for (int i = 0; i < fields.length; ++i) {
            list.add(ctx.getSpecExpre(curEtf, fields[i]));
        }
        sqlSentence = HiStringUtils.format(sqlSentence, list);
        return sqlSentence;
    }

    public static void main(String[] args) throws EncoderException, DecoderException {
        Hex codec = new Hex();
        byte[] buf = codec.encode("测333试".getBytes());
        System.out.println(new String(buf));
        System.out.println(new String(codec.decode(buf)));
    }
}