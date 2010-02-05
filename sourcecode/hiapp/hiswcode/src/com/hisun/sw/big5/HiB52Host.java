 package com.hisun.sw.big5;
 
 import com.hisun.sw.HiChar;
 import java.util.ArrayList;
 
 public class HiB52Host
 {
   public static final byte IMPOSSIBLECHARVALUE = -1;
   private static ArrayList<SYMNODE> _symboltable = new ArrayList();
 
   private static ArrayList<HOSTNODE> _hosttable = new ArrayList();
 
   private static ArrayList<UDCNODE> _udctable = new ArrayList();
 
   private static ArrayList<HOSTCHAR> _hostchartable = new ArrayList();
 
   private static int _loadudc = 0;
 
   private static boolean _loadudcMappingTable()
   {
     return false;
   }
 
   private static void ConvertBig5To5550(HiChar from, HiChar to)
   {
     int i;
     int hbn;
     short ibmhighbyte = (short)(from.highByte & 0xFF);
     short ibmlowbyte = (short)(from.lowByte & 0xFF);
 
     if (((161 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 254))))) || ((162 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 254))))) || ((163 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 224))))) || ((198 == ibmhighbyte) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((199 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 254))))) || ((200 == ibmhighbyte) && ((((64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((161 <= ibmlowbyte) && (ibmlowbyte <= 211))))) || ((242 == ibmhighbyte) && (134 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((243 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((244 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((245 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((246 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((247 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((248 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((249 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((200 == ibmhighbyte) && (ibmlowbyte == 254)))
     {
       i = 0;
       for (; (i < _symboltable.size()) && (((SYMNODE)_symboltable.get(i)).big5code[0] != -1); ++i) {
         if ((((SYMNODE)_symboltable.get(i)).big5code[0] != ibmhighbyte) || (((SYMNODE)_symboltable.get(i)).big5code[1] != ibmlowbyte))
           continue;
         to.highByte = ((SYMNODE)_symboltable.get(i)).ibm5550code[0];
         to.lowByte = ((SYMNODE)_symboltable.get(i)).ibm5550code[1];
         return;
       }
 
       LogMsg("*** ERROR : can't find the symbol ", "");
       return;
     }
 
     if (((250 <= ibmhighbyte) && (ibmhighbyte <= 254) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((250 <= ibmhighbyte) && (ibmhighbyte <= 254) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((142 <= ibmhighbyte) && (ibmhighbyte <= 160) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((142 <= ibmhighbyte) && (ibmhighbyte <= 160) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 141) && (64 <= ibmlowbyte) && (ibmlowbyte <= 126)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 141) && (161 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((129 <= ibmhighbyte) && (ibmhighbyte <= 139) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((140 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 130)) || ((249 == ibmhighbyte) && (214 <= ibmlowbyte) && (ibmlowbyte <= 254)))
     {
       if (_loadudc == 0) {
         if (!(_loadudcMappingTable())) {
           LogMsg(" ERROR : loading udc mapping table failed ", "");
           return;
         }
         _loadudc = 1;
       }
 
       i = 0;
       for (; (i < _udctable.size()) && (((UDCNODE)_udctable.get(i)).big5code[0] != -1); ++i) {
         if ((((UDCNODE)_udctable.get(i)).big5code[0] != ibmhighbyte) || (((UDCNODE)_udctable.get(i)).big5code[1] != ibmlowbyte))
           continue;
         to.highByte = ((UDCNODE)_udctable.get(i)).ibm5550code[0];
         to.lowByte = ((UDCNODE)_udctable.get(i)).ibm5550code[1];
         return;
       }
 
       LogMsg("*** ERROR : can't find the udc symbol ", "");
       return;
     }
 
     if ((254 < ibmhighbyte) || (ibmhighbyte < 129) || (254 < ibmlowbyte) || (ibmlowbyte < 64) || ((126 < ibmlowbyte) && (ibmlowbyte < 129)))
     {
       to.highByte = -4;
       to.lowByte = -4;
       return;
     }
 
     if (((141 <= ibmhighbyte) && (ibmhighbyte <= 241) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((250 <= ibmhighbyte) && (ibmhighbyte <= 254) && (129 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((140 == ibmhighbyte) && (131 <= ibmlowbyte) && (ibmlowbyte <= 160)) || ((163 == ibmhighbyte) && (225 <= ibmlowbyte) && (ibmlowbyte <= 254)) || ((200 == ibmhighbyte) && (212 <= ibmlowbyte) && (ibmlowbyte < 254)) || ((242 == ibmhighbyte) && (129 <= ibmlowbyte) && (ibmlowbyte <= 133)))
     {
       to.highByte = -4;
       to.lowByte = -5;
       return;
     }
     if (ibmhighbyte < 201)
       hbn = (ibmhighbyte - 164) * 157;
     else {
       hbn = (ibmhighbyte - 201) * 157;
     }
     int lbn = ibmlowbyte - 64;
     if (lbn > 63) {
       lbn -= 34;
     }
     int sn = hbn + lbn;
     if (ibmhighbyte >= 201) {
       if (sn == 138) {
         sn -= 9;
       }
       else if ((sn >= 129) && (sn < 138)) {
         ++sn;
       }
 
     }
 
     int b5h = sn / 188;
     int b5l = sn % 188;
     if (b5l >= 63) {
       b5l += 1;
     }
     if (ibmhighbyte < 201)
       to.highByte = (byte)(b5h + 140);
     else {
       to.highByte = (byte)(b5h + 169);
     }
     to.lowByte = (byte)(b5l + 64);
   }
 
   private static void Convert5550ToHOST(HiChar big5)
   {
     short big5highbyte = (short)(big5.highByte & 0xFF);
     short big5lowbyte = (short)(big5.lowByte & 0xFF);
 
     if (big5highbyte < 140) {
       int i = 0;
       for (; (i < _hosttable.size()) && (((HOSTNODE)_hosttable.get(i)).ibm5550code[0] != -1); ++i) {
         if ((((HOSTNODE)_hosttable.get(i)).ibm5550code[0] != big5highbyte) || (((HOSTNODE)_hosttable.get(i)).ibm5550code[1] != big5lowbyte))
           continue;
         big5.highByte = ((HOSTNODE)_hosttable.get(i)).hostcode[0];
         big5.lowByte = ((HOSTNODE)_hosttable.get(i)).hostcode[1];
         return;
       }
 
       LogMsg("*** ERROR : can't find the host symbol ", "");
       return;
     }
 
     if ((252 == big5highbyte) && (((big5lowbyte == 252) || (big5lowbyte == 251))))
     {
       if (big5lowbyte == 252)
         big5.lowByte = -2;
       else {
         big5.lowByte = -3;
       }
       big5.highByte = -2;
       return;
     }
     if (big5highbyte > 209) {
       big5.highByte = (byte)(big5.highByte - 25);
       big5.lowByte = (byte)(big5.lowByte + 1);
     } else {
       big5.highByte = (byte)(big5.highByte - 64);
       big5.lowByte = (byte)(big5.lowByte + 1);
     }
   }
 
   public static void b52host(HiChar from, HiChar to)
   {
     ConvertBig5To5550(from, to);
     Convert5550ToHOST(to);
     if (to.highByte == 0)
       to.highByte = 32;
     if (to.lowByte == 0)
       to.lowByte = 32;
   }
 
   private static void LogMsg(String p1, String p2)
   {
   }
 
   class HOSTCHAR
   {
     byte big5code;
     byte hostcharcode;
   }
 
   class UDCNODE
   {
     byte[] big5code;
     byte[] ibm5550code;
 
     UDCNODE()
     {
       this.big5code = new byte[2];
       this.ibm5550code = new byte[2];
     }
   }
 
   class HOSTNODE
   {
     byte[] ibm5550code;
     byte[] hostcode;
 
     HOSTNODE()
     {
       this.ibm5550code = new byte[2];
       this.hostcode = new byte[2];
     }
   }
 
   class SYMNODE
   {
     byte[] big5code;
     byte[] ibm5550code;
 
     SYMNODE()
     {
       this.big5code = new byte[2];
       this.ibm5550code = new byte[2];
     }
   }
 }