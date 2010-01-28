 package com.hisun.sw.big5;
 
 import com.hisun.sw.HiChar;
 
 public class HiBIG5SwCode
 {
   public static int HostToClient(byte[] buffer, byte[] temp)
   {
     return HostToClient(buffer, buffer.length, temp);
   }
 
   public static int HostToClient(byte[] buffer, int len, byte[] temp)
   {
     int ilen = len;
     int olen = 0;
 
     int ipos = 0;
     int i = 0; for (int j = 0; i < ilen; )
     {
       if ((buffer[i] == 1) || (buffer[i] == 2)) {
         temp[(j++)] = buffer[(i++)];
       }
 
       if (buffer[i] == 14)
       {
         int icount = 1; int iend = 0; ipos = i + 1; for (int templen = 1; ipos < ilen; ) {
           if (buffer[ipos] == 14) {
             ++icount;
             if (icount > 1) {
               break;
             }
           }
           if (buffer[ipos] == 15) {
             --icount;
           }
 
           if (buffer[ipos] == 2) {
             iend = 1;
             break;
           }
           ++ipos;
           ++templen;
         }
 
         if (icount % 2 == 1) {
           iend = 0;
         }
         if (iend == 1)
           toGB(buffer, i, temp, j, templen);
         else {
           toGB2(buffer, i, temp, j, templen);
         }
         i += templen;
         j += templen;
       }
 
       if ((i + 1 < ilen) && (buffer[(i + 1)] == 1)) {
         temp[j] = signedConvert((short)buffer[i]);
         ++i;
         ++j;
       }
       temp[(j++)] = bEbcdToAscii(buffer[(i++)]);
     }
 
     return j;
   }
 
   public static int ClientToHost(byte[] buffer, byte[] temp)
   {
     return ClientToHost(buffer, buffer.length, temp);
   }
 
   public static int ClientToHost(byte[] buffer, int len, byte[] temp)
   {
     int ilen = len;
     int olen = 0;
 
     int i = 0; for (int j = 0; i < ilen; )
     {
       short s = (short)(buffer[i] & 0xFF);
       if ((buffer[i] == 1) || (buffer[i] == 2)) {
         ++i;
       }
 
       if (s >= 128)
       {
         int ipos = i + 1; for (int templen = 1; ipos < ilen; ) {
           if (buffer[ipos] == 2) {
             break;
           }
 
           ++ipos;
           ++templen;
         }
 
         toDBCS(buffer, i, temp, j, templen);
         i += templen;
         j += templen;
       }
 
       if ((i + 1 < ilen) && (buffer[(i + 1)] == 1))
       {
         if (buffer[i] >= 112)
           temp[j] = (byte)(buffer[i] + 96);
         else {
           temp[j] = (byte)(buffer[i] + 144);
         }
         ++i;
         ++j;
       }
       else {
         temp[(j++)] = bAsciiToEbcd(buffer[(i++)]);
       }
     }
     return j;
   }
 
   public static void toGB(byte[] from, int off1, byte[] to, int off2, int len)
   {
     int iPos = off2;
     int i = off1;
     int iLen = len + off1;
     while (true) { if (i >= iLen) break label239;
       if (from[i] == 15) {
         ++i;
       }
 
       if ((from[i] == 14) && (((from[i] != 14) || (!(Unmatch0f(i + 1, iLen, from)))))) {
         break;
       }
       to[(iPos++)] = bEbcdToAscii(from[(i++)]);
     }
 
     ++i;
     while (true) { while (true) { do do while (true) { if (i < iLen - 1);
               if (from[i] != 64) break;
               to[(iPos++)] = 32;
               ++i;
             }
 
           while (from[i] == 15); while (from[i] == 14);
 
         if ((from[(i + 1)] != 15) && (from[(i + 1)] != 14))
           break;
         to[(iPos++)] = 63;
         ++i;
       }
 
       HiChar tmpChar = new HiChar();
       HiHost2B5.host2b5(new HiChar(from[i], from[(i + 1)]), tmpChar);
       to[iPos] = tmpChar.highByte;
       to[(iPos + 1)] = tmpChar.lowByte;
       i += 2;
       iPos += 2;
     }
 
     while (iPos < iLen)
       label239: to[(iPos++)] = 32;
   }
 
   public static void toGB2(byte[] from, int off1, byte[] to, int off2, int len)
   {
     int iPos = off2;
     int iLen = len;
     int i = off1;
     while (i < iLen + off1) {
       if (from[i] == 15) {
         to[(iPos++)] = 32;
         ++i;
       }
 
       if ((from[i] != 14) || ((from[i] == 14) && (Unmatch0f(i + 1, iLen, from))))
       {
         to[iPos] = bEbcdToAscii(from[i]);
         ++i;
         ++iPos;
       }
 
       to[(iPos++)] = 32;
       ++i;
       while (i < iLen - 1) {
         if (from[i] == 64) {
           to[(iPos++)] = 32;
           ++i;
         }
 
         if (from[i] == 15) break; if (from[i] == 14)
           break;
         if ((from[(i + 1)] == 15) || (from[(i + 1)] == 14))
         {
           to[(iPos++)] = 63;
           ++i;
           break;
         }
 
         HiChar tmpChar = new HiChar();
         HiHost2B5.host2b5(new HiChar(from[i], from[(i + 1)]), tmpChar);
         to[iPos] = tmpChar.highByte;
         to[(iPos + 1)] = tmpChar.lowByte;
 
         i += 2;
         iPos += 2;
       }
       to[(iPos++)] = 32;
       ++i;
     }
 
     while (i < iLen) {
       to[(iPos++)] = 32;
       ++i;
     }
   }
 
   public static void toDBCS(byte[] from, int off1, byte[] to, int off2, int len)
   {
     int iLen = len;
     int iPos = off2;
     int i = off1;
     while (i < iLen + off1) {
       short s = (short)(from[i] & 0xFF);
       if (s < 128) {
         to[(iPos++)] = bAsciiToEbcd(from[(i++)]);
       }
 
       if (i - off1 > iLen - 4) {
         break;
       }
       to[(iPos++)] = 14;
       while (s >= 128) {
         HiChar tmpChar = new HiChar();
         HiB52Host.b52host(new HiChar(from[i], from[(i + 1)]), tmpChar);
 
         to[iPos] = tmpChar.highByte;
         to[(iPos + 1)] = tmpChar.lowByte;
         i += 2;
         iPos += 2;
 
         s = (short)(from[i] & 0xFF);
         if (iPos - off1 > iLen - 3) {
           break;
         }
 
       }
 
       to[(iPos++)] = 15;
     }
 
     while (iPos < iLen)
       to[(iPos++)] = 64;
   }
 
   public static byte bEbcdToAscii(byte bEbcd)
   {
     byte[] ebc_to_asc = { 32, 1, 2, 3, 0, 9, 0, 0, 0, 0, 0, 11, 12, 13, 32, 32, 16, 17, 18, 19, 0, 21, 8, 0, 24, 25, 0, 0, 28, 29, 30, 31, 0, 0, 0, 0, 0, 10, 23, 27, 0, 0, 0, 0, 0, 5, 6, 7, 0, 0, 22, 0, 0, 0, 0, 4, 0, 0, 0, 0, 20, 21, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 91, 46, 60, 40, 43, 124, 38, 32, 32, 32, 32, 32, 32, 32, 32, 32, 33, 36, 42, 41, 59, 94, 45, 47, 32, 32, 32, 32, 32, 32, 32, 32, 93, 44, 37, 95, 62, 63, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 58, 35, 64, 39, 61, 34, 32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 32, 32, 32, 32, 32, 32, 32, 106, 107, 108, 109, 110, 111, 112, 113, 114, 32, 32, 32, 32, 32, 32, 32, 126, 115, 116, 117, 118, 119, 120, 121, 122, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 123, 65, 66, 67, 68, 69, 70, 71, 72, 73, 32, 32, 32, 32, 32, 32, 125, 74, 75, 76, 77, 78, 79, 80, 81, 82, 32, 32, 32, 32, 32, 32, 92, 32, 83, 84, 85, 86, 87, 88, 89, 90, 32, 32, 32, 32, 32, 32, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 32, 32, 32, 32, 32, 32 };
 
     return ebc_to_asc[(bEbcd & 0xFF)];
   }
 
   public static byte bAsciiToEbcd(byte bAscii)
   {
     byte[] asc_to_ebc = { 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 12, 13, 63, 63, 16, 17, 18, 19, 60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 30, 31, 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, 122, 94, 76, 126, 110, 111, 124, -63, -62, -61, -60, -59, -58, -57, -56, -55, -47, -46, -45, -44, -43, -42, -41, -40, -39, -30, -29, -28, -27, -26, -25, -24, -23, 74, -32, 106, 95, 109, 121, -127, -126, -125, -124, -123, -122, -121, -120, -119, -111, -110, -109, -108, -107, -106, -105, -104, -103, -94, -93, -92, -91, -90, -89, -88, -87, -64, 79, -48, -95, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 74, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64 };
 
     return asc_to_ebc[(bAscii & 0xFF)];
   }
 
   private static boolean Unmatch0f(int iBeginPos, int iBufLen, byte[] buf)
   {
     int i = iBeginPos;
     while (i < iBufLen) {
       if (buf[i] == 14)
         return true;
       if (buf[i] == 15)
         return false;
       ++i;
     }
 
     return true;
   }
 
   public static byte signedConvert(short bFrom)
   {
     short bTo;
     if ((bFrom >= 240) && (bFrom <= 249))
       bTo = (short)(byte)(bFrom - 192);
     else if ((bFrom >= 192) && (bFrom <= 201))
       bTo = (short)(byte)(bFrom - 144);
     else if ((bFrom >= 208) && (bFrom <= 217))
       bTo = (short)(byte)(bFrom - 96);
     else {
       bTo = bFrom;
     }
     return (byte)bTo;
   }
 }