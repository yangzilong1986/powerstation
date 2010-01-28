 package com.hisun.sqn;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.database.HiResultSet;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import java.util.ArrayList;
 import java.util.Iterator;
 import org.apache.commons.lang.StringUtils;
 
 public class HiDumTlrInfo
 {
   private static Logger _log = HiLog.getLogger("SYS.trc");
   private ArrayList _dumTlrNodes;
 
   public HiDumTlrInfo()
   {
     this._dumTlrNodes = new ArrayList();
   }
 
   public String getDumTlrFromMem(String brNo, String txnCnl, String cnlSub)
     throws HiException
   {
     debug(brNo + ":" + txnCnl + ":" + cnlSub);
     HiDumTrlNode node = getDumTlrNode(brNo, txnCnl, cnlSub);
     if (node == null) {
       throw new HiException("241004");
     }
     return node.getDumTrl();
   }
 
   public void init() throws HiException {
     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
     try {
       HiResultSet rs = dbUtil.execQuerySQL("SELECT BR_NO, TXN_CNL, CNL_SUB, DUM_TLR FROM pubdumtlr");
 
       for (int i = 0; i < rs.size(); ++i)
         append(rs.getValue(i, 0).trim(), rs.getValue(i, 1).trim(), rs.getValue(i, 2).trim(), rs.getValue(i, 3).trim());
     }
     finally
     {
       dbUtil.close();
     }
   }
 
   private void append(String brNo, String txnCnl, String cnlSub, String dumTlr) {
     HiDumTrlNode node = null;
     debug(brNo + ":" + txnCnl + ":" + cnlSub + ":" + dumTlr);
     node = getDumTlrNode(brNo, txnCnl, cnlSub);
     if (node == null) {
       node = new HiDumTrlNode();
       node._brNo = brNo;
       node._cnlSub = cnlSub;
       node._txnCnl = txnCnl;
       node._trlList.add(dumTlr);
       this._dumTlrNodes.add(node);
     } else {
       node._trlList.add(dumTlr);
     }
   }
 
   private HiDumTrlNode getDumTlrNode(String brNo, String txnCnl, String cnlSub) {
     Iterator iter = this._dumTlrNodes.iterator();
     while (iter.hasNext()) {
       HiDumTrlNode node = (HiDumTrlNode)iter.next();
       if ((((StringUtils.equals(node._brNo, brNo)) || (brNo == null))) && (((StringUtils.equals(node._cnlSub, cnlSub)) || (cnlSub == null))) && (((StringUtils.equals(node._txnCnl, txnCnl)) || (txnCnl == null))))
       {
         return node;
       }
     }
     return null;
   }
 
   private void debug(String msg) {
     if ((_log != null) && (_log.isDebugEnabled()))
       _log.debug(msg);
   }
 
   public void setLogger(Logger log)
   {
     _log = log;
   }
 }