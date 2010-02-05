 package com.hisun.atc.bat;
 
 import com.hisun.atc.common.HiAtcLib;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import java.util.Map;
 
 public class HiPubBatchInfo
 {
   public String acc_dt;
   public String br_no;
   public String bus_typ;
   public String ccy_cd;
   public String chk_flg;
   public String chk_tlr;
   public String cmt_flg;
   public String crp_cd;
   public String dn_tlr;
   public String dsk_nm;
   public String dsk_no;
   public String fal_amt;
   public String fal_cnt;
   public String fil_nm;
   public String frsp_cd;
   public String fTxn_Cd;
   public String hst_tm;
   public String lst_tm;
   public String nod_no;
   public String obj_svr;
   public String orn_amt;
   public String orn_cnt;
   public String rcv_tm;
   public String rsv_fld;
   public String rtn_tm;
   public String snd_cnt;
   public String snd_tlr;
   public String snd_tms;
   public String sts;
   public String suc_amt;
   public String suc_cnt;
   public String sum_flg;
   public String trd_tbl;
   public String txn_mod;
   public String txn_sqn;
   public String upd_flg;
 
   public void setValuesFromETF(HiETF root)
     throws HiException
   {
     HiAtcLib.setBeanPropertyFromETF(this, root);
   }
 
   public void setValuesFromMap(Map map) throws HiException {
     HiAtcLib.setBeanPropertyFromMap(this, map);
   }
 
   public void setETFValues(HiETF root) throws HiException
   {
     HiAtcLib.setETFFromBeanProperty(this, root);
   }
 
   public String toString()
   {
     return "sBatchInfo.BrNo=[" + this.br_no + "]\n" + "sBatchInfo.SndCnt=[" + this.snd_cnt + "]\n" + "sBatchInfo.LstTim=[" + this.lst_tm + "]\n" + "sBatchInfo.DskNo=[" + this.dsk_no + "]\n" + "sBatchInfo.DskNam=[" + this.dsk_nm + "]\n" + "sBatchInfo.BusTyp=[" + this.bus_typ + "]\n" + "sBatchInfo.ActDat=[" + this.acc_dt + "]\n" + "sBatchInfo.CrpCod=[" + this.crp_cd + "]\n" + "sBatchInfo.TxnMod=[" + this.txn_mod + "]\n" + "sBatchInfo.FilNam=[" + this.fil_nm + "]\n" + "sBatchInfo.CcyCod=[" + this.ccy_cd + "]\n" + "sBatchInfo.SndTlr=[" + this.snd_tlr + "]\n" + "sBatchInfo.NodNo=[" + this.nod_no + "]\n" + "sBatchInfo.RcvTm=[" + this.rcv_tm + "]\n" + "sBatchInfo.ChkTlr=[" + this.chk_tlr + "]\n" + "sBatchInfo.HstTm=[" + this.hst_tm + "]\n" + "sBatchInfo.RtnTm=[" + this.rtn_tm + "]\n" + "sBatchInfo.DnTlr=[" + this.dn_tlr + "]\n" + "sBatchInfo.OrnCnt=[" + this.orn_cnt + "]\n" + "sBatchInfo.OrnAmt=[" + this.orn_amt + "]\n" + "sBatchInfo.SucCnt=[" + this.suc_cnt + "]\n" + "sBatchInfo.SucAmt=[" + this.suc_amt + "]\n" + "sBatchInfo.FalCnt=[" + this.fal_cnt + "]\n" + "sBatchInfo.FalAmt=[" + this.fal_amt + "]\n" + "sBatchInfo.FRspCd=[" + this.frsp_cd + "]\n" + "sBatchInfo.TxnSqn=[" + this.txn_sqn + "]\n" + "sBatchInfo.Status=[" + this.sts + "]\n" + "sBatchInfo.CmtFlg=[" + this.cmt_flg + "]\n" + "sBatchInfo.SndTms=[" + this.snd_tms + "]\n" + "sBatchInfo.FTxnCd=[" + this.fTxn_Cd + "]\n" + "sBatchInfo.ObjSvr=[" + this.obj_svr + "]\n" + "sBatchInfo.TrdTbl=[" + this.trd_tbl + "]\n" + "sBatchInfo.ChkFlg=[" + this.chk_flg + "]\n" + "sBatchInfo.SumFlg=[" + this.sum_flg + "]\n" + "sBatchInfo.UpdFlg=[" + this.upd_flg + "]\n";
   }
 }