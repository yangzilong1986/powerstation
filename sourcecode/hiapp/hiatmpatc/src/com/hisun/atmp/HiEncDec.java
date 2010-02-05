package com.hisun.atmp;

import com.hisun.atmp.crypt.DES;
import com.hisun.atmp.crypt.Shuffle;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class HiEncDec {
    private static final char[] KEY2 = {'\1', '\1', '\1', '\1', '\1', '\1', '\1', '\1'};

    private static char[] DieboldMaskterKey = {136, 136, 136, 136, 136, 136, 136, 136};

    public static int MaskKey(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String keynode = argsMap.get("key");
        String key = root.getChildValue(keynode);
        char[] m_key = key.toCharArray();
        Shuffle.shufkey(m_key);
        String skey = new String(m_key);

        if (log.isInfoEnabled()) {
            log.info("maskkey : before = [" + key + "],after=[" + skey + "]");
        }
        root.setChildValue(keynode, skey);
        return 0;
    }

    public static int Shuffle(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String key = argsMap.get("key");
        String data = argsMap.get("data");
        if (log.isInfoEnabled()) {
            log.info("shuffle parameters: key = [" + key + "],data=[" + data + "]");
        }

        String outnode = argsMap.get("output");

        char[] out = new char[data.length()];
        char[] charArray = data.toCharArray();
        char[] charArray2 = key.toCharArray();
        Shuffle.shufkey(charArray2);
        Shuffle.shuffle(charArray, charArray2, out, charArray.length);

        String ret = new String(out);
        if (log.isInfoEnabled()) {
            log.info("shuffle output:" + ret);
        }
        root.setChildValue(outnode, ret);
        return 0;
    }

    public static int UnShuffle(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String key = argsMap.get("key");
        String data = argsMap.get("data");
        if (log.isInfoEnabled()) {
            log.info("unshuffle parameters: key = [" + key + "],data=[" + data + "]");
        }

        String outnode = argsMap.get("output");

        char[] out = new char[data.length()];
        char[] charArray = key.toCharArray();
        Shuffle.shufkey(charArray);
        Shuffle.unshuffle(data.toCharArray(), charArray, out, out.length);

        String ret = new String(out);
        if (log.isInfoEnabled()) {
            log.info("unshuffle output:" + ret);
        }
        root.setChildValue(outnode, ret);
        return 0;
    }

    public static int UnShuffleConvert(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String key = argsMap.get("key");
        String data = argsMap.get("data");
        if (log.isInfoEnabled()) {
            log.info("unshuffle parameters: key = [" + key + "],data=[" + data + "]");
        }

        String outnode = argsMap.get("output");

        char[] out = new char[data.length()];
        char[] charArray = key.toCharArray();
        Shuffle.shufkey(charArray);
        Shuffle.unshuffle(data.toCharArray(), charArray, out, out.length);

        char[] o = new char[16];
        o[0] = '0';
        o[1] = '6';
        for (int i = 2; i < 8; ++i)
            o[i] = out[i];
        for (i = 8; i < 16; ++i) {
            o[i] = 'F';
        }
        String brno = argsMap.get("brno");
        String crdno = argsMap.get("crdno");
        if (log.isInfoEnabled()) {
            log.info("brno:" + brno + ",crdno:" + crdno);
        }
        char[] result2 = encry_pass(log, brno.toCharArray(), crdno.toCharArray(), o);

        String out2 = new String(result2);
        String ret = out2;
        if (log.isInfoEnabled()) {
            log.info("UnShuffleAndDES output:" + ret);
        }
        root.setChildValue(outnode, ret);
        return 0;
    }

    public static int UnShuffleAccount(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String key = argsMap.get("key");
        String data = argsMap.get("data");
        if (log.isInfoEnabled()) {
            log.info("unshuffle parameters: key = [" + key + "],data=[" + data + "]");
        }

        String outnode = argsMap.get("output");

        String pre = "";
        String end = "";
        pre = getPre(data);
        end = getEnd(data);
        data = data.substring(2, 18);
        if (log.isInfoEnabled()) {
            log.info("账号:" + data);
        }

        char[] out = new char[data.length()];
        char[] charArray = key.toCharArray();
        Shuffle.shufkey(charArray);
        Shuffle.unshuffle(data.toCharArray(), charArray, out, out.length);

        String ret = new String(out);
        ret = pre + ret + end;
        if (log.isInfoEnabled()) {
            log.info("unshuffle output:" + ret);
        }
        root.setChildValue(outnode, ret);
        return 0;
    }

    private static String getEnd(String data) {
        int iend = data.length() - 1;
        for (int i = iend; i > 0; --i) {
            if (data.charAt(i) != 'A') {
                iend = i;
                break;
            }
        }

        String end = data.substring(18, iend + 1);
        return end;
    }

    private static String getPre(String data) {
        int istart = 0;
        for (int i = 0; i < data.length(); ++i) {
            if (data.charAt(i) != 'A') {
                istart = i;
                break;
            }
        }
        return data.substring(istart, 2);
    }

    public static int UpdateKey(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String keynode = argsMap.get("key");
        String keyValue = root.getChildValue(keynode);
        if (log.isInfoEnabled()) {
            log.info("updateKey parameters: key = [" + keyValue + "]");
        }

        char[] key = keyValue.toCharArray();
        Shuffle.shufkey(key);
        Shuffle.updatetrm(key);
        Shuffle.shufkey(key);

        String ret = new String(key);
        if (log.isInfoEnabled()) {
            log.info("updateKey output:" + ret);
        }
        root.setChildValue(keynode, ret);
        return 0;
    }

    public static int PinConvert(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());
        try {
            String keynode = argsMap.get("key");
            char[] key = keynode.toCharArray();
            Shuffle.shufkey(key);

            String spblock = argsMap.get("pinblock");
            char[] pinblock = spblock.toCharArray();

            if (log.isInfoEnabled()) {
                log.info("pinConvert parameters: key = [" + keynode + "],pinblock=[" + spblock + "]");

                log.info("key:" + new String(key));
            }

            char[] packed_pin = new char[pinblock.length / 2];
            Shuffle.strToBin(packed_pin, pinblock, packed_pin.length);

            if (log.isInfoEnabled()) {
                log.info("packed pinblock:" + new String(packed_pin));
            }

            char[] encryptKey = PINBlockKey(key);

            if (log.isInfoEnabled()) {
                log.info("PINBlockKey:" + new String(encryptKey));
            }

            char[] result = desEncrypt(encryptKey, packed_pin, 0);

            if (log.isInfoEnabled()) {
                log.info("des pinblock:" + new String(result));
            }

            char[] ret = new char[16];
            Shuffle.binToStr(ret, result, 8);

            int pinlen = argsMap.getInt("pinlen");
            String atmid = argsMap.get("atmid");
            if (log.isInfoEnabled()) {
                log.info("pinblock:" + new String(ret));
                log.info("checking: pinlen=" + pinlen + ",atmid=" + atmid);
            }

            char[] output1 = new char[16];
            int iret = checkPinDate(ret, atmid, pinlen, output1);
            if (iret == 0) {
                String brno = argsMap.get("brno");
                String crdno = argsMap.get("crdno");
                if (log.isInfoEnabled()) {
                    log.info("brno:" + brno + ",crdno:" + crdno);
                }
                char[] result2 = encry_pass(log, brno.toCharArray(), crdno.toCharArray(), output1);

                String outnode = argsMap.get("output");
                String out2 = new String(result2);
                if (log.isInfoEnabled()) {
                    log.info("pinConvert output:" + out2);
                }
                root.setChildValue(outnode, out2);
            }
            return iret;
        } catch (RuntimeException t) {
            log.error(t.toString());
            throw t;
        }
    }

    public static int DieboldDesPin(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        char[] key = genRandomKey();

        if (log.isInfoEnabled()) {
            String pinkey = new String(key);
            log.info("DieboldDesPin parameters: pinkey = [" + pinkey + "]");
        }

        char[] packed_pin = new char[key.length / 2];
        Shuffle.strToBCD(packed_pin, key, packed_pin.length);

        char[] pin_key = desEncrypt(DieboldMaskterKey, packed_pin, 1);

        char[] d = new char[16];
        Shuffle.bcdToStr(d, pin_key, 8);
        String key16 = new String(d);
        if (log.isInfoEnabled()) {
            log.info("DieboldDesPin output: key16 = [" + key16 + "]");
        }

        StringBuffer buf = new StringBuffer(24);
        for (int i = 0; i < 8; ++i) {
            int v = pin_key[i] & 0xFF;
            buf.append(StringUtils.leftPad(String.valueOf(v), 3, '0'));
        }
        String out_key = buf.toString();
        if (log.isInfoEnabled()) {
            log.info("DieboldDesPin output: key24 = [" + out_key + "]");
        }

        String keynode = argsMap.get("key24");
        root.setChildValue(keynode, out_key);
        String keynode16 = argsMap.get("key16");
        root.setChildValue(keynode16, key16);
        return 0;
    }

    private static char[] genRandomKey() {
        char[] key = new char[16];
        for (int i = 0; i < 16; ++i)
            key[i] = genRandomNum();
        return key;
    }

    private static char genRandomNum() {
        return (char) (RandomUtils.nextInt(10) + 48);
    }

    public static int DieboldPinConvert(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
        HiETF root = ctx.getCurrentMsg().getETFBody();
        Logger log = HiLog.getLogger(ctx.getCurrentMsg());

        String keynode = argsMap.get("key");
        char[] key = keynode.toCharArray();

        String spblock = argsMap.get("pinblock");
        char[] pinblock = spblock.toCharArray();

        if (log.isInfoEnabled()) {
            log.info("DieboldPinConvert parameters: key = [" + keynode + "],pinblock=[" + spblock + "]");
        }

        char[] packed_pin = new char[pinblock.length / 2];
        Shuffle.strToBin(packed_pin, pinblock, packed_pin.length);

        if (log.isInfoEnabled()) {
            log.info("packed pinblock:" + new String(packed_pin));
        }

        char[] packed_key = new char[8];
        Shuffle.strToBCD(packed_key, key, packed_key.length);

        char[] pin_key = desEncrypt(DieboldMaskterKey, packed_key, 0);

        char[] ret = desEncrypt(pin_key, packed_pin, 0);

        char[] d = new char[16];
        Shuffle.bcdToStr(d, ret, 8);
        if (log.isInfoEnabled()) {
            log.info("diebold pinblock:" + new String(d));
        }

        int i = 0;
        for (; i < d.length; ++i) {
            if (d[i] == 'F') break;
        }
        String prestr = String.valueOf(i);
        prestr = StringUtils.leftPad(prestr, 2, "0");

        String lennode = argsMap.get("pinlen");
        if (lennode != null) {
            root.setChildValue(lennode, prestr);
            if (log.isInfoEnabled()) {
                log.info("set pinlen:" + prestr);
            }
        }

        char[] ret2 = new char[16];
        ret2[0] = prestr.charAt(0);
        ret2[1] = prestr.charAt(1);
        System.arraycopy(d, 0, ret2, 2, 14);

        String brno = argsMap.get("brno");
        String crdno = argsMap.get("crdno");
        if (log.isInfoEnabled()) {
            log.info("brno:" + brno + ",crdno:" + crdno);
        }
        char[] result2 = encry_pass(log, brno.toCharArray(), crdno.toCharArray(), ret2);

        String outnode = argsMap.get("output");
        String out2 = new String(result2);
        if (log.isInfoEnabled()) {
            log.info("DieboldPinConvert output:" + out2);
        }
        root.setChildValue(outnode, out2);

        return 0;
    }

    private static char[] encry_pass(Logger log, char[] brno, char[] crdno, char[] pin) {
        if (log.isInfoEnabled()) {
            log.info("pin code:" + new String(pin));
        }

        char[] key = new char[16];
        key[0] = '1';
        key[1] = '6';
        System.arraycopy(brno, 0, key, 2, 3);
        System.arraycopy(crdno, crdno.length - 12, key, 5, 11);

        if (log.isInfoEnabled()) {
            log.info("key:" + new String(key));
        }
        char[] packed_key = new char[8];
        Shuffle.strToBin(packed_key, key, 8);

        char[] packed_pin1 = new char[pin.length / 2];
        Shuffle.strToBCD(packed_pin1, pin, packed_pin1.length);
        char[] xor = new char[16];
        for (int i = 0; i < 16; ++i)
            xor[i] = '0';
        System.arraycopy(crdno, crdno.length - 13, xor, 4, 12);
        char[] packed_xor = new char[8];
        Shuffle.strToBCD(packed_xor, xor, packed_xor.length);

        for (int i = 0; i < 8; ++i) {
            packed_pin1[i] = (char) (packed_pin1[i] ^ packed_xor[i]);
        }
        if (log.isInfoEnabled()) {
            char[] d = new char[16];
            Shuffle.bcdToStr(d, packed_pin1, 8);
            log.info("data:" + new String(d));
        }

        char[] result2 = desEncrypt(packed_key, packed_pin1, 1);

        char[] result = new char[16];
        Shuffle.bcdToStr(result, result2, 8);
        return result;
    }

    private static int checkPinDate(char[] data, String atmid, int pinlen, char[] output) {
        if (!(new String(data).startsWith(atmid))) return 1;
        int start = atmid.length();
        if (start + pinlen > data.length) {
            return 2;
        }
        boolean ok = true;
        for (int i = start + pinlen; i < data.length; ++i) {
            if (data[i] != '0') {
                ok = false;
                break;
            }
        }
        if (!(ok)) return 2;
        char[] ret = output;
        for (int j = 0; j < ret.length; ++j) {
            ret[j] = 'F';
        }
        String prestr = String.valueOf(pinlen);
        prestr = StringUtils.leftPad(prestr, 2, "0");

        ret[0] = prestr.charAt(0);
        ret[1] = prestr.charAt(1);

        System.arraycopy(data, start, ret, 2, pinlen);
        return 0;
    }

    private static char[] desEncrypt(char[] key, char[] data, int flag) {
        DES des = new DES();

        byte[] result = des.DesEncrypt(chars2bytes(key), chars2bytes(data), flag);

        char[] ret = new char[result.length];

        for (int i = 0; i < key.length; ++i)
            ret[i] = (char) result[i];
        return ret;
    }

    private static byte[] chars2bytes(char[] key) {
        byte[] b_key = new byte[key.length];
        for (int i = 0; i < key.length; ++i)
            b_key[i] = (byte) key[i];
        return b_key;
    }

    private static char[] bytes2chars(byte[] bytes) {
        char[] ret = new char[bytes.length];

        for (int i = 0; i < bytes.length; ++i)
            ret[i] = (char) bytes[i];
        return ret;
    }

    private static char[] PINBlockKey(char[] key) {
        char[] blockKeyMask = {'1', '1', '2', '2', '4', '4', '7', '7', '8', '8'};

        char[] key1 = {'0', '0', '0', '0', '0', '0', '0', '0'};
        char[] result = new char[8];

        System.arraycopy(key, 0, key1, 0, key.length);
        for (int i = 0; i < key1.length; ++i) {
            char c = key1[i];
            if (!(Character.isDigit(c))) c = '0';
            result[i] = blockKeyMask[(c - '0')];
        }
        return result;
    }

    public static void main(String[] args) {
    }
}