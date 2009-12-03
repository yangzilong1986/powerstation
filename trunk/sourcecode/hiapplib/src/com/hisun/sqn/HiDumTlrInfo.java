/*    */ package com.hisun.sqn;
/*    */ 
/*    */ import com.hisun.database.HiDataBaseUtil;
/*    */ import com.hisun.database.HiResultSet;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiDumTlrInfo
/*    */ {
/* 17 */   private static Logger _log = HiLog.getLogger("SYS.trc");
/*    */   private ArrayList _dumTlrNodes;
/*    */ 
/*    */   public HiDumTlrInfo()
/*    */   {
/* 22 */     this._dumTlrNodes = new ArrayList();
/*    */   }
/*    */ 
/*    */   public String getDumTlrFromMem(String brNo, String txnCnl, String cnlSub)
/*    */     throws HiException
/*    */   {
/* 35 */     debug(brNo + ":" + txnCnl + ":" + cnlSub);
/* 36 */     HiDumTrlNode node = getDumTlrNode(brNo, txnCnl, cnlSub);
/* 37 */     if (node == null) {
/* 38 */       throw new HiException("241004");
/*    */     }
/* 40 */     return node.getDumTrl();
/*    */   }
/*    */ 
/*    */   public void init() throws HiException {
/* 44 */     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
/*    */     try {
/* 46 */       HiResultSet rs = dbUtil.execQuerySQL("SELECT BR_NO, TXN_CNL, CNL_SUB, DUM_TLR FROM pubdumtlr");
/*    */ 
/* 48 */       for (int i = 0; i < rs.size(); ++i)
/* 49 */         append(rs.getValue(i, 0).trim(), rs.getValue(i, 1).trim(), rs.getValue(i, 2).trim(), rs.getValue(i, 3).trim());
/*    */     }
/*    */     finally
/*    */     {
/* 53 */       dbUtil.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   private void append(String brNo, String txnCnl, String cnlSub, String dumTlr) {
/* 58 */     HiDumTrlNode node = null;
/* 59 */     debug(brNo + ":" + txnCnl + ":" + cnlSub + ":" + dumTlr);
/* 60 */     node = getDumTlrNode(brNo, txnCnl, cnlSub);
/* 61 */     if (node == null) {
/* 62 */       node = new HiDumTrlNode();
/* 63 */       node._brNo = brNo;
/* 64 */       node._cnlSub = cnlSub;
/* 65 */       node._txnCnl = txnCnl;
/* 66 */       node._trlList.add(dumTlr);
/* 67 */       this._dumTlrNodes.add(node);
/*    */     } else {
/* 69 */       node._trlList.add(dumTlr);
/*    */     }
/*    */   }
/*    */ 
/*    */   private HiDumTrlNode getDumTlrNode(String brNo, String txnCnl, String cnlSub) {
/* 74 */     Iterator iter = this._dumTlrNodes.iterator();
/* 75 */     while (iter.hasNext()) {
/* 76 */       HiDumTrlNode node = (HiDumTrlNode)iter.next();
/* 77 */       if ((((StringUtils.equals(node._brNo, brNo)) || (brNo == null))) && (((StringUtils.equals(node._cnlSub, cnlSub)) || (cnlSub == null))) && (((StringUtils.equals(node._txnCnl, txnCnl)) || (txnCnl == null))))
/*    */       {
/* 80 */         return node;
/*    */       }
/*    */     }
/* 83 */     return null;
/*    */   }
/*    */ 
/*    */   private void debug(String msg) {
/* 87 */     if ((_log != null) && (_log.isDebugEnabled()))
/* 88 */       _log.debug(msg);
/*    */   }
/*    */ 
/*    */   public void setLogger(Logger log)
/*    */   {
/* 93 */     _log = log;
/*    */   }
/*    */ }