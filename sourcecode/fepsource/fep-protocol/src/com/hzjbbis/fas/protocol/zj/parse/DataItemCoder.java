package com.hzjbbis.fas.protocol.zj.parse;

import com.hzjbbis.fas.model.FaalRequestParam;
import com.hzjbbis.fas.protocol.conf.ProtocolDataItemConfig;
import com.hzjbbis.fk.model.BizRtu;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;

public class DataItemCoder {
    private static Log log = LogFactory.getLog(DataItemCoder.class);
    public static final int DEFAULT_DATABLOCK_MAX = 100;
    public static final int SMS_DATABLOCK_MAX = 210;
    public static final int NET_DATABLOCK_MAX = 1000;

    public static int coder(byte[] frame, int loc, FaalRequestParam fp, ProtocolDataItemConfig dic) {
        int slen = -1;
        try {
            if ((frame != null) && (fp != null) && (dic != null)) {
                String value = fp.getValue();
                switch (dic.getParserno()) {
                    case 1:
                        slen = Parser01.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 2:
                        slen = Parser02.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 3:
                        slen = Parser03.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 4:
                        slen = Parser04.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 5:
                        slen = Parser05.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 6:
                        slen = Parser06.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 7:
                        slen = Parser07.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 8:
                        slen = Parser08.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 9:
                        slen = Parser09.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 10:
                        slen = Parser10.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 11:
                        slen = Parser11.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 12:
                        slen = Parser12.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 13:
                        slen = Parser13.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 14:
                        slen = Parser14.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 15:
                        slen = Parser15.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 16:
                        slen = Parser16.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 17:
                        slen = Parser17.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 18:
                        slen = Parser18.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 19:
                        slen = Parser19.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 20:
                        slen = Parser20.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 21:
                        slen = Parser21.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 22:
                        slen = Parser22.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 23:
                        slen = Parser23.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 24:
                        slen = Parser24.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 25:
                        slen = Parser25.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 26:
                        slen = Parser26.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 27:
                        slen = Parser27.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 28:
                        slen = Parser28.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 29:
                        slen = Parser29.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 30:
                        slen = Parser30.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 31:
                        slen = Parser31.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 32:
                        slen = Parser32.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 33:
                        slen = Parser33.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 34:
                        slen = Parser34.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 35:
                        slen = Parser35.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 36:
                        slen = Parser36.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 37:
                        slen = Parser37.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 38:
                        slen = Parser38.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 39:
                        slen = Parser39.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 40:
                        slen = Parser40.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 41:
                        slen = Parser41.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 42:
                        slen = Parser42.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 43:
                        slen = Parser43.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 44:
                        slen = Parser44.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 45:
                        slen = Parser45.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 46:
                        slen = Parser46.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 47:
                        slen = Parser47.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 48:
                        slen = Parser48.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 49:
                        slen = Parser49.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 50:
                        slen = Parser50.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 51:
                        slen = Parser51.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 52:
                        slen = Parser52.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 53:
                        slen = Parser53.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    case 54:
                        slen = Parser54.constructor(frame, value, loc, dic.getLength(), dic.getFraction());
                        break;
                    default:
                        slen = fakeCoder(frame, loc, value, dic.getLength());
                }

            }

        } catch (Exception e) {
            log.error("coder", e);
        }
        return slen;
    }

    public static int fakeCoder(byte[] frame, int loc, String data, int len) {
        try {
            Arrays.fill(frame, loc, loc + len - 1, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return len;
    }

    public static int getDataMax(BizRtu rtu) {
        return 100;
    }
}