 package com.hisun.common.util;
 
 import java.math.BigInteger;
 
 public final class HiStringUtilExt
 {
   public static final int TRUNCAMENTO = 1;
   public static final int ARREDONDA = 2;
   public static final int ESQUERDA = 3;
   public static final int DIREITA = 4;
 
   public static String allTrim(String str)
   {
     return str.trim();
   }
 
   public static byte[] asciiToByteArray(String str)
   {
     char[] aux = str.toCharArray();
     byte[] result = new byte[aux.length];
     for (int i = 0; i < aux.length; ++i) {
       result[i] = (byte)aux[i];
     }
     return result;
   }
 
   public static String byteToString(byte[] b)
   {
     return new String(b);
   }
 
   public static String centraliza(String str, int tamanho)
   {
     int falta = tamanho - str.length();
     if (falta >= 0) {
       if (falta % 2 == 1) {
         str = str + " ";
         --falta;
       }
       while (falta > 0) {
         str = " " + str + " ";
         falta -= 2;
       }
     }
     return str;
   }
 
   public static String completaString(String texto, int tamanho, char complemento, int lado)
   {
     return completaString(texto, tamanho, complemento, lado == 3);
   }
 
   public static String completaString(String texto, int tamanho, char complemento, boolean esquerda)
   {
     while (texto.length() < tamanho) {
       while (esquerda) {
         texto = complemento + texto;
       }
       texto = texto + complemento;
     }
 
     return texto;
   }
 
   public static String copia(char caractere, int quantidade)
   {
     char[] c = new char[quantidade];
     for (int i = 0; i < quantidade; ++i) {
       c[i] = caractere;
     }
     return new String(c);
   }
 
   public static String copia(String str, int quantidade)
   {
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < quantidade; ++i) {
       sb.append(str);
     }
     return sb.toString();
   }
 
   public static int count(char c, String s)
   {
     int retorno = 0;
 
     for (int i = 0; i < s.length(); ++i) {
       if (s.charAt(i) == c) {
         ++retorno;
       }
     }
 
     return retorno;
   }
 
   public static String duplicaChar(char caracter, String texto)
   {
     try
     {
       int i = 0;
       while (i < texto.length()) {
         if (texto.charAt(i++) == caracter);
         if (i != texto.length()) {
           texto = texto.substring(0, i) + texto.substring(i - 1, texto.length());
         }
         else {
           texto = texto + caracter;
         }
         ++i;
       }
     }
     catch (Throwable e) {
       e.printStackTrace();
     }
 
     return texto;
   }
 
   public static String lTrim(String str)
   {
     String result = "";
 
     for (int i = 0; i < str.length(); ++i) {
       if (!(Character.isWhitespace(str.charAt(i)))) {
         break;
       }
     }
     for (; i < str.length(); ++i) {
       result = result + str.charAt(i);
     }
     return result;
   }
 
   public static String numeroToString(int numero, int decimais, int tamanho, String separadorDecimal, String separadorMilhares, int modoTruncamento)
   {
     return numeroToString(numero, decimais, tamanho, separadorDecimal, separadorMilhares, modoTruncamento == 1);
   }
 
   public static String numeroToString(int numero, int decimais, int tamanho, String separadorDecimal, String separadorMilhares, boolean truncar)
   {
     long num;
     long multiplicador = 1L;
     boolean negativo = numero < 0.0D;
     if (negativo) {
       numero = -numero;
     }
     for (int i = 0; i < decimais; ++i) {
       multiplicador *= 10L;
     }
     numero = (int)(numero * multiplicador);
 
     if (truncar)
       num = Math.round(Math.floor(numero));
     else {
       num = Math.round(numero);
     }
     long frac = num % multiplicador;
     String strFrac = completaString("" + frac, decimais, '0', 3);
     if (strFrac.length() > decimais) {
       strFrac = strFrac.substring(0, decimais);
     }
     num /= multiplicador;
     String result = "";
     int tamMilhar = 3 + separadorMilhares.length();
     do {
       String auxMilhar = (num % 1000L) + separadorMilhares;
       while ((num >= 1000L) && 
         (auxMilhar.length() < tamMilhar)) {
         auxMilhar = "0" + auxMilhar;
       }
 
       result = auxMilhar + result;
       num /= 1000L; }
     while (num > 0L);
     result = result.substring(0, result.length() - separadorMilhares.length());
 
     result = result + separadorDecimal + strFrac;
     result = completaString(result, (negativo) ? tamanho - 1 : tamanho, '0', 3);
 
     if (negativo) {
       result = "-" + result;
     }
     return result;
   }
 
   public static String removeChar(String str, char ch)
   {
     String aux = str;
     int iaux = aux.indexOf("" + ch);
     while (iaux != -1) {
       aux = aux.substring(0, iaux) + aux.substring(iaux + 1);
       iaux = aux.indexOf("" + ch);
     }
     return aux;
   }
 
   public static String reverse(String str)
   {
     return new StringBuffer(str).reverse().toString();
   }
 
   public static String rTrim(String str)
   {
     String result = str;
 
     int tam = result.length();
     while ((tam > 0) && 
       (Character.isWhitespace(result.charAt(tam - 1)))) {
       result = result.substring(0, tam - 1);
       --tam;
     }
 
     return result;
   }
 
   public static String sprintf(String texto, Object[] args)
   {
     StringBuffer sb = new StringBuffer();
     int arg = 0;
     int i = 0;
     while (i < texto.length()) {
       char c = texto.charAt(i);
       if (c != '%') {
         sb.append(c);
         ++i;
       } else if (texto.charAt(i + 1) == '%') {
         sb.append('%');
         ++i;
       }
       else
       {
         int num;
         String sinal;
         String str;
         boolean flagPlus = false; boolean flagMinus = false;
         int width = -1;
         boolean flagZeroEsquerda = false;
         int precision = -1;
         boolean flagLong = false;
         char type = ' ';
         ++i;
         char aux = texto.charAt(i);
         if (aux == '+') {
           flagPlus = true;
           ++i;
           aux = texto.charAt(i);
         } else if (aux == '-') {
           flagMinus = true;
           ++i;
           aux = texto.charAt(i);
         }
         if (Character.isDigit(aux)) {
           num = 0;
           if (aux == '0') {
             flagZeroEsquerda = true;
           }
           while (Character.isDigit(aux)) {
             num = 10 * num + aux - 48;
             aux = texto.charAt(++i);
           }
           width = num;
         }
         if (aux == '*') {
           width = ((Integer)(Integer)args[(arg++)]).intValue();
           aux = texto.charAt(++i);
         }
         if (aux == '.') {
           aux = texto.charAt(++i);
           if (aux == '*') {
             precision = ((Integer)args[(arg++)]).intValue();
             aux = texto.charAt(++i);
           } else if (Character.isDigit(aux)) {
             num = 0;
             while (Character.isDigit(aux)) {
               num = 10 * num + aux - 48;
               aux = texto.charAt(++i);
             }
             precision = num;
           } else {
             precision = 0;
           }
         }
         if ((aux == 'l') || (aux == 'L')) {
           flagLong = true;
           aux = texto.charAt(++i);
         }
         type = aux;
         ++i;
         switch (type) {
         case 'd':
           long num;
           try {
             num = ((Number)args[arg]).longValue();
           } catch (ClassCastException e) {
             num = 0L;
           }
           ++arg;
           if (precision == -1) {
             precision = 1;
           }
           if ((num == 0L) && (precision == 0)) {
             continue;
           }
           negativo = num < 0L;
           if (negativo) {
             num = -num;
           }
           sinal = (flagPlus) ? "+" : (negativo) ? "-" : "";
           str = "" + num;
           while (str.length() < precision) {
             str = "0" + str;
           }
           width -= sinal.length();
 
           while (str.length() < width) {
             if (flagMinus)
               str = str + " ";
             if (flagZeroEsquerda) {
               str = "0" + str;
             }
             str = " " + str;
           }
 
           str = sinal + str;
           sb.append(str);
           break;
         case 'f':
           double num;
           try
           {
             num = ((Number)args[arg]).doubleValue();
           } catch (ClassCastException negativo) {
             num = 0.0D;
           }
           ++arg;
           if (precision == -1) {
             precision = 6;
           }
           negativo = num < 0.0D;
           if (negativo) {
             num = -num;
           }
           sinal = (flagPlus) ? "+" : (negativo) ? "-" : "";
           for (int j = 0; j < precision; ++j) {
             num *= 10.0D;
           }
           num += 0.5D;
           for (j = 0; j < precision; ++j) {
             num /= 10.0D;
           }
           str = "" + Math.round(Math.floor(num));
           if (precision != 0) {
             str = str + ".";
             double dAux = num - Math.floor(num);
             for (int j = 0; j < precision; ++j) {
               dAux *= 10.0D;
               long n = Math.round(Math.floor(dAux));
               str = str + n;
               dAux -= n;
             }
           }
           width -= sinal.length();
 
           while (str.length() < width) {
             if (flagMinus)
               str = str + " ";
             if (flagZeroEsquerda) {
               str = "0" + str;
             }
             str = " " + str;
           }
 
           str = sinal + str;
           sb.append(str);
           break;
         case 's':
           String str = (String)args[(arg++)];
           if ((precision != -1) && (str.length() > precision)) {
             str = str.substring(0, precision);
           }
           while (str.length() < width) {
             if (flagMinus) {
               str = str + " ";
             }
             str = " " + str;
           }
 
           sb.append(str);
           break;
         case 'X':
         case 'x':
           boolean flagMaiusc = type == 'X';
           int letraBase = (flagMaiusc) ? 65 : 97;
           int num = ((Integer)args[(arg++)]).intValue();
           if (precision == -1) {
             precision = 1;
           }
           if ((num == 0) && (precision == 0)) {
             continue;
           }
           if (num < 0)
             continue;
           String str = (num == 0) ? "0" : "";
           while (num > 0) {
             int n16 = num % 16;
             if (n16 < 10)
               str = n16 + str;
             else {
               str = (char)(letraBase + n16 - 10) + str;
             }
             num /= 16;
           }
           while (str.length() < precision) {
             str = "0" + str;
           }
           while (str.length() < width) {
             if (flagMinus) {
               str = str + " ";
             }
             str = ((flagZeroEsquerda) ? "0" : " ") + str;
           }
 
           sb.append(str);
           break;
         case 'c':
           char caractere;
           Object obj = args[(arg++)];
 
           if (obj instanceof Character)
             caractere = ((Character)obj).charValue();
           else {
             try {
               caractere = (char)((Number)obj).intValue();
             } catch (ClassCastException num) {
               caractere = '0';
             }
           }
           sb.append(caractere);
         }
       }
 
     }
 
     return sb.toString();
   }
 
   public static String sprintf(String texto, Object arg0) {
     return sprintf(texto, new Object[] { arg0 });
   }
 
   public static String sprintf(String texto, Object arg0, Object arg1) {
     return sprintf(texto, new Object[] { arg0, arg1 });
   }
 
   public static String sprintf(String texto, Object arg0, Object arg1, Object arg2)
   {
     return sprintf(texto, new Object[] { arg0, arg1, arg2 });
   }
 
   public static String sprintf(String texto, Object arg0, Object arg1, Object arg2, Object arg3)
   {
     return sprintf(texto, new Object[] { arg0, arg1, arg2, arg3 });
   }
 
   public static String trocaChar(String str, char antigo, char novo)
   {
     StringBuffer sb = new StringBuffer(str);
     for (int i = 0; i < str.length(); ++i) {
       if (sb.charAt(i) == antigo) {
         sb.setCharAt(i, novo);
       }
     }
     return sb.toString();
   }
 
   public static String zerosEsquerda(String str)
   {
     BigInteger i = new BigInteger(str);
     return i.toString();
   }
 
   public static String completeNumberZERO(long numOri, int tam)
   {
     String strOri = String.valueOf(numOri);
     return wrappedCompleteChar(strOri, tam, '0', true);
   }
 
   public static String completeStringCHAR(String strOri, int tam)
   {
     if (strOri != null) {
       return wrappedCompleteChar(strOri, tam, ' ', false);
     }
     return wrappedCompleteChar("null", tam, ' ', false);
   }
 
   public static String completeStringZERO(String strOri, int tam)
   {
     if (strOri != null) {
       return wrappedCompleteChar(strOri, tam, '0', true);
     }
     return wrappedCompleteChar("null", tam, '0', true);
   }
 
   private static String wrappedCompleteChar(String strOri, int tam, char cs, boolean left)
   {
     return ((strOri.length() < tam) ? completaString(strOri, tam, cs, left) : strOri.substring(0, tam));
   }
 }