 package com.hisun.sw.gbk;
 
 import com.hisun.sw.HiChar;
 
 public class HiIBMSwGB
 {
   public static void GBKtoEBCDIC(HiChar pchFrom, HiChar pchTo)
   {
     if (pchFrom.high() >= 254) {
       GBKtoEBCDIC3(pchFrom, pchTo);
       return;
     }
     if (pchFrom.high() > 161) {
       GBKtoEBCDIC2(pchFrom, pchTo);
       return;
     }
 
     int iLineWord = 190;
     int iIndex = (pchFrom.high() - 129) * iLineWord + pchFrom.low() - 64;
     if (pchFrom.low() > 127) {
       --iIndex;
     }
     int iNewLineWord = 188;
     int iToLine = iIndex / iNewLineWord;
     int iToCol = iIndex % iNewLineWord;
 
     pchTo.setHigh(iToLine + 129);
     pchTo.setLow(iToCol + 65);
     if (pchTo.low() >= 128)
       pchTo.setLow(pchTo.low() + 1);
   }
 
   public static void GBKtoEBCDIC2(HiChar pchFrom, HiChar pchTo)
   {
     int iLineWord = 96;
     int iIndex = (pchFrom.high() - 170) * iLineWord + pchFrom.low();
     if (pchFrom.low() > 127) {
       --iIndex;
     }
     int iNewLineWord = 188;
     int iToLine = iIndex / iNewLineWord;
     int iToCol = iIndex % iNewLineWord;
 
     pchTo.setHigh(iToLine + 161);
     pchTo.setLow(iToCol + 65);
 
     if (pchTo.low() >= 128)
       pchTo.setLow(pchTo.low() + 1);
   }
 
   public static void GBKtoEBCDIC3(HiChar pchFrom, HiChar pchTo)
   {
     pchTo.setHigh(pchFrom.highByte - 48);
     pchTo.setLow(pchFrom.lowByte + 6);
   }
 
   public static void EBCDICtoGBK(HiChar pchFrom, HiChar pchTo)
   {
     if (pchFrom.high() == 206) {
       EBCDICtoGBK3(pchFrom, pchTo);
       return;
     }
     if ((pchFrom.high() >= 162) || ((pchFrom.high() == 161) && (pchFrom.low() >= 130))) {
       EBCDICtoGBK2(pchFrom, pchTo);
       return;
     }
 
     int iLineWord = 188;
     int iIndex = (pchFrom.high() - 129) * iLineWord + pchFrom.low() - 65;
     if (pchFrom.low() > 128) {
       --iIndex;
     }
     int iNewLineWord = 190;
     int iToLine = iIndex / iNewLineWord;
     int iToCol = iIndex % iNewLineWord;
 
     pchTo.setHigh(iToLine + 129);
     pchTo.setLow(iToCol + 64);
 
     if (pchTo.low() >= 127)
       pchTo.setLow(pchTo.low() + 1);
   }
 
   public static void EBCDICtoGBK2(HiChar pchFrom, HiChar pchTo)
   {
     int iLineWord = 188;
     int iIndex = (pchFrom.high() - 161) * iLineWord + pchFrom.low() - 129;
     if (pchFrom.low() > 128) {
       --iIndex;
     }
     int iNewLineWord = 96;
     int iToLine = iIndex / iNewLineWord;
     int iToCol = iIndex % iNewLineWord;
 
     pchTo.setHigh(iToLine + 170);
     pchTo.setLow(iToCol + 64);
 
     if (pchTo.low() >= 127)
       pchTo.setLow(pchTo.low() + 1);
   }
 
   public static void EBCDICtoGBK3(HiChar pchFrom, HiChar pchTo)
   {
     pchTo.setHigh(pchFrom.highByte + 48);
     pchTo.setLow(pchFrom.lowByte - 6);
   }
 }