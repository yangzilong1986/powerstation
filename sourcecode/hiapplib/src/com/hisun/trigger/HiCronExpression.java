/*      */ package com.hisun.trigger;
/*      */ 
/*      */ import com.hisun.exception.HiException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ import java.util.TreeSet;
/*      */ 
/*      */ public class HiCronExpression
/*      */ {
/*      */   private static final int MINUTE = 1;
/*      */   private static final int HOUR = 2;
/*      */   private static final int DAY_OF_MONTH = 3;
/*      */   private static final int MONTH = 4;
/*      */   private static final int DAY_OF_WEEK = 5;
/*      */   private static final int YEAR = 6;
/*      */   private static final int WEEK_OF_MONTH = 7;
/*   36 */   private transient TreeSet daysOfMonth = new TreeSet();
/*      */ 
/*   38 */   private transient TreeSet daysOfWeek = new TreeSet();
/*      */ 
/*   40 */   private transient TreeSet hours = new TreeSet();
/*      */ 
/*   42 */   private transient TreeSet minutes = new TreeSet();
/*      */ 
/*   44 */   private transient TreeSet months = new TreeSet();
/*      */ 
/*   46 */   private transient TreeSet weeksOfMonth = new TreeSet();
/*      */ 
/*   48 */   private transient TreeSet years = new TreeSet();
/*      */   protected static final int NO_SPEC_INT = 98;
/*      */   private static final int ALL_SPEC_INT = 99;
/*   54 */   protected static final Integer NO_SPEC = new Integer(98);
/*      */ 
/*   56 */   private static final Integer ALL_SPEC = new Integer(99);
/*      */ 
/*   58 */   private TimeZone timeZone = null;
/*      */ 
/*      */   public HiCronExpression(String minute, String hour, String monthday, String month, String weekday, String monthweek)
/*      */     throws HiException
/*      */   {
/*   63 */     setMinuteVals(minute);
/*   64 */     setHourVals(hour);
/*   65 */     setDayMonthVals(monthday);
/*      */ 
/*   67 */     setMonthVals(month);
/*   68 */     setWeekDayVals(weekday);
/*   69 */     setWeekMonthVals(monthweek);
/*   70 */     setYearVals("*");
/*      */   }
/*      */ 
/*      */   private void addToSet(int val, int end, int incr, int type)
/*      */     throws HiException
/*      */   {
/*   77 */     TreeSet set = getSet(type);
/*      */ 
/*   79 */     if (type == 1)
/*      */     {
/*   82 */       if (val == 60)
/*   83 */         val = 0;
/*   84 */       if (end == 60)
/*   85 */         end = 59;
/*   86 */       if (((val >= 0) && (val <= 59) && (end <= 59)) || (val == 99))
/*      */         break label275;
/*   88 */       throw new HiException("Minute and Second values must be between 0 and 59");
/*      */     }
/*      */ 
/*   92 */     if (type == 2)
/*      */     {
/*   94 */       if (((val >= 0) && (val <= 23) && (end <= 23)) || (val == 99))
/*      */         break label275;
/*   96 */       throw new HiException("Hour values must be between 0 and 23");
/*      */     }
/*      */ 
/*   99 */     if (type == 3)
/*      */     {
/*  101 */       if (((val >= 1) && (val <= 31) && (end <= 31)) || (val == 99))
/*      */         break label275;
/*  103 */       throw new HiException("Day of month values must be between 1 and 31");
/*      */     }
/*      */ 
/*  107 */     if (type == 4)
/*      */     {
/*  109 */       if (((val >= 1) && (val <= 12) && (end <= 12)) || (val == 99))
/*      */         break label275;
/*  111 */       throw new HiException("Month values must be between 1 and 12");
/*      */     }
/*      */ 
/*  114 */     if (type == 5)
/*      */     {
/*  118 */       if (val != 99)
/*  119 */         val += 1;
/*  120 */       if (end != -1)
/*  121 */         end += 1;
/*  122 */       if (((val != 0) && (val <= 7) && (end <= 7)) || (val == 99))
/*      */         break label275;
/*  124 */       throw new HiException("WEEK values must be between 1 and 7");
/*      */     }
/*      */ 
/*  127 */     if ((type == 7) && 
/*  129 */       (((val == 0) || (val > 6) || (end > 6))) && (val != 99))
/*      */     {
/*  131 */       throw new HiException("WEEK_OF_MONTH values must be between 1 and 6");
/*      */     }
/*      */ 
/*  136 */     if ((((incr == 0) || (incr == -1))) && (val != 99))
/*      */     {
/*  138 */       if (val != -1)
/*      */       {
/*  140 */         label275: set.add(new Integer(val));
/*      */       }
/*      */       else
/*      */       {
/*  144 */         set.add(NO_SPEC);
/*      */       }
/*      */ 
/*  147 */       return;
/*      */     }
/*      */ 
/*  150 */     int startAt = val;
/*  151 */     int stopAt = end;
/*      */ 
/*  153 */     if ((val == 99) && (incr <= 0))
/*      */     {
/*  155 */       incr = 1;
/*  156 */       set.add(ALL_SPEC);
/*      */     }
/*      */ 
/*  159 */     if (type == 1)
/*      */     {
/*  161 */       if (stopAt == -1)
/*      */       {
/*  163 */         stopAt = 59;
/*      */       }
/*  165 */       if ((startAt == -1) || (startAt == 99))
/*      */       {
/*  167 */         startAt = 0;
/*      */       }
/*      */     }
/*  170 */     else if (type == 2)
/*      */     {
/*  172 */       if (stopAt == -1)
/*      */       {
/*  174 */         stopAt = 23;
/*      */       }
/*  176 */       if ((startAt == -1) || (startAt == 99))
/*      */       {
/*  178 */         startAt = 0;
/*      */       }
/*      */     }
/*  181 */     else if (type == 3)
/*      */     {
/*  183 */       if (stopAt == -1)
/*      */       {
/*  185 */         stopAt = 31;
/*      */       }
/*  187 */       if ((startAt == -1) || (startAt == 99))
/*      */       {
/*  189 */         startAt = 1;
/*      */       }
/*      */     }
/*  192 */     else if (type == 4)
/*      */     {
/*  194 */       if (stopAt == -1)
/*      */       {
/*  196 */         stopAt = 12;
/*      */       }
/*  198 */       if ((startAt == -1) || (startAt == 99))
/*      */       {
/*  200 */         startAt = 1;
/*      */       }
/*      */     }
/*  203 */     else if (type == 7)
/*      */     {
/*  205 */       if (stopAt == -1)
/*      */       {
/*  207 */         stopAt = 6;
/*      */       }
/*  209 */       if ((startAt == -1) || (startAt == 99))
/*      */       {
/*  211 */         startAt = 1;
/*      */       }
/*      */     }
/*  214 */     else if (type == 5)
/*      */     {
/*  216 */       if (stopAt == -1)
/*      */       {
/*  218 */         stopAt = 7;
/*      */       }
/*  220 */       if ((startAt == -1) || (startAt == 99))
/*      */       {
/*  222 */         startAt = 1;
/*      */       }
/*      */     }
/*  225 */     else if (type == 6)
/*      */     {
/*  227 */       if (stopAt == -1)
/*      */       {
/*  229 */         stopAt = 2099;
/*      */       }
/*  231 */       if ((startAt == -1) || (startAt == 99))
/*      */       {
/*  233 */         startAt = 1970;
/*      */       }
/*      */     }
/*      */ 
/*  237 */     for (int i = startAt; i <= stopAt; i += incr)
/*      */     {
/*  239 */       set.add(new Integer(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   private int checkNext(int pos, String s, int val, int type)
/*      */     throws HiException
/*      */   {
/*      */     ValueSet vs;
/*  247 */     int end = -1;
/*  248 */     int i = pos;
/*      */ 
/*  250 */     if (i >= s.length())
/*      */     {
/*  252 */       addToSet(val, end, -1, type);
/*  253 */       return i;
/*      */     }
/*      */ 
/*  256 */     char c = s.charAt(pos);
/*      */ 
/*  258 */     if (c == '-')
/*      */     {
/*  260 */       ++i;
/*  261 */       c = s.charAt(i);
/*  262 */       int v = Integer.parseInt(String.valueOf(c));
/*  263 */       end = v;
/*  264 */       ++i;
/*  265 */       if (i >= s.length())
/*      */       {
/*  267 */         addToSet(val, end, 1, type);
/*  268 */         return i;
/*      */       }
/*  270 */       c = s.charAt(i);
/*  271 */       if ((c >= '0') && (c <= '9'))
/*      */       {
/*  273 */         vs = getValue(v, s, i);
/*  274 */         int v1 = vs.value;
/*  275 */         end = v1;
/*  276 */         i = vs.pos;
/*      */       }
/*  278 */       if (i < s.length()) if ((c = s.charAt(i)) == '/')
/*      */         {
/*  280 */           ++i;
/*  281 */           c = s.charAt(i);
/*  282 */           int v2 = Integer.parseInt(String.valueOf(c));
/*  283 */           ++i;
/*  284 */           if (i >= s.length())
/*      */           {
/*  286 */             addToSet(val, end, v2, type);
/*  287 */             return i;
/*      */           }
/*  289 */           c = s.charAt(i);
/*  290 */           if ((c >= '0') && (c <= '9'))
/*      */           {
/*  292 */             ValueSet vs = getValue(v2, s, i);
/*  293 */             int v3 = vs.value;
/*  294 */             addToSet(val, end, v3, type);
/*  295 */             i = vs.pos;
/*  296 */             return i;
/*      */           }
/*      */ 
/*  300 */           addToSet(val, end, v2, type);
/*  301 */           return i;
/*      */         }
/*      */ 
/*      */ 
/*  306 */       addToSet(val, end, 1, type);
/*  307 */       return i;
/*      */     }
/*      */ 
/*  311 */     if (c == '/')
/*      */     {
/*  313 */       ++i;
/*  314 */       c = s.charAt(i);
/*  315 */       int v2 = Integer.parseInt(String.valueOf(c));
/*  316 */       ++i;
/*  317 */       if (i >= s.length())
/*      */       {
/*  319 */         addToSet(val, end, v2, type);
/*  320 */         return i;
/*      */       }
/*  322 */       c = s.charAt(i);
/*  323 */       if ((c >= '0') && (c <= '9'))
/*      */       {
/*  325 */         vs = getValue(v2, s, i);
/*  326 */         int v3 = vs.value;
/*  327 */         addToSet(val, end, v3, type);
/*  328 */         i = vs.pos;
/*  329 */         return i;
/*      */       }
/*      */ 
/*  333 */       throw new HiException("Unexpected character '" + c + "' after '/'");
/*      */     }
/*      */ 
/*  338 */     addToSet(val, end, 0, type);
/*  339 */     ++i;
/*  340 */     return i;
/*      */   }
/*      */ 
/*      */   private int findNextWhiteSpace(int i, String s)
/*      */   {
/*  345 */     for (; (i < s.length()) && (((s.charAt(i) != ' ') || (s.charAt(i) != '\t'))); ++i);
/*  349 */     return i;
/*      */   }
/*      */ 
/*      */   private String getExpressionSetSummary(Set set)
/*      */   {
/*  354 */     if (set.contains(ALL_SPEC))
/*      */     {
/*  356 */       return "*";
/*      */     }
/*      */ 
/*  359 */     StringBuffer buf = new StringBuffer();
/*      */ 
/*  361 */     Iterator itr = set.iterator();
/*  362 */     boolean first = true;
/*  363 */     while (itr.hasNext())
/*      */     {
/*  365 */       Integer iVal = (Integer)itr.next();
/*  366 */       String val = iVal.toString();
/*  367 */       if (!(first))
/*      */       {
/*  369 */         buf.append(",");
/*      */       }
/*  371 */       buf.append(val);
/*  372 */       first = false;
/*      */     }
/*      */ 
/*  375 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public String toString() {
/*  379 */     StringBuffer buf = new StringBuffer();
/*      */ 
/*  381 */     buf.append("minutes: ");
/*  382 */     buf.append(getExpressionSetSummary(this.minutes));
/*  383 */     buf.append("\n");
/*  384 */     buf.append("hours: ");
/*  385 */     buf.append(getExpressionSetSummary(this.hours));
/*  386 */     buf.append("\n");
/*  387 */     buf.append("daysOfMonth: ");
/*  388 */     buf.append(getExpressionSetSummary(this.daysOfMonth));
/*  389 */     buf.append("\n");
/*  390 */     buf.append("months: ");
/*  391 */     buf.append(getExpressionSetSummary(this.months));
/*  392 */     buf.append("\n");
/*  393 */     buf.append("daysOfWeek: ");
/*  394 */     buf.append(getExpressionSetSummary(this.daysOfWeek));
/*  395 */     buf.append("\n");
/*  396 */     buf.append("monthweek: ");
/*  397 */     buf.append(getExpressionSetSummary(this.weeksOfMonth));
/*  398 */     buf.append("\n");
/*  399 */     buf.append("years: ");
/*  400 */     buf.append(getExpressionSetSummary(this.years));
/*  401 */     buf.append("\n");
/*      */ 
/*  403 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   private int getLastDayOfMonth(int monthNum, int year)
/*      */   {
/*  409 */     switch (monthNum)
/*      */     {
/*      */     case 1:
/*  412 */       return 31;
/*      */     case 2:
/*  414 */       return ((isLeapYear(year)) ? 29 : 28);
/*      */     case 3:
/*  416 */       return 31;
/*      */     case 4:
/*  418 */       return 30;
/*      */     case 5:
/*  420 */       return 31;
/*      */     case 6:
/*  422 */       return 30;
/*      */     case 7:
/*  424 */       return 31;
/*      */     case 8:
/*  426 */       return 31;
/*      */     case 9:
/*  428 */       return 30;
/*      */     case 10:
/*  430 */       return 31;
/*      */     case 11:
/*  432 */       return 30;
/*      */     case 12:
/*  434 */       return 31;
/*      */     }
/*  436 */     throw new IllegalArgumentException("Illegal month number: " + monthNum);
/*      */   }
/*      */ 
/*      */   private int getNumericValue(String s, int i)
/*      */   {
/*  443 */     int endOfVal = findNextWhiteSpace(i, s);
/*  444 */     String val = s.substring(i, endOfVal);
/*  445 */     return Integer.parseInt(val);
/*      */   }
/*      */ 
/*      */   private TreeSet getSet(int type)
/*      */   {
/*  450 */     switch (type)
/*      */     {
/*      */     case 1:
/*  453 */       return this.minutes;
/*      */     case 2:
/*  455 */       return this.hours;
/*      */     case 3:
/*  457 */       return this.daysOfMonth;
/*      */     case 5:
/*  459 */       return this.daysOfWeek;
/*      */     case 7:
/*  461 */       return this.weeksOfMonth;
/*      */     case 4:
/*  463 */       return this.months;
/*      */     case 6:
/*  465 */       return this.years;
/*      */     }
/*  467 */     return null;
/*      */   }
/*      */ 
/*      */   public Date getTimeAfter(Date afterTime)
/*      */   {
/*  474 */     Calendar cl = Calendar.getInstance(getTimeZone());
/*      */ 
/*  478 */     afterTime = new Date(afterTime.getTime() + 1000L);
/*      */ 
/*  480 */     cl.setTime(afterTime);
/*  481 */     cl.set(14, 0);
/*      */ 
/*  483 */     boolean gotOne = false;
/*      */ 
/*  485 */     while (!(gotOne))
/*      */     {
/*  489 */       if (cl.get(1) > 2999) {
/*  490 */         return null;
/*      */       }
/*  492 */       SortedSet st = null;
/*  493 */       int t = 0;
/*      */ 
/*  496 */       int min = cl.get(12);
/*      */ 
/*  512 */       min = cl.get(12);
/*  513 */       int hr = cl.get(11);
/*  514 */       t = -1;
/*      */ 
/*  517 */       st = this.minutes.tailSet(new Integer(min));
/*  518 */       if ((st != null) && (st.size() != 0))
/*      */       {
/*  520 */         t = min;
/*  521 */         min = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/*  525 */         min = ((Integer)this.minutes.first()).intValue();
/*  526 */         ++hr;
/*      */       }
/*  528 */       if (min != t)
/*      */       {
/*  530 */         cl.set(13, 0);
/*  531 */         cl.set(12, min);
/*  532 */         setCalendarHour(cl, hr);
/*      */       }
/*      */ 
/*  535 */       cl.set(12, min);
/*      */ 
/*  537 */       hr = cl.get(11);
/*  538 */       int day = cl.get(5);
/*  539 */       t = -1;
/*      */ 
/*  542 */       st = this.hours.tailSet(new Integer(hr));
/*  543 */       if ((st != null) && (st.size() != 0))
/*      */       {
/*  545 */         t = hr;
/*  546 */         hr = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/*  550 */         hr = ((Integer)this.hours.first()).intValue();
/*  551 */         ++day;
/*      */       }
/*  553 */       if (hr != t)
/*      */       {
/*  555 */         cl.set(13, 0);
/*  556 */         cl.set(12, 0);
/*  557 */         cl.set(5, day);
/*  558 */         setCalendarHour(cl, hr);
/*      */       }
/*      */ 
/*  561 */       cl.set(11, hr);
/*      */ 
/*  563 */       day = cl.get(5);
/*  564 */       int mon = cl.get(2) + 1;
/*      */ 
/*  567 */       t = -1;
/*  568 */       int tmon = mon;
/*      */ 
/*  571 */       boolean dayOfMSpec = !(this.daysOfMonth.contains(NO_SPEC));
/*  572 */       if (dayOfMSpec)
/*      */       {
/*  574 */         st = this.daysOfMonth.tailSet(new Integer(day));
/*  575 */         if ((st != null) && (st.size() != 0))
/*      */         {
/*  577 */           t = day;
/*  578 */           day = ((Integer)st.first()).intValue();
/*      */         }
/*      */         else
/*      */         {
/*  582 */           day = ((Integer)this.daysOfMonth.first()).intValue();
/*  583 */           ++mon;
/*      */         }
/*      */ 
/*  586 */         if ((day != t) || (mon != tmon))
/*      */         {
/*  588 */           cl.set(13, 0);
/*  589 */           cl.set(12, 0);
/*  590 */           cl.set(11, 0);
/*  591 */           cl.set(5, day);
/*  592 */           cl.set(2, mon - 1);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  598 */       boolean dayOfWSpec = !(this.daysOfWeek.contains(NO_SPEC));
/*  599 */       if (dayOfWSpec)
/*      */       {
/*  601 */         int mDay = cl.get(5);
/*  602 */         int cDow = cl.get(7);
/*  603 */         int dow = ((Integer)this.daysOfWeek.first()).intValue();
/*      */ 
/*  605 */         st = this.daysOfWeek.tailSet(new Integer(cDow));
/*  606 */         if ((st != null) && (st.size() > 0))
/*      */         {
/*  608 */           dow = ((Integer)st.first()).intValue();
/*      */         }
/*      */ 
/*  611 */         int daysToAdd = 0;
/*  612 */         if (cDow < dow)
/*      */         {
/*  614 */           daysToAdd = dow - cDow;
/*      */         }
/*  616 */         if (cDow > dow)
/*      */         {
/*  618 */           daysToAdd = dow + 7 - cDow;
/*      */         }
/*      */ 
/*  621 */         int lDay = getLastDayOfMonth(mon, cl.get(1));
/*      */ 
/*  623 */         if (mDay + daysToAdd > lDay)
/*      */         {
/*  626 */           cl.set(13, 0);
/*  627 */           cl.set(12, 0);
/*  628 */           cl.set(11, 0);
/*  629 */           cl.set(5, 1);
/*  630 */           cl.set(2, mon);
/*      */         }
/*      */ 
/*  634 */         if (daysToAdd > 0)
/*      */         {
/*  636 */           cl.set(13, 0);
/*  637 */           cl.set(12, 0);
/*  638 */           cl.set(11, 0);
/*  639 */           cl.set(5, mDay + daysToAdd);
/*  640 */           cl.set(2, mon - 1);
/*      */         }
/*      */ 
/*  645 */         if (day > mDay) {
/*  646 */           day = mDay;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  699 */       cl.set(5, day);
/*      */ 
/*  701 */       mon = cl.get(2) + 1;
/*      */ 
/*  704 */       int year = cl.get(1);
/*  705 */       t = -1;
/*      */ 
/*  709 */       if (year > 2099)
/*      */       {
/*  711 */         return null;
/*      */       }
/*      */ 
/*  715 */       st = this.months.tailSet(new Integer(mon));
/*  716 */       if ((st != null) && (st.size() != 0))
/*      */       {
/*  718 */         t = mon;
/*  719 */         mon = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/*  723 */         mon = ((Integer)this.months.first()).intValue();
/*  724 */         ++year;
/*      */       }
/*  726 */       if (mon != t)
/*      */       {
/*  728 */         cl.set(13, 0);
/*  729 */         cl.set(12, 0);
/*  730 */         cl.set(11, 0);
/*  731 */         cl.set(5, 1);
/*  732 */         cl.set(2, mon - 1);
/*      */ 
/*  735 */         cl.set(1, year);
/*      */       }
/*      */ 
/*  738 */       cl.set(2, mon - 1);
/*      */ 
/*  742 */       year = cl.get(1);
/*  743 */       t = -1;
/*      */ 
/*  746 */       st = this.years.tailSet(new Integer(year));
/*  747 */       if ((st != null) && (st.size() != 0))
/*      */       {
/*  749 */         t = year;
/*  750 */         year = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/*  754 */         return null;
/*      */       }
/*      */ 
/*  757 */       if (year != t)
/*      */       {
/*  759 */         cl.set(13, 0);
/*  760 */         cl.set(12, 0);
/*  761 */         cl.set(11, 0);
/*  762 */         cl.set(5, 1);
/*  763 */         cl.set(2, 0);
/*      */ 
/*  766 */         cl.set(1, year);
/*      */       }
/*      */ 
/*  769 */       cl.set(1, year);
/*      */ 
/*  771 */       gotOne = true;
/*      */     }
/*      */ 
/*  774 */     return cl.getTime();
/*      */   }
/*      */ 
/*      */   public Calendar isTrigger(Date afterTime)
/*      */   {
/*  779 */     Calendar cl = Calendar.getInstance(getTimeZone());
/*      */ 
/*  783 */     afterTime = new Date(afterTime.getTime() + 1000L);
/*      */ 
/*  785 */     cl.setTime(afterTime);
/*  786 */     cl.set(14, 0);
/*      */ 
/*  788 */     boolean gotOne = false;
/*      */ 
/*  790 */     while (!(gotOne))
/*      */     {
/*  794 */       if (cl.get(1) > 2999) {
/*  795 */         return null;
/*      */       }
/*  797 */       SortedSet st = null;
/*  798 */       int t = 0;
/*      */ 
/*  801 */       int min = cl.get(12);
/*      */ 
/*  817 */       min = cl.get(12);
/*  818 */       int hr = cl.get(11);
/*  819 */       t = -1;
/*      */ 
/*  822 */       st = this.minutes.tailSet(new Integer(min));
/*  823 */       if ((st != null) && (st.size() != 0))
/*      */       {
/*  825 */         t = min;
/*  826 */         min = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/*  830 */         min = ((Integer)this.minutes.first()).intValue();
/*  831 */         ++hr;
/*      */       }
/*  833 */       if (min != t)
/*      */       {
/*  835 */         cl.set(13, 0);
/*  836 */         cl.set(12, min);
/*  837 */         setCalendarHour(cl, hr);
/*      */       }
/*      */ 
/*  840 */       cl.set(12, min);
/*      */ 
/*  842 */       hr = cl.get(11);
/*  843 */       int day = cl.get(5);
/*  844 */       t = -1;
/*      */ 
/*  847 */       st = this.hours.tailSet(new Integer(hr));
/*  848 */       if ((st != null) && (st.size() != 0))
/*      */       {
/*  850 */         t = hr;
/*  851 */         hr = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/*  855 */         hr = ((Integer)this.hours.first()).intValue();
/*  856 */         ++day;
/*      */       }
/*  858 */       if (hr != t)
/*      */       {
/*  860 */         cl.set(13, 0);
/*  861 */         cl.set(12, 0);
/*  862 */         cl.set(5, day);
/*  863 */         setCalendarHour(cl, hr);
/*      */       }
/*      */ 
/*  866 */       cl.set(11, hr);
/*      */ 
/*  868 */       day = cl.get(5);
/*  869 */       int mon = cl.get(2) + 1;
/*      */ 
/*  872 */       t = -1;
/*  873 */       int tmon = mon;
/*      */ 
/*  876 */       boolean dayOfMSpec = !(this.daysOfMonth.contains(NO_SPEC));
/*  877 */       if (dayOfMSpec)
/*      */       {
/*  879 */         st = this.daysOfMonth.tailSet(new Integer(day));
/*  880 */         if ((st != null) && (st.size() != 0))
/*      */         {
/*  882 */           t = day;
/*  883 */           day = ((Integer)st.first()).intValue();
/*      */         }
/*      */         else
/*      */         {
/*  887 */           day = ((Integer)this.daysOfMonth.first()).intValue();
/*  888 */           ++mon;
/*      */         }
/*      */ 
/*  891 */         if ((day != t) || (mon != tmon))
/*      */         {
/*  893 */           cl.set(13, 0);
/*  894 */           cl.set(12, 0);
/*  895 */           cl.set(11, 0);
/*  896 */           cl.set(5, day);
/*  897 */           cl.set(2, mon - 1);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  903 */       boolean dayOfWSpec = !(this.daysOfWeek.contains(NO_SPEC));
/*  904 */       if (dayOfWSpec)
/*      */       {
/*  906 */         int mDay = cl.get(5);
/*  907 */         int cDow = cl.get(7);
/*  908 */         int dow = ((Integer)this.daysOfWeek.first()).intValue();
/*      */ 
/*  910 */         st = this.daysOfWeek.tailSet(new Integer(cDow));
/*  911 */         if ((st != null) && (st.size() > 0))
/*      */         {
/*  913 */           dow = ((Integer)st.first()).intValue();
/*      */         }
/*      */ 
/*  916 */         int daysToAdd = 0;
/*  917 */         if (cDow < dow)
/*      */         {
/*  919 */           daysToAdd = dow - cDow;
/*      */         }
/*  921 */         if (cDow > dow)
/*      */         {
/*  923 */           daysToAdd = dow + 7 - cDow;
/*      */         }
/*      */ 
/*  926 */         int lDay = getLastDayOfMonth(mon, cl.get(1));
/*      */ 
/*  928 */         if (mDay + daysToAdd > lDay)
/*      */         {
/*  931 */           cl.set(13, 0);
/*  932 */           cl.set(12, 0);
/*  933 */           cl.set(11, 0);
/*  934 */           cl.set(5, 1);
/*  935 */           cl.set(2, mon);
/*      */         }
/*      */ 
/*  939 */         if (daysToAdd > 0)
/*      */         {
/*  941 */           cl.set(13, 0);
/*  942 */           cl.set(12, 0);
/*  943 */           cl.set(11, 0);
/*  944 */           cl.set(5, mDay + daysToAdd);
/*  945 */           cl.set(2, mon - 1);
/*      */         }
/*      */ 
/*  950 */         if (day > mDay) {
/*  951 */           day = mDay;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1004 */       cl.set(5, day);
/*      */ 
/* 1006 */       mon = cl.get(2) + 1;
/*      */ 
/* 1009 */       int year = cl.get(1);
/* 1010 */       t = -1;
/*      */ 
/* 1014 */       if (year > 2099)
/*      */       {
/* 1016 */         return null;
/*      */       }
/*      */ 
/* 1020 */       st = this.months.tailSet(new Integer(mon));
/* 1021 */       if ((st != null) && (st.size() != 0))
/*      */       {
/* 1023 */         t = mon;
/* 1024 */         mon = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/* 1028 */         mon = ((Integer)this.months.first()).intValue();
/* 1029 */         ++year;
/*      */       }
/* 1031 */       if (mon != t)
/*      */       {
/* 1033 */         cl.set(13, 0);
/* 1034 */         cl.set(12, 0);
/* 1035 */         cl.set(11, 0);
/* 1036 */         cl.set(5, 1);
/* 1037 */         cl.set(2, mon - 1);
/*      */ 
/* 1040 */         cl.set(1, year);
/*      */       }
/*      */ 
/* 1043 */       cl.set(2, mon - 1);
/*      */ 
/* 1047 */       year = cl.get(1);
/* 1048 */       t = -1;
/*      */ 
/* 1051 */       st = this.years.tailSet(new Integer(year));
/* 1052 */       if ((st != null) && (st.size() != 0))
/*      */       {
/* 1054 */         t = year;
/* 1055 */         year = ((Integer)st.first()).intValue();
/*      */       }
/*      */       else
/*      */       {
/* 1059 */         return null;
/*      */       }
/*      */ 
/* 1062 */       if (year != t)
/*      */       {
/* 1064 */         cl.set(13, 0);
/* 1065 */         cl.set(12, 0);
/* 1066 */         cl.set(11, 0);
/* 1067 */         cl.set(5, 1);
/* 1068 */         cl.set(2, 0);
/*      */ 
/* 1071 */         cl.set(1, year);
/*      */       }
/*      */ 
/* 1074 */       cl.set(1, year);
/*      */ 
/* 1076 */       gotOne = true;
/*      */     }
/*      */ 
/* 1079 */     return cl;
/*      */   }
/*      */ 
/*      */   private TimeZone getTimeZone()
/*      */   {
/* 1084 */     if (this.timeZone == null)
/*      */     {
/* 1086 */       this.timeZone = TimeZone.getDefault();
/*      */     }
/*      */ 
/* 1089 */     return this.timeZone;
/*      */   }
/*      */ 
/*      */   private ValueSet getValue(int v, String s, int i)
/*      */   {
/* 1094 */     char c = s.charAt(i);
/* 1095 */     String s1 = String.valueOf(v);
/* 1096 */     while ((c >= '0') && (c <= '9'))
/*      */     {
/* 1098 */       s1 = s1 + c;
/* 1099 */       ++i;
/* 1100 */       if (i >= s.length()) {
/*      */         break;
/*      */       }
/*      */ 
/* 1104 */       c = s.charAt(i);
/*      */     }
/* 1106 */     ValueSet val = new ValueSet();
/*      */ 
/* 1108 */     val.pos = ((i < s.length()) ? i : i + 1);
/* 1109 */     val.value = Integer.parseInt(s1);
/* 1110 */     return val;
/*      */   }
/*      */ 
/*      */   private boolean isLeapYear(int year)
/*      */   {
/* 1115 */     return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
/*      */   }
/*      */ 
/*      */   private void setCalendarHour(Calendar cal, int hour)
/*      */   {
/* 1120 */     cal.set(11, hour);
/* 1121 */     if ((cal.get(11) == hour) || (hour == 24))
/*      */       return;
/* 1123 */     cal.set(11, hour + 1);
/*      */   }
/*      */ 
/*      */   private void setDayMonthVals(String s)
/*      */     throws HiException
/*      */   {
/* 1129 */     StringTokenizer vTok = new StringTokenizer(s, ",");
/* 1130 */     while (vTok.hasMoreTokens())
/*      */     {
/* 1132 */       String v = vTok.nextToken();
/* 1133 */       storeExpressionVals(v, 3);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setHourVals(String s) throws HiException
/*      */   {
/* 1139 */     StringTokenizer vTok = new StringTokenizer(s, ",");
/* 1140 */     while (vTok.hasMoreTokens())
/*      */     {
/* 1142 */       String v = vTok.nextToken();
/* 1143 */       storeExpressionVals(v, 2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setMinuteVals(String s) throws HiException
/*      */   {
/*      */     try
/*      */     {
/* 1151 */       StringTokenizer vTok = new StringTokenizer(s, ",");
/* 1152 */       while (vTok.hasMoreTokens())
/*      */       {
/* 1154 */         String v = vTok.nextToken();
/* 1155 */         storeExpressionVals(v, 1);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1160 */       System.out.println("minute[" + s + "]");
/* 1161 */       if (e instanceof HiException) {
/* 1162 */         throw ((HiException)e);
/*      */       }
/* 1164 */       throw new HiException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setMonthVals(String s) throws HiException
/*      */   {
/* 1170 */     StringTokenizer vTok = new StringTokenizer(s, ",");
/* 1171 */     while (vTok.hasMoreTokens())
/*      */     {
/* 1173 */       String v = vTok.nextToken();
/* 1174 */       storeExpressionVals(v, 4);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setWeekDayVals(String s) throws HiException
/*      */   {
/* 1180 */     StringTokenizer vTok = new StringTokenizer(s, ",");
/* 1181 */     while (vTok.hasMoreTokens())
/*      */     {
/* 1183 */       String v = vTok.nextToken();
/* 1184 */       storeExpressionVals(v, 5);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setWeekMonthVals(String s) throws HiException
/*      */   {
/* 1190 */     StringTokenizer vTok = new StringTokenizer(s, ",");
/* 1191 */     while (vTok.hasMoreTokens())
/*      */     {
/* 1193 */       String v = vTok.nextToken();
/* 1194 */       storeExpressionVals(v, 7);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setYearVals(String s) throws HiException
/*      */   {
/* 1200 */     StringTokenizer vTok = new StringTokenizer(s, ",");
/* 1201 */     while (vTok.hasMoreTokens())
/*      */     {
/* 1203 */       String v = vTok.nextToken();
/* 1204 */       storeExpressionVals(v, 6);
/*      */     }
/*      */   }
/*      */ 
/*      */   private int storeExpressionVals(String s, int type) throws HiException
/*      */   {
/* 1210 */     int incr = 0;
/* 1211 */     int i = 0;
/* 1212 */     char c = s.charAt(i);
/*      */ 
/* 1214 */     if ((c == '*') || (c == '/'))
/*      */     {
/* 1216 */       if ((c == '*') && (i + 1 >= s.length()))
/*      */       {
/* 1218 */         addToSet(99, -1, incr, type);
/* 1219 */         return (i + 1);
/*      */       }
/* 1221 */       if ((c == '/') && (((i + 1 >= s.length()) || (s.charAt(i + 1) == ' ') || (s.charAt(i + 1) == '\t'))))
/*      */       {
/* 1225 */         throw new HiException("'/' must be followed by an integer.");
/*      */       }
/* 1227 */       if (c == '*')
/*      */       {
/* 1229 */         ++i;
/*      */       }
/* 1231 */       c = s.charAt(i);
/* 1232 */       if (c == '/')
/*      */       {
/* 1234 */         ++i;
/* 1235 */         if (i >= s.length())
/*      */         {
/* 1237 */           throw new HiException("Unexpected end of string.");
/*      */         }
/*      */ 
/* 1240 */         incr = getNumericValue(s, i);
/*      */ 
/* 1242 */         ++i;
/* 1243 */         if (incr > 10)
/*      */         {
/* 1245 */           ++i; break label338:
/*      */         }
/*      */ 
/* 1251 */         if ((incr > 23) && (type == 2))
/*      */         {
/* 1253 */           throw new HiException("Increment > 24 : " + incr);
/*      */         }
/* 1255 */         if ((incr > 31) && (type == 3))
/*      */         {
/* 1257 */           throw new HiException("Increment > 31 : " + incr);
/*      */         }
/* 1259 */         if ((incr > 6) && (type == 7))
/*      */         {
/* 1261 */           throw new HiException("Increment > 6 : " + incr);
/*      */         }
/* 1263 */         if ((incr <= 12) || (type != 4))
/*      */           break label338;
/* 1265 */         throw new HiException("Increment > 12 : " + incr);
/*      */       }
/*      */ 
/* 1270 */       incr = 1;
/*      */ 
/* 1273 */       label338: addToSet(99, -1, incr, type);
/* 1274 */       return i;
/*      */     }
/* 1276 */     if ((c >= '0') && (c <= '9'))
/*      */     {
/* 1278 */       int val = Integer.parseInt(String.valueOf(c));
/* 1279 */       ++i;
/* 1280 */       if (i >= s.length())
/*      */       {
/* 1282 */         addToSet(val, -1, -1, type);
/*      */       }
/*      */       else
/*      */       {
/* 1286 */         c = s.charAt(i);
/* 1287 */         if ((c >= '0') && (c <= '9'))
/*      */         {
/* 1289 */           ValueSet vs = getValue(val, s, i);
/* 1290 */           val = vs.value;
/* 1291 */           i = vs.pos;
/*      */         }
/* 1293 */         i = checkNext(i, s, val, type);
/* 1294 */         return i;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1299 */       throw new HiException("Unexpected character: " + c);
/*      */     }
/*      */ 
/* 1302 */     return i;
/*      */   }
/*      */ }