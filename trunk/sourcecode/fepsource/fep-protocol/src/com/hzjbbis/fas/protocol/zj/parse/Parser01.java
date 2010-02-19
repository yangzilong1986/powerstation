package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;

import java.text.NumberFormat;

public class Parser01 {
    public static Object parsevalue(byte[] data, int loc, int len, int fraction) {
        Object rt = null;
        try {
            boolean ok = true;

            ok = ParseTool.isValidBCD(data, loc, len);
            if (ok) {
                int val = ParseTool.nBcdToDecimal(data, loc, len);
                if (val >= 0) if (fraction > 0) {
                    NumberFormat snf = NumberFormat.getInstance();
                    snf.setMinimumFractionDigits(fraction);
                    snf.setGroupingUsed(false);
                    rt = snf.format(val / ParseTool.fraction[fraction]);
                } else {
                    rt = new Integer(val);
                }
            }
        } catch (Exception e) {
            throw new MessageDecodeException(e);
        }
        return rt;
    }

    public static int constructor(byte[] frame, String value, int loc, int len, int fraction) {
        try {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(4);

            double val = nf.parse(value).doubleValue();
            if (fraction > 0) {
                val *= ParseTool.fraction[fraction];
            }

            ParseTool.IntToBcd(frame, (int) val, loc, len);
        } catch (Exception e) {
            throw new MessageEncodeException("bab BCD string:" + value);
        }
        return len;
    }
}