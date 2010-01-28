 package com.hisun.sw.big5;
 
 import com.hisun.sw.HiChar;
 import java.util.ArrayList;
 
 public class HiHost2B5
 {
   private static final byte IMPOSSIBLECHARVALUE = -1;
   private static ArrayList<HOSTNODE> hosttable = new ArrayList();
 
   private static ArrayList<SYMNODE> symboltable = new ArrayList();
 
   private static ArrayList<UDCNODE> udctable = new ArrayList();
 
   private static ArrayList<HOSTCHAR> hostchartable = new ArrayList();
 
   private static int loadudc = 0;
 
   private static boolean LoadUDCMappingTable()
   {
     return false;
   }
 
   private static void Convert5550ToBig5(HiChar ibm, HiChar big5)
   {
     int i;
     int hbn;
     short ibmhighbyte = (short)(ibm.highByte & 0xFF);
     short ibmlowbyte = (short)(ibm.lowByte & 0xFF);
 
     if (((210 <= ibmhighbyte) && (ibmhighbyte <= 218) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((210 <= ibmhighbyte) && (ibmhighbyte <= 218) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((132 == ibmhighbyte) && (191 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((133 == ibmhighbyte) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((133 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((134 == ibmhighbyte) && (161 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((136 == ibmhighbyte) && (89 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((136 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((137 == ibmhighbyte) && (122 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((137 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((139 == ibmhighbyte) && (98 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((139 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((168 == ibmhighbyte) && (203 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((209 == ibmhighbyte) && (199 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((252 == ibmhighbyte) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((252 == ibmhighbyte) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)))
     {
       big5.highByte = -56;
       big5.lowByte = -2;
       return;
     }
 
     if (((129 <= ibmhighbyte) && (ibmhighbyte <= 138) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 138) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)) || ((139 == ibmhighbyte) && (64 <= ibmlowbyte) && (ibmlowbyte <= 97)) || ((209 == ibmhighbyte) && (((ibmlowbyte == 197) || (ibmlowbyte == 198)))) || ((168 == ibmhighbyte) && (ibmlowbyte == 202)) || ((252 == ibmhighbyte) && (((ibmlowbyte == 252) || (ibmlowbyte == 251)))))
     {
       i = 0;
       for (; (i < symboltable.size()) && (((SYMNODE)symboltable.get(i)).ibm5550code[0] != -1); ++i) {
         if ((((SYMNODE)symboltable.get(i)).ibm5550code[0] != ibmhighbyte) || (((SYMNODE)symboltable.get(i)).ibm5550code[1] != ibmlowbyte))
           continue;
         big5.highByte = ((SYMNODE)symboltable.get(i)).big5code[0];
         big5.lowByte = ((SYMNODE)symboltable.get(i)).big5code[1];
         return;
       }
 
       LogMsg("", "*** ERROR : can't find the symbol  ");
       return;
     }
 
     if (((219 <= ibmhighbyte) && (ibmhighbyte <= 251) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((219 <= ibmhighbyte) && (ibmhighbyte <= 251) && (128 <= ibmlowbyte) && (ibmlowbyte <= 252)))
     {
       if (loadudc == 0) {
         if (!(LoadUDCMappingTable())) {
           LogMsg("", " ERROR : loading udc mapping table failed  ");
           return;
         }
         loadudc = 1;
       }
 
       i = 0;
       for (; (i < udctable.size()) && (((UDCNODE)udctable.get(i)).ibm5550code[0] != -1); ++i) {
         if ((((UDCNODE)udctable.get(i)).ibm5550code[0] != ibmhighbyte) || (((UDCNODE)udctable.get(i)).ibm5550code[1] != ibmlowbyte))
           continue;
         big5.highByte = ((UDCNODE)udctable.get(i)).big5code[0];
         big5.lowByte = ((UDCNODE)udctable.get(i)).big5code[1];
         return;
       }
 
       LogMsg("", "*** ERROR : can't find the udc symbol  ");
       return;
     }
 
     if ((252 < ibmhighbyte) || (ibmhighbyte < 129) || (252 < ibmlowbyte) || (ibmlowbyte < 64) || ((126 < ibmlowbyte) && (ibmlowbyte < 128)))
     {
       big5.highByte = -56;
       big5.lowByte = -2;
       return;
     }
     if (ibmhighbyte < 169)
       hbn = (ibmhighbyte - 140) * 188;
     else {
       hbn = (ibmhighbyte - 169) * 188;
     }
     int lbn = ibmlowbyte - 64;
     if (lbn > 63) {
       lbn -= 1;
     }
     int sn = hbn + lbn;
     if (ibmhighbyte >= 169) {
       if (sn == 129) {
         sn += 9;
       }
       else if ((sn > 129) && (sn < 139)) {
         --sn;
       }
 
     }
 
     int b5h = sn / 157;
     int b5l = sn % 157;
     if (b5l >= 63) {
       b5l += 34;
     }
     if (ibmhighbyte < 169)
       big5.highByte = (byte)(b5h + 164);
     else {
       big5.highByte = (byte)(b5h + 201);
     }
     big5.lowByte = (byte)(b5l + 64);
   }
 
   private static void ConvertHOSTTo5550(HiChar big5)
   {
     short big5highbyte = (short)(big5.highByte & 0xFF);
     short big5lowbyte = (short)(big5.lowByte & 0xFF);
     if (big5highbyte < 76) {
       int i = 0;
       for (; (i < hosttable.size()) && (((HOSTNODE)hosttable.get(i)).ibm5550code[0] != -1); ++i) {
         if ((((HOSTNODE)hosttable.get(i)).hostcode[0] != big5highbyte) || (((HOSTNODE)hosttable.get(i)).hostcode[1] != big5lowbyte))
           continue;
         big5.highByte = ((HOSTNODE)hosttable.get(i)).ibm5550code[0];
         big5.lowByte = ((HOSTNODE)hosttable.get(i)).ibm5550code[1];
         return;
       }
 
       LogMsg("", "*** ERROR : can't find the symbol  ");
       return;
     }
 
     if ((254 == big5highbyte) && (((big5lowbyte == 254) || (big5lowbyte == 253))))
     {
       if (big5lowbyte == 254)
         big5.lowByte = -4;
       else {
         big5.lowByte = -5;
       }
       big5.highByte = -4;
       return;
     }
     if (big5highbyte > 145)
     {
       big5.highByte = (byte)(big5.highByte + 25);
       big5.lowByte = (byte)(big5.lowByte - 1);
     } else {
       big5.highByte = (byte)(big5.highByte + 64);
       big5.lowByte = (byte)(big5.lowByte - 1);
     }
   }
 
   public static void host2b5(HiChar from, HiChar to)
   {
     ConvertHOSTTo5550(from);
     Convert5550ToBig5(from, to);
     if (to.highByte == 0)
       to.highByte = 32;
     if (to.lowByte == 0)
       to.lowByte = 32;
   }
 
   private static void LogMsg(String msg1, String msg2)
   {
   }
 
   class HOSTCHAR
   {
     byte hostcharcode;
     byte big5code;
   }
 
   class UDCNODE
   {
     byte[] ibm5550code;
     byte[] big5code;
 
     UDCNODE()
     {
       this.ibm5550code = new byte[2];
       this.big5code = new byte[2];
     }
   }
 
   class SYMNODE
   {
     byte[] ibm5550code;
     byte[] big5code;
 
     SYMNODE()
     {
       this.ibm5550code = new byte[2];
       this.big5code = new byte[2];
     }
   }
 
   class HOSTNODE
   {
     byte[] hostcode;
     byte[] ibm5550code;
 
     HOSTNODE()
     {
       this.hostcode = new byte[2];
       this.ibm5550code = new byte[2];
     }
   }
 }