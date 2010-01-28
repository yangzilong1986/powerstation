 package com.hisun.atmp;
 
 import com.hisun.crypt.Encryptor;
 import com.hisun.crypt.des.DESCryptorFactory;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiConvHelper;
 import com.hisun.util.HiICSProperty;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.nio.ByteBuffer;
 import java.nio.channels.FileChannel;
 import java.nio.channels.FileChannel.MapMode;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Random;
 import org.apache.commons.codec.binary.Hex;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiAtmpFit
 {
   private static final Encryptor encryptor;
 
   public int GetCrdFlg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String trk2 = argsMap.get("Trk2");
     String trk3 = argsMap.get("Trk3");
     boolean isHandInputCard = false;
     String crdNo = argsMap.get("CrdNo");
 
     if ((StringUtils.isBlank(trk2)) && (StringUtils.isBlank(trk3))) {
       if (log.isDebugEnabled()) {
         log.debug("hand input card:" + crdNo);
       }
       isHandInputCard = true;
     }
     if ((isHandInputCard) && (StringUtils.isEmpty(crdNo))) {
       throw new HiAppException(-1, "310008");
     }
     int i = 0;
 
     HiFit.loadFit(ctx);
     ArrayList fitTable = HiFit.getFitTable();
     for (; i < fitTable.size(); ++i) {
       HiFitItem item = new HiFitItem();
       item.toFitItem((HashMap)fitTable.get(i));
       if (isHandInputCard) {
         String fitValue = StringUtils.substring(crdNo, 0, item.fitLen);
 
         String fit2 = item.fitCtt.substring(0, item.fitLen);
         if (!(StringUtils.equals(fitValue, fit2)))
           continue;
       }
       else
       {
         int idx;
         String trk = null;
 
         if (item.fitTrk == 2)
           trk = trk2;
         else {
           trk = trk3;
         }
 
         if (StringUtils.isEmpty(trk))
         {
           continue;
         }
 
         if (item.fitOfs + item.fitLen > trk.length()) {
           continue;
         }
         if (item.fitLen > item.fitCtt.length()) {
           throw new HiAppException(-1, "310003");
         }
 
         String fit1 = trk.substring(item.fitOfs, item.fitOfs + item.fitLen);
         String fit2 = item.fitCtt.substring(0, item.fitLen);
         if (log.isDebugEnabled()) {
           log.debug("fit:[" + fit1 + "]:[" + fit2 + "]");
         }
 
         if (!(StringUtils.equals(fit1, fit2))) {
           continue;
         }
 
         if ((item.crdTrk == 2) && (StringUtils.isEmpty(trk2))) {
           throw new HiAppException(-1, "310001");
         }
 
         if ((item.crdTrk == 3) && (StringUtils.isEmpty(trk3))) {
           throw new HiAppException(-1, "310002");
         }
 
         if (item.crdTrk == 2)
           trk = trk2;
         else {
           trk = trk3;
         }
 
         if (item.crdLen > 0) {
           if (item.crdOfs + item.crdLen > trk.length()) {
             throw new HiAppException(-1, "310004");
           }
 
           crdNo = trk.substring(item.crdOfs, item.crdOfs + item.crdLen);
         }
         else {
           idx = trk.indexOf("=", item.crdOfs);
           if (idx == -1) {
             throw new HiAppException(-1, "310006");
           }
 
           crdNo = trk.substring(item.crdOfs, idx);
         }
 
         if (item.vaDtOf >= 0) {
           if (item.vaDtOf + 4 > trk.length()) {
             throw new HiAppException(-1, "310010", String.valueOf(item.vaDtOf));
           }
 
           String expDat = trk.substring(item.vaDtOf, item.vaDtOf + 4);
           if (!(StringUtils.isNumeric(expDat))) {
             throw new HiException("310011", expDat);
           }
 
           if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
             throw new HiException("310011", expDat);
           }
           root.setChildValue("ExpDat", expDat);
         } else {
           idx = trk2.indexOf("=");
           if ((idx == -1) || (idx == 0)) {
             throw new HiAppException(-1, "310006");
           }
 
           String expDat = trk2.substring(idx + 1, idx + 1 + 4);
           if (!(StringUtils.isNumeric(expDat))) {
             throw new HiException("310011", expDat);
           }
 
           if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
             throw new HiException("310011", expDat);
           }
           root.setChildValue("ExpDat", expDat);
         }
       }
 
       root.setChildValue("Crd_No", crdNo);
       root.setChildValue("CrdFlg", item.crdFlg);
       root.setChildValue("BnkTyp", item.bnkTyp);
       if ((!(StringUtils.equals(item.crdFlg, "01"))) && (!(StringUtils.equals(item.crdFlg, "02"))) && (!(StringUtils.equals(item.crdFlg, "03"))) && (!(StringUtils.equals(item.crdFlg, "04"))))
       {
         break;
       }
 
       if (item.cdCdOf + 3 >= crdNo.length()) {
         throw new HiAppException(-1, "310005");
       }
 
       String orgCd = crdNo.substring(item.cdCdOf, item.cdCdOf + 3);
       root.setChildValue("OrgCd", orgCd);
       break;
     }
 
     if (i >= fitTable.size())
     {
       if (log.isInfoEnabled()) {
         log.info("no match record!default process...");
       }
       if (isHandInputCard) {
         root.setChildValue("Crd_No", crdNo);
       } else {
         if (StringUtils.isEmpty(trk2)) {
           throw new HiAppException(-1, "310001");
         }
 
         int idx = trk2.indexOf("=");
         if ((idx == -1) || (idx == 0)) {
           throw new HiAppException(-1, "310006");
         }
 
         crdNo = trk2.substring(0, idx);
         root.setChildValue("Crd_No", crdNo);
 
         String expDat = trk2.substring(idx + 1, idx + 1 + 4);
         if (!(StringUtils.isNumeric(expDat))) {
           throw new HiException("310011", expDat);
         }
 
         if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
           throw new HiException("310011", expDat);
         }
         root.setChildValue("ExpDat", expDat);
       }
       root.setChildValue("CrdFlg", "50");
       root.setChildValue("BnkTyp", "9999");
     }
 
     return 0;
   }
 
   public int DESEncrypt(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     String node = argsMap.get("node");
 
     String data = root.getChildValue(node);
     if ((data != null) && (data.length() > 0)) {
       try {
         byte[] ret = encryptor.encrypt(data.getBytes());
         root.setChildValue(node, HiConvHelper.binToAscStr(ret));
       } catch (Exception e) {
         throw new HiException(e);
       }
     }
 
     return 0;
   }
 
   public int XOREncrypt(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     String node = argsMap.get("node");
 
     String data = root.getChildValue(node);
     StringBuffer result = new StringBuffer();
     String key = "hisun";
     int j = 0;
     for (int i = 0; i < data.length(); ++i) {
       if (j == key.length())
         j = 0;
       result.append(StringUtils.leftPad(Integer.toHexString(data.charAt(i) ^ key.charAt(j)), 2, '0'));
 
       ++j;
     }
     root.setChildValue(node, result.toString());
     return 0;
   }
 
   public int XORDecrypt(HiATLParam argsMap, HiMessageContext ctx) throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     String node = argsMap.get("node");
 
     String data = root.getChildValue(node);
     StringBuffer result = new StringBuffer();
     String key = "hisun";
     int j = 0;
     for (int i = 0; i < data.length(); i += 2) {
       if (j == key.length())
         j = 0;
       int c = Integer.parseInt(data.substring(i, i + 2), 16);
       result.append((char)(c ^ key.charAt(j)));
       ++j;
     }
     root.setChildValue(node, result.toString());
     return 0;
   }
 
   public static int ParseFile(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String file = HiICSProperty.getWorkDir() + "/" + argsMap.get("file");
     String lennode = argsMap.get("len");
     String datanode = argsMap.get("data");
 
     if (log.isInfoEnabled()) {
       log.info("ParseFile parameters: file = [" + file + "]");
     }
     FileChannel channel = null;
     try {
       channel = new FileInputStream(file).getChannel();
       ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
       byte[] lenbyte = new byte[3];
       buf.get(lenbyte);
 
       String len = new String(lenbyte);
 
       byte[] databyte = new byte[(int)(channel.size() - 3L)];
       buf.get(databyte);
       char[] hex = Hex.encodeHex(databyte);
       String data = new String(hex);
 
       if (log.isInfoEnabled()) {
         log.info("ParseFile output: len = [" + len + "], data = [" + data + "]");
       }
 
       root.setChildValue(lennode, len);
       root.setChildValue(datanode, data);
     } catch (Exception e) {
     }
     finally {
       try {
         if (channel != null)
           channel.close();
       }
       catch (IOException e) {
       }
     }
     return 0;
   }
 
   public static int Random(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF root = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String freq = argsMap.get("freq");
     int n = Integer.parseInt(freq);
     Random r = new Random();
     if (n == 1) {
       root.setChildValue("result", "1");
       return 0;
     }
 
     int ret = r.nextInt(n);
     if (ret == 1)
       root.setChildValue("result", "1");
     else
       root.setChildValue("result", "0");
     return 0;
   }
 
   public static int PasswdCheck(HiATLParam argsMap, HiMessageContext ctx) throws HiException
   {
     String passwd = argsMap.get("PassWd");
     for (int i = 0; i < passwd.length(); ++i) {
       if (!(Character.isDigit(passwd.charAt(i)))) {
         return 1;
       }
     }
     return 0;
   }
 
   public int ReloadCrdFlg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiFit.reLoadFit(ctx);
     return 0;
   }
 
   static
   {
     DESCryptorFactory factory = new DESCryptorFactory();
     encryptor = factory.getEncryptor();
     encryptor.setKey(factory.getDefaultEncryptKey());
   }
 }