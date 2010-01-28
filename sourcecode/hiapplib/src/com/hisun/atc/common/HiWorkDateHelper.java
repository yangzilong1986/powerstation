 package com.hisun.atc.common;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiDateUtils;
 import java.util.Date;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiWorkDateHelper
 {
   public static final int HLDFLGLEN = 372;
 
   public static String[] getHoliday(HiMessageContext ctx, String areCod)
     throws HiException
   {
     HiETF recRoot = HiETFFactory.createETF("REC", "");
 
     String sqlCmd = "SELECT Year,HldFlg FROM cimhldtbl WHERE AreCod='" + areCod + "' ORDER BY Year";
 
     int recNum = HiDbtUtils.dbtsqlquery(sqlCmd, recRoot, ctx);
     if (recNum == 0)
     {
       return null;
     }
 
     String lastYear = null;
 
     String[] hldFlgInfo = new String[2];
     StringBuffer bb = new StringBuffer(372 * recNum + 3);
     for (int i = 1; i <= recNum; ++i)
     {
       HiETF recNode = recRoot.getChildNode("REC_" + i);
       if (recNode == null)
       {
         break;
       }
 
       String curYear = recNode.getChildValue("Year");
       if (i == 1)
       {
         hldFlgInfo[0] = curYear;
       }
 
       if ((lastYear != null) && (NumberUtils.toInt(curYear) - NumberUtils.toInt(lastYear) != 1))
       {
         break;
       }
 
       String hldFlg = recNode.getChildValue("HldFlg");
 
       hldFlg = StringUtils.rightPad(hldFlg, 372);
 
       bb.append(hldFlg);
       lastYear = curYear;
     }
     hldFlgInfo[1] = bb.toString();
     recRoot = null;
 
     return hldFlgInfo;
   }
 
   public static int date2pos(String dateStr, String refYear, int numYear)
   {
     try
     {
       int curYear = NumberUtils.toInt(dateStr.substring(0, 4));
       int difYear = curYear - NumberUtils.toInt(refYear);
       if ((difYear >= numYear) || (difYear < 0))
       {
         return -1;
       }
 
       Date refDate = HiDateUtils.parseDate(curYear + "0101");
       Date curDate = HiDateUtils.parseDate(dateStr);
 
       int seq = HiDateUtils.diffDate(curDate, refDate);
       if (seq < 0)
       {
         return -1;
       }
 
       return (difYear * 372 + seq);
     }
     catch (HiException e) {
     }
     return -1;
   }
 
   public static String pos2date(String refYear, int pos)
   {
     int curYear = NumberUtils.toInt(refYear) + pos / 372;
     try
     {
       Date curDate = HiDateUtils.addDate(HiDateUtils.parseDate(curYear + "0101"), pos % 372);
       return HiDateUtils.formatDateByFormat(curDate, "yyyyMMdd");
     } catch (HiException e) {
     }
     return null;
   }
 }