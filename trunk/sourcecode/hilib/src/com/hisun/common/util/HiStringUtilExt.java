/*     */ package com.hisun.common.util;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ public final class HiStringUtilExt
/*     */ {
/*     */   public static final int TRUNCAMENTO = 1;
/*     */   public static final int ARREDONDA = 2;
/*     */   public static final int ESQUERDA = 3;
/*     */   public static final int DIREITA = 4;
/*     */ 
/*     */   public static String allTrim(String str)
/*     */   {
/*  49 */     return str.trim();
/*     */   }
/*     */ 
/*     */   public static byte[] asciiToByteArray(String str)
/*     */   {
/*  63 */     char[] aux = str.toCharArray();
/*  64 */     byte[] result = new byte[aux.length];
/*  65 */     for (int i = 0; i < aux.length; ++i) {
/*  66 */       result[i] = (byte)aux[i];
/*     */     }
/*  68 */     return result;
/*     */   }
/*     */ 
/*     */   public static String byteToString(byte[] b)
/*     */   {
/*  81 */     return new String(b);
/*     */   }
/*     */ 
/*     */   public static String centraliza(String str, int tamanho)
/*     */   {
/* 100 */     int falta = tamanho - str.length();
/* 101 */     if (falta >= 0) {
/* 102 */       if (falta % 2 == 1) {
/* 103 */         str = str + " ";
/* 104 */         --falta;
/*     */       }
/* 106 */       while (falta > 0) {
/* 107 */         str = " " + str + " ";
/* 108 */         falta -= 2;
/*     */       }
/*     */     }
/* 111 */     return str;
/*     */   }
/*     */ 
/*     */   public static String completaString(String texto, int tamanho, char complemento, int lado)
/*     */   {
/* 135 */     return completaString(texto, tamanho, complemento, lado == 3);
/*     */   }
/*     */ 
/*     */   public static String completaString(String texto, int tamanho, char complemento, boolean esquerda)
/*     */   {
/* 159 */     while (texto.length() < tamanho) {
/* 160 */       while (esquerda) {
/* 161 */         texto = complemento + texto;
/*     */       }
/* 163 */       texto = texto + complemento;
/*     */     }
/*     */ 
/* 166 */     return texto;
/*     */   }
/*     */ 
/*     */   public static String copia(char caractere, int quantidade)
/*     */   {
/* 181 */     char[] c = new char[quantidade];
/* 182 */     for (int i = 0; i < quantidade; ++i) {
/* 183 */       c[i] = caractere;
/*     */     }
/* 185 */     return new String(c);
/*     */   }
/*     */ 
/*     */   public static String copia(String str, int quantidade)
/*     */   {
/* 201 */     StringBuffer sb = new StringBuffer();
/* 202 */     for (int i = 0; i < quantidade; ++i) {
/* 203 */       sb.append(str);
/*     */     }
/* 205 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static int count(char c, String s)
/*     */   {
/* 221 */     int retorno = 0;
/*     */ 
/* 223 */     for (int i = 0; i < s.length(); ++i) {
/* 224 */       if (s.charAt(i) == c) {
/* 225 */         ++retorno;
/*     */       }
/*     */     }
/*     */ 
/* 229 */     return retorno;
/*     */   }
/*     */ 
/*     */   public static String duplicaChar(char caracter, String texto)
/*     */   {
/*     */     try
/*     */     {
/* 248 */       int i = 0;
/* 249 */       while (i < texto.length()) {
/* 250 */         if (texto.charAt(i++) == caracter);
/* 251 */         if (i != texto.length()) {
/* 252 */           texto = texto.substring(0, i) + texto.substring(i - 1, texto.length());
/*     */         }
/*     */         else {
/* 255 */           texto = texto + caracter;
/*     */         }
/* 257 */         ++i;
/*     */       }
/*     */     }
/*     */     catch (Throwable e) {
/* 261 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 264 */     return texto;
/*     */   }
/*     */ 
/*     */   public static String lTrim(String str)
/*     */   {
/* 278 */     String result = "";
/*     */ 
/* 281 */     for (int i = 0; i < str.length(); ++i) {
/* 282 */       if (!(Character.isWhitespace(str.charAt(i)))) {
/*     */         break;
/*     */       }
/*     */     }
/* 286 */     for (; i < str.length(); ++i) {
/* 287 */       result = result + str.charAt(i);
/*     */     }
/* 289 */     return result;
/*     */   }
/*     */ 
/*     */   public static String numeroToString(int numero, int decimais, int tamanho, String separadorDecimal, String separadorMilhares, int modoTruncamento)
/*     */   {
/* 322 */     return numeroToString(numero, decimais, tamanho, separadorDecimal, separadorMilhares, modoTruncamento == 1);
/*     */   }
/*     */ 
/*     */   public static String numeroToString(int numero, int decimais, int tamanho, String separadorDecimal, String separadorMilhares, boolean truncar)
/*     */   {
/*     */     long num;
/* 355 */     long multiplicador = 1L;
/* 356 */     boolean negativo = numero < 0.0D;
/* 357 */     if (negativo) {
/* 358 */       numero = -numero;
/*     */     }
/* 360 */     for (int i = 0; i < decimais; ++i) {
/* 361 */       multiplicador *= 10L;
/*     */     }
/* 363 */     numero = (int)(numero * multiplicador);
/*     */ 
/* 365 */     if (truncar)
/* 366 */       num = Math.round(Math.floor(numero));
/*     */     else {
/* 368 */       num = Math.round(numero);
/*     */     }
/* 370 */     long frac = num % multiplicador;
/* 371 */     String strFrac = completaString("" + frac, decimais, '0', 3);
/* 372 */     if (strFrac.length() > decimais) {
/* 373 */       strFrac = strFrac.substring(0, decimais);
/*     */     }
/* 375 */     num /= multiplicador;
/* 376 */     String result = "";
/* 377 */     int tamMilhar = 3 + separadorMilhares.length();
/*     */     do {
/* 379 */       String auxMilhar = (num % 1000L) + separadorMilhares;
/* 380 */       while ((num >= 1000L) && 
/* 381 */         (auxMilhar.length() < tamMilhar)) {
/* 382 */         auxMilhar = "0" + auxMilhar;
/*     */       }
/*     */ 
/* 385 */       result = auxMilhar + result;
/* 386 */       num /= 1000L; }
/* 387 */     while (num > 0L);
/* 388 */     result = result.substring(0, result.length() - separadorMilhares.length());
/*     */ 
/* 390 */     result = result + separadorDecimal + strFrac;
/* 391 */     result = completaString(result, (negativo) ? tamanho - 1 : tamanho, '0', 3);
/*     */ 
/* 393 */     if (negativo) {
/* 394 */       result = "-" + result;
/*     */     }
/* 396 */     return result;
/*     */   }
/*     */ 
/*     */   public static String removeChar(String str, char ch)
/*     */   {
/* 411 */     String aux = str;
/* 412 */     int iaux = aux.indexOf("" + ch);
/* 413 */     while (iaux != -1) {
/* 414 */       aux = aux.substring(0, iaux) + aux.substring(iaux + 1);
/* 415 */       iaux = aux.indexOf("" + ch);
/*     */     }
/* 417 */     return aux;
/*     */   }
/*     */ 
/*     */   public static String reverse(String str)
/*     */   {
/* 431 */     return new StringBuffer(str).reverse().toString();
/*     */   }
/*     */ 
/*     */   public static String rTrim(String str)
/*     */   {
/* 445 */     String result = str;
/*     */ 
/* 447 */     int tam = result.length();
/* 448 */     while ((tam > 0) && 
/* 449 */       (Character.isWhitespace(result.charAt(tam - 1)))) {
/* 450 */       result = result.substring(0, tam - 1);
/* 451 */       --tam;
/*     */     }
/*     */ 
/* 454 */     return result;
/*     */   }
/*     */ 
/*     */   public static String sprintf(String texto, Object[] args)
/*     */   {
/* 599 */     StringBuffer sb = new StringBuffer();
/* 600 */     int arg = 0;
/* 601 */     int i = 0;
/* 602 */     while (i < texto.length()) {
/* 603 */       char c = texto.charAt(i);
/* 604 */       if (c != '%') {
/* 605 */         sb.append(c);
/* 606 */         ++i;
/* 607 */       } else if (texto.charAt(i + 1) == '%') {
/* 608 */         sb.append('%');
/* 609 */         ++i;
/*     */       }
/*     */       else
/*     */       {
/*     */         int num;
/*     */         String sinal;
/*     */         String str;
/* 611 */         boolean flagPlus = false; boolean flagMinus = false;
/* 612 */         int width = -1;
/* 613 */         boolean flagZeroEsquerda = false;
/* 614 */         int precision = -1;
/* 615 */         boolean flagLong = false;
/* 616 */         char type = ' ';
/* 617 */         ++i;
/* 618 */         char aux = texto.charAt(i);
/* 619 */         if (aux == '+') {
/* 620 */           flagPlus = true;
/* 621 */           ++i;
/* 622 */           aux = texto.charAt(i);
/* 623 */         } else if (aux == '-') {
/* 624 */           flagMinus = true;
/* 625 */           ++i;
/* 626 */           aux = texto.charAt(i);
/*     */         }
/* 628 */         if (Character.isDigit(aux)) {
/* 629 */           num = 0;
/* 630 */           if (aux == '0') {
/* 631 */             flagZeroEsquerda = true;
/*     */           }
/* 633 */           while (Character.isDigit(aux)) {
/* 634 */             num = 10 * num + aux - 48;
/* 635 */             aux = texto.charAt(++i);
/*     */           }
/* 637 */           width = num;
/*     */         }
/* 639 */         if (aux == '*') {
/* 640 */           width = ((Integer)(Integer)args[(arg++)]).intValue();
/* 641 */           aux = texto.charAt(++i);
/*     */         }
/* 643 */         if (aux == '.') {
/* 644 */           aux = texto.charAt(++i);
/* 645 */           if (aux == '*') {
/* 646 */             precision = ((Integer)args[(arg++)]).intValue();
/* 647 */             aux = texto.charAt(++i);
/* 648 */           } else if (Character.isDigit(aux)) {
/* 649 */             num = 0;
/* 650 */             while (Character.isDigit(aux)) {
/* 651 */               num = 10 * num + aux - 48;
/* 652 */               aux = texto.charAt(++i);
/*     */             }
/* 654 */             precision = num;
/*     */           } else {
/* 656 */             precision = 0;
/*     */           }
/*     */         }
/* 659 */         if ((aux == 'l') || (aux == 'L')) {
/* 660 */           flagLong = true;
/* 661 */           aux = texto.charAt(++i);
/*     */         }
/* 663 */         type = aux;
/* 664 */         ++i;
/* 665 */         switch (type) {
/*     */         case 'd':
/*     */           long num;
/*     */           try {
/* 669 */             num = ((Number)args[arg]).longValue();
/*     */           } catch (ClassCastException e) {
/* 671 */             num = 0L;
/*     */           }
/* 673 */           ++arg;
/* 674 */           if (precision == -1) {
/* 675 */             precision = 1;
/*     */           }
/* 677 */           if ((num == 0L) && (precision == 0)) {
/*     */             continue;
/*     */           }
/* 680 */           negativo = num < 0L;
/* 681 */           if (negativo) {
/* 682 */             num = -num;
/*     */           }
/* 684 */           sinal = (flagPlus) ? "+" : (negativo) ? "-" : "";
/* 685 */           str = "" + num;
/* 686 */           while (str.length() < precision) {
/* 687 */             str = "0" + str;
/*     */           }
/* 689 */           width -= sinal.length();
/*     */ 
/* 691 */           while (str.length() < width) {
/* 692 */             if (flagMinus)
/* 693 */               str = str + " ";
/* 694 */             if (flagZeroEsquerda) {
/* 695 */               str = "0" + str;
/*     */             }
/* 697 */             str = " " + str;
/*     */           }
/*     */ 
/* 700 */           str = sinal + str;
/* 701 */           sb.append(str);
/* 702 */           break;
/*     */         case 'f':
/*     */           double num;
/*     */           try
/*     */           {
/* 707 */             num = ((Number)args[arg]).doubleValue();
/*     */           } catch (ClassCastException negativo) {
/* 709 */             num = 0.0D;
/*     */           }
/* 711 */           ++arg;
/* 712 */           if (precision == -1) {
/* 713 */             precision = 6;
/*     */           }
/* 715 */           negativo = num < 0.0D;
/* 716 */           if (negativo) {
/* 717 */             num = -num;
/*     */           }
/* 719 */           sinal = (flagPlus) ? "+" : (negativo) ? "-" : "";
/* 720 */           for (int j = 0; j < precision; ++j) {
/* 721 */             num *= 10.0D;
/*     */           }
/* 723 */           num += 0.5D;
/* 724 */           for (j = 0; j < precision; ++j) {
/* 725 */             num /= 10.0D;
/*     */           }
/* 727 */           str = "" + Math.round(Math.floor(num));
/* 728 */           if (precision != 0) {
/* 729 */             str = str + ".";
/* 730 */             double dAux = num - Math.floor(num);
/* 731 */             for (int j = 0; j < precision; ++j) {
/* 732 */               dAux *= 10.0D;
/* 733 */               long n = Math.round(Math.floor(dAux));
/* 734 */               str = str + n;
/* 735 */               dAux -= n;
/*     */             }
/*     */           }
/* 738 */           width -= sinal.length();
/*     */ 
/* 740 */           while (str.length() < width) {
/* 741 */             if (flagMinus)
/* 742 */               str = str + " ";
/* 743 */             if (flagZeroEsquerda) {
/* 744 */               str = "0" + str;
/*     */             }
/* 746 */             str = " " + str;
/*     */           }
/*     */ 
/* 749 */           str = sinal + str;
/* 750 */           sb.append(str);
/* 751 */           break;
/*     */         case 's':
/* 754 */           String str = (String)args[(arg++)];
/* 755 */           if ((precision != -1) && (str.length() > precision)) {
/* 756 */             str = str.substring(0, precision);
/*     */           }
/* 758 */           while (str.length() < width) {
/* 759 */             if (flagMinus) {
/* 760 */               str = str + " ";
/*     */             }
/* 762 */             str = " " + str;
/*     */           }
/*     */ 
/* 765 */           sb.append(str);
/* 766 */           break;
/*     */         case 'X':
/*     */         case 'x':
/* 770 */           boolean flagMaiusc = type == 'X';
/* 771 */           int letraBase = (flagMaiusc) ? 65 : 97;
/* 772 */           int num = ((Integer)args[(arg++)]).intValue();
/* 773 */           if (precision == -1) {
/* 774 */             precision = 1;
/*     */           }
/* 776 */           if ((num == 0) && (precision == 0)) {
/*     */             continue;
/*     */           }
/* 779 */           if (num < 0)
/*     */             continue;
/* 781 */           String str = (num == 0) ? "0" : "";
/* 782 */           while (num > 0) {
/* 783 */             int n16 = num % 16;
/* 784 */             if (n16 < 10)
/* 785 */               str = n16 + str;
/*     */             else {
/* 787 */               str = (char)(letraBase + n16 - 10) + str;
/*     */             }
/* 789 */             num /= 16;
/*     */           }
/* 791 */           while (str.length() < precision) {
/* 792 */             str = "0" + str;
/*     */           }
/* 794 */           while (str.length() < width) {
/* 795 */             if (flagMinus) {
/* 796 */               str = str + " ";
/*     */             }
/* 798 */             str = ((flagZeroEsquerda) ? "0" : " ") + str;
/*     */           }
/*     */ 
/* 801 */           sb.append(str);
/* 802 */           break;
/*     */         case 'c':
/*     */           char caractere;
/* 805 */           Object obj = args[(arg++)];
/*     */ 
/* 807 */           if (obj instanceof Character)
/* 808 */             caractere = ((Character)obj).charValue();
/*     */           else {
/*     */             try {
/* 811 */               caractere = (char)((Number)obj).intValue();
/*     */             } catch (ClassCastException num) {
/* 813 */               caractere = '0';
/*     */             }
/*     */           }
/* 816 */           sb.append(caractere);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 822 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String sprintf(String texto, Object arg0) {
/* 826 */     return sprintf(texto, new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */   public static String sprintf(String texto, Object arg0, Object arg1) {
/* 830 */     return sprintf(texto, new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public static String sprintf(String texto, Object arg0, Object arg1, Object arg2)
/*     */   {
/* 835 */     return sprintf(texto, new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public static String sprintf(String texto, Object arg0, Object arg1, Object arg2, Object arg3)
/*     */   {
/* 840 */     return sprintf(texto, new Object[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public static String trocaChar(String str, char antigo, char novo)
/*     */   {
/* 857 */     StringBuffer sb = new StringBuffer(str);
/* 858 */     for (int i = 0; i < str.length(); ++i) {
/* 859 */       if (sb.charAt(i) == antigo) {
/* 860 */         sb.setCharAt(i, novo);
/*     */       }
/*     */     }
/* 863 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String zerosEsquerda(String str)
/*     */   {
/* 875 */     BigInteger i = new BigInteger(str);
/* 876 */     return i.toString();
/*     */   }
/*     */ 
/*     */   public static String completeNumberZERO(long numOri, int tam)
/*     */   {
/* 892 */     String strOri = String.valueOf(numOri);
/* 893 */     return wrappedCompleteChar(strOri, tam, '0', true);
/*     */   }
/*     */ 
/*     */   public static String completeStringCHAR(String strOri, int tam)
/*     */   {
/* 909 */     if (strOri != null) {
/* 910 */       return wrappedCompleteChar(strOri, tam, ' ', false);
/*     */     }
/* 912 */     return wrappedCompleteChar("null", tam, ' ', false);
/*     */   }
/*     */ 
/*     */   public static String completeStringZERO(String strOri, int tam)
/*     */   {
/* 929 */     if (strOri != null) {
/* 930 */       return wrappedCompleteChar(strOri, tam, '0', true);
/*     */     }
/* 932 */     return wrappedCompleteChar("null", tam, '0', true);
/*     */   }
/*     */ 
/*     */   private static String wrappedCompleteChar(String strOri, int tam, char cs, boolean left)
/*     */   {
/* 939 */     return ((strOri.length() < tam) ? completaString(strOri, tam, cs, left) : strOri.substring(0, tam));
/*     */   }
/*     */ }