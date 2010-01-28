 package com.hisun.trigger;
 
 import com.hisun.exception.HiException;
 import java.io.PrintStream;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.Set;
 import java.util.SortedSet;
 import java.util.StringTokenizer;
 import java.util.TimeZone;
 import java.util.TreeSet;
 
 public class HiCronExpression
 {
   private static final int MINUTE = 1;
   private static final int HOUR = 2;
   private static final int DAY_OF_MONTH = 3;
   private static final int MONTH = 4;
   private static final int DAY_OF_WEEK = 5;
   private static final int YEAR = 6;
   private static final int WEEK_OF_MONTH = 7;
   private transient TreeSet daysOfMonth = new TreeSet();
 
   private transient TreeSet daysOfWeek = new TreeSet();
 
   private transient TreeSet hours = new TreeSet();
 
   private transient TreeSet minutes = new TreeSet();
 
   private transient TreeSet months = new TreeSet();
 
   private transient TreeSet weeksOfMonth = new TreeSet();
 
   private transient TreeSet years = new TreeSet();
   protected static final int NO_SPEC_INT = 98;
   private static final int ALL_SPEC_INT = 99;
   protected static final Integer NO_SPEC = new Integer(98);
 
   private static final Integer ALL_SPEC = new Integer(99);
 
   private TimeZone timeZone = null;
 
   public HiCronExpression(String minute, String hour, String monthday, String month, String weekday, String monthweek)
     throws HiException
   {
     setMinuteVals(minute);
     setHourVals(hour);
     setDayMonthVals(monthday);
 
     setMonthVals(month);
     setWeekDayVals(weekday);
     setWeekMonthVals(monthweek);
     setYearVals("*");
   }
 
   private void addToSet(int val, int end, int incr, int type)
     throws HiException
   {
     TreeSet set = getSet(type);
 
     if (type == 1)
     {
       if (val == 60)
         val = 0;
       if (end == 60)
         end = 59;
       if (((val >= 0) && (val <= 59) && (end <= 59)) || (val == 99))
         break label275;
       throw new HiException("Minute and Second values must be between 0 and 59");
     }
 
     if (type == 2)
     {
       if (((val >= 0) && (val <= 23) && (end <= 23)) || (val == 99))
         break label275;
       throw new HiException("Hour values must be between 0 and 23");
     }
 
     if (type == 3)
     {
       if (((val >= 1) && (val <= 31) && (end <= 31)) || (val == 99))
         break label275;
       throw new HiException("Day of month values must be between 1 and 31");
     }
 
     if (type == 4)
     {
       if (((val >= 1) && (val <= 12) && (end <= 12)) || (val == 99))
         break label275;
       throw new HiException("Month values must be between 1 and 12");
     }
 
     if (type == 5)
     {
       if (val != 99)
         val += 1;
       if (end != -1)
         end += 1;
       if (((val != 0) && (val <= 7) && (end <= 7)) || (val == 99))
         break label275;
       throw new HiException("WEEK values must be between 1 and 7");
     }
 
     if ((type == 7) && 
       (((val == 0) || (val > 6) || (end > 6))) && (val != 99))
     {
       throw new HiException("WEEK_OF_MONTH values must be between 1 and 6");
     }
 
     if ((((incr == 0) || (incr == -1))) && (val != 99))
     {
       if (val != -1)
       {
         label275: set.add(new Integer(val));
       }
       else
       {
         set.add(NO_SPEC);
       }
 
       return;
     }
 
     int startAt = val;
     int stopAt = end;
 
     if ((val == 99) && (incr <= 0))
     {
       incr = 1;
       set.add(ALL_SPEC);
     }
 
     if (type == 1)
     {
       if (stopAt == -1)
       {
         stopAt = 59;
       }
       if ((startAt == -1) || (startAt == 99))
       {
         startAt = 0;
       }
     }
     else if (type == 2)
     {
       if (stopAt == -1)
       {
         stopAt = 23;
       }
       if ((startAt == -1) || (startAt == 99))
       {
         startAt = 0;
       }
     }
     else if (type == 3)
     {
       if (stopAt == -1)
       {
         stopAt = 31;
       }
       if ((startAt == -1) || (startAt == 99))
       {
         startAt = 1;
       }
     }
     else if (type == 4)
     {
       if (stopAt == -1)
       {
         stopAt = 12;
       }
       if ((startAt == -1) || (startAt == 99))
       {
         startAt = 1;
       }
     }
     else if (type == 7)
     {
       if (stopAt == -1)
       {
         stopAt = 6;
       }
       if ((startAt == -1) || (startAt == 99))
       {
         startAt = 1;
       }
     }
     else if (type == 5)
     {
       if (stopAt == -1)
       {
         stopAt = 7;
       }
       if ((startAt == -1) || (startAt == 99))
       {
         startAt = 1;
       }
     }
     else if (type == 6)
     {
       if (stopAt == -1)
       {
         stopAt = 2099;
       }
       if ((startAt == -1) || (startAt == 99))
       {
         startAt = 1970;
       }
     }
 
     for (int i = startAt; i <= stopAt; i += incr)
     {
       set.add(new Integer(i));
     }
   }
 
   private int checkNext(int pos, String s, int val, int type)
     throws HiException
   {
     ValueSet vs;
     int end = -1;
     int i = pos;
 
     if (i >= s.length())
     {
       addToSet(val, end, -1, type);
       return i;
     }
 
     char c = s.charAt(pos);
 
     if (c == '-')
     {
       ++i;
       c = s.charAt(i);
       int v = Integer.parseInt(String.valueOf(c));
       end = v;
       ++i;
       if (i >= s.length())
       {
         addToSet(val, end, 1, type);
         return i;
       }
       c = s.charAt(i);
       if ((c >= '0') && (c <= '9'))
       {
         vs = getValue(v, s, i);
         int v1 = vs.value;
         end = v1;
         i = vs.pos;
       }
       if (i < s.length()) if ((c = s.charAt(i)) == '/')
         {
           ++i;
           c = s.charAt(i);
           int v2 = Integer.parseInt(String.valueOf(c));
           ++i;
           if (i >= s.length())
           {
             addToSet(val, end, v2, type);
             return i;
           }
           c = s.charAt(i);
           if ((c >= '0') && (c <= '9'))
           {
             ValueSet vs = getValue(v2, s, i);
             int v3 = vs.value;
             addToSet(val, end, v3, type);
             i = vs.pos;
             return i;
           }
 
           addToSet(val, end, v2, type);
           return i;
         }
 
 
       addToSet(val, end, 1, type);
       return i;
     }
 
     if (c == '/')
     {
       ++i;
       c = s.charAt(i);
       int v2 = Integer.parseInt(String.valueOf(c));
       ++i;
       if (i >= s.length())
       {
         addToSet(val, end, v2, type);
         return i;
       }
       c = s.charAt(i);
       if ((c >= '0') && (c <= '9'))
       {
         vs = getValue(v2, s, i);
         int v3 = vs.value;
         addToSet(val, end, v3, type);
         i = vs.pos;
         return i;
       }
 
       throw new HiException("Unexpected character '" + c + "' after '/'");
     }
 
     addToSet(val, end, 0, type);
     ++i;
     return i;
   }
 
   private int findNextWhiteSpace(int i, String s)
   {
     for (; (i < s.length()) && (((s.charAt(i) != ' ') || (s.charAt(i) != '\t'))); ++i);
     return i;
   }
 
   private String getExpressionSetSummary(Set set)
   {
     if (set.contains(ALL_SPEC))
     {
       return "*";
     }
 
     StringBuffer buf = new StringBuffer();
 
     Iterator itr = set.iterator();
     boolean first = true;
     while (itr.hasNext())
     {
       Integer iVal = (Integer)itr.next();
       String val = iVal.toString();
       if (!(first))
       {
         buf.append(",");
       }
       buf.append(val);
       first = false;
     }
 
     return buf.toString();
   }
 
   public String toString() {
     StringBuffer buf = new StringBuffer();
 
     buf.append("minutes: ");
     buf.append(getExpressionSetSummary(this.minutes));
     buf.append("\n");
     buf.append("hours: ");
     buf.append(getExpressionSetSummary(this.hours));
     buf.append("\n");
     buf.append("daysOfMonth: ");
     buf.append(getExpressionSetSummary(this.daysOfMonth));
     buf.append("\n");
     buf.append("months: ");
     buf.append(getExpressionSetSummary(this.months));
     buf.append("\n");
     buf.append("daysOfWeek: ");
     buf.append(getExpressionSetSummary(this.daysOfWeek));
     buf.append("\n");
     buf.append("monthweek: ");
     buf.append(getExpressionSetSummary(this.weeksOfMonth));
     buf.append("\n");
     buf.append("years: ");
     buf.append(getExpressionSetSummary(this.years));
     buf.append("\n");
 
     return buf.toString();
   }
 
   private int getLastDayOfMonth(int monthNum, int year)
   {
     switch (monthNum)
     {
     case 1:
       return 31;
     case 2:
       return ((isLeapYear(year)) ? 29 : 28);
     case 3:
       return 31;
     case 4:
       return 30;
     case 5:
       return 31;
     case 6:
       return 30;
     case 7:
       return 31;
     case 8:
       return 31;
     case 9:
       return 30;
     case 10:
       return 31;
     case 11:
       return 30;
     case 12:
       return 31;
     }
     throw new IllegalArgumentException("Illegal month number: " + monthNum);
   }
 
   private int getNumericValue(String s, int i)
   {
     int endOfVal = findNextWhiteSpace(i, s);
     String val = s.substring(i, endOfVal);
     return Integer.parseInt(val);
   }
 
   private TreeSet getSet(int type)
   {
     switch (type)
     {
     case 1:
       return this.minutes;
     case 2:
       return this.hours;
     case 3:
       return this.daysOfMonth;
     case 5:
       return this.daysOfWeek;
     case 7:
       return this.weeksOfMonth;
     case 4:
       return this.months;
     case 6:
       return this.years;
     }
     return null;
   }
 
   public Date getTimeAfter(Date afterTime)
   {
     Calendar cl = Calendar.getInstance(getTimeZone());
 
     afterTime = new Date(afterTime.getTime() + 1000L);
 
     cl.setTime(afterTime);
     cl.set(14, 0);
 
     boolean gotOne = false;
 
     while (!(gotOne))
     {
       if (cl.get(1) > 2999) {
         return null;
       }
       SortedSet st = null;
       int t = 0;
 
       int min = cl.get(12);
 
       min = cl.get(12);
       int hr = cl.get(11);
       t = -1;
 
       st = this.minutes.tailSet(new Integer(min));
       if ((st != null) && (st.size() != 0))
       {
         t = min;
         min = ((Integer)st.first()).intValue();
       }
       else
       {
         min = ((Integer)this.minutes.first()).intValue();
         ++hr;
       }
       if (min != t)
       {
         cl.set(13, 0);
         cl.set(12, min);
         setCalendarHour(cl, hr);
       }
 
       cl.set(12, min);
 
       hr = cl.get(11);
       int day = cl.get(5);
       t = -1;
 
       st = this.hours.tailSet(new Integer(hr));
       if ((st != null) && (st.size() != 0))
       {
         t = hr;
         hr = ((Integer)st.first()).intValue();
       }
       else
       {
         hr = ((Integer)this.hours.first()).intValue();
         ++day;
       }
       if (hr != t)
       {
         cl.set(13, 0);
         cl.set(12, 0);
         cl.set(5, day);
         setCalendarHour(cl, hr);
       }
 
       cl.set(11, hr);
 
       day = cl.get(5);
       int mon = cl.get(2) + 1;
 
       t = -1;
       int tmon = mon;
 
       boolean dayOfMSpec = !(this.daysOfMonth.contains(NO_SPEC));
       if (dayOfMSpec)
       {
         st = this.daysOfMonth.tailSet(new Integer(day));
         if ((st != null) && (st.size() != 0))
         {
           t = day;
           day = ((Integer)st.first()).intValue();
         }
         else
         {
           day = ((Integer)this.daysOfMonth.first()).intValue();
           ++mon;
         }
 
         if ((day != t) || (mon != tmon))
         {
           cl.set(13, 0);
           cl.set(12, 0);
           cl.set(11, 0);
           cl.set(5, day);
           cl.set(2, mon - 1);
         }
 
       }
 
       boolean dayOfWSpec = !(this.daysOfWeek.contains(NO_SPEC));
       if (dayOfWSpec)
       {
         int mDay = cl.get(5);
         int cDow = cl.get(7);
         int dow = ((Integer)this.daysOfWeek.first()).intValue();
 
         st = this.daysOfWeek.tailSet(new Integer(cDow));
         if ((st != null) && (st.size() > 0))
         {
           dow = ((Integer)st.first()).intValue();
         }
 
         int daysToAdd = 0;
         if (cDow < dow)
         {
           daysToAdd = dow - cDow;
         }
         if (cDow > dow)
         {
           daysToAdd = dow + 7 - cDow;
         }
 
         int lDay = getLastDayOfMonth(mon, cl.get(1));
 
         if (mDay + daysToAdd > lDay)
         {
           cl.set(13, 0);
           cl.set(12, 0);
           cl.set(11, 0);
           cl.set(5, 1);
           cl.set(2, mon);
         }
 
         if (daysToAdd > 0)
         {
           cl.set(13, 0);
           cl.set(12, 0);
           cl.set(11, 0);
           cl.set(5, mDay + daysToAdd);
           cl.set(2, mon - 1);
         }
 
         if (day > mDay) {
           day = mDay;
         }
 
       }
 
       cl.set(5, day);
 
       mon = cl.get(2) + 1;
 
       int year = cl.get(1);
       t = -1;
 
       if (year > 2099)
       {
         return null;
       }
 
       st = this.months.tailSet(new Integer(mon));
       if ((st != null) && (st.size() != 0))
       {
         t = mon;
         mon = ((Integer)st.first()).intValue();
       }
       else
       {
         mon = ((Integer)this.months.first()).intValue();
         ++year;
       }
       if (mon != t)
       {
         cl.set(13, 0);
         cl.set(12, 0);
         cl.set(11, 0);
         cl.set(5, 1);
         cl.set(2, mon - 1);
 
         cl.set(1, year);
       }
 
       cl.set(2, mon - 1);
 
       year = cl.get(1);
       t = -1;
 
       st = this.years.tailSet(new Integer(year));
       if ((st != null) && (st.size() != 0))
       {
         t = year;
         year = ((Integer)st.first()).intValue();
       }
       else
       {
         return null;
       }
 
       if (year != t)
       {
         cl.set(13, 0);
         cl.set(12, 0);
         cl.set(11, 0);
         cl.set(5, 1);
         cl.set(2, 0);
 
         cl.set(1, year);
       }
 
       cl.set(1, year);
 
       gotOne = true;
     }
 
     return cl.getTime();
   }
 
   public Calendar isTrigger(Date afterTime)
   {
     Calendar cl = Calendar.getInstance(getTimeZone());
 
     afterTime = new Date(afterTime.getTime() + 1000L);
 
     cl.setTime(afterTime);
     cl.set(14, 0);
 
     boolean gotOne = false;
 
     while (!(gotOne))
     {
       if (cl.get(1) > 2999) {
         return null;
       }
       SortedSet st = null;
       int t = 0;
 
       int min = cl.get(12);
 
       min = cl.get(12);
       int hr = cl.get(11);
       t = -1;
 
       st = this.minutes.tailSet(new Integer(min));
       if ((st != null) && (st.size() != 0))
       {
         t = min;
         min = ((Integer)st.first()).intValue();
       }
       else
       {
         min = ((Integer)this.minutes.first()).intValue();
         ++hr;
       }
       if (min != t)
       {
         cl.set(13, 0);
         cl.set(12, min);
         setCalendarHour(cl, hr);
       }
 
       cl.set(12, min);
 
       hr = cl.get(11);
       int day = cl.get(5);
       t = -1;
 
       st = this.hours.tailSet(new Integer(hr));
       if ((st != null) && (st.size() != 0))
       {
         t = hr;
         hr = ((Integer)st.first()).intValue();
       }
       else
       {
         hr = ((Integer)this.hours.first()).intValue();
         ++day;
       }
       if (hr != t)
       {
         cl.set(13, 0);
         cl.set(12, 0);
         cl.set(5, day);
         setCalendarHour(cl, hr);
       }
 
       cl.set(11, hr);
 
       day = cl.get(5);
       int mon = cl.get(2) + 1;
 
       t = -1;
       int tmon = mon;
 
       boolean dayOfMSpec = !(this.daysOfMonth.contains(NO_SPEC));
       if (dayOfMSpec)
       {
         st = this.daysOfMonth.tailSet(new Integer(day));
         if ((st != null) && (st.size() != 0))
         {
           t = day;
           day = ((Integer)st.first()).intValue();
         }
         else
         {
           day = ((Integer)this.daysOfMonth.first()).intValue();
           ++mon;
         }
 
         if ((day != t) || (mon != tmon))
         {
           cl.set(13, 0);
           cl.set(12, 0);
           cl.set(11, 0);
           cl.set(5, day);
           cl.set(2, mon - 1);
         }
 
       }
 
       boolean dayOfWSpec = !(this.daysOfWeek.contains(NO_SPEC));
       if (dayOfWSpec)
       {
         int mDay = cl.get(5);
         int cDow = cl.get(7);
         int dow = ((Integer)this.daysOfWeek.first()).intValue();
 
         st = this.daysOfWeek.tailSet(new Integer(cDow));
         if ((st != null) && (st.size() > 0))
         {
           dow = ((Integer)st.first()).intValue();
         }
 
         int daysToAdd = 0;
         if (cDow < dow)
         {
           daysToAdd = dow - cDow;
         }
         if (cDow > dow)
         {
           daysToAdd = dow + 7 - cDow;
         }
 
         int lDay = getLastDayOfMonth(mon, cl.get(1));
 
         if (mDay + daysToAdd > lDay)
         {
           cl.set(13, 0);
           cl.set(12, 0);
           cl.set(11, 0);
           cl.set(5, 1);
           cl.set(2, mon);
         }
 
         if (daysToAdd > 0)
         {
           cl.set(13, 0);
           cl.set(12, 0);
           cl.set(11, 0);
           cl.set(5, mDay + daysToAdd);
           cl.set(2, mon - 1);
         }
 
         if (day > mDay) {
           day = mDay;
         }
 
       }
 
       cl.set(5, day);
 
       mon = cl.get(2) + 1;
 
       int year = cl.get(1);
       t = -1;
 
       if (year > 2099)
       {
         return null;
       }
 
       st = this.months.tailSet(new Integer(mon));
       if ((st != null) && (st.size() != 0))
       {
         t = mon;
         mon = ((Integer)st.first()).intValue();
       }
       else
       {
         mon = ((Integer)this.months.first()).intValue();
         ++year;
       }
       if (mon != t)
       {
         cl.set(13, 0);
         cl.set(12, 0);
         cl.set(11, 0);
         cl.set(5, 1);
         cl.set(2, mon - 1);
 
         cl.set(1, year);
       }
 
       cl.set(2, mon - 1);
 
       year = cl.get(1);
       t = -1;
 
       st = this.years.tailSet(new Integer(year));
       if ((st != null) && (st.size() != 0))
       {
         t = year;
         year = ((Integer)st.first()).intValue();
       }
       else
       {
         return null;
       }
 
       if (year != t)
       {
         cl.set(13, 0);
         cl.set(12, 0);
         cl.set(11, 0);
         cl.set(5, 1);
         cl.set(2, 0);
 
         cl.set(1, year);
       }
 
       cl.set(1, year);
 
       gotOne = true;
     }
 
     return cl;
   }
 
   private TimeZone getTimeZone()
   {
     if (this.timeZone == null)
     {
       this.timeZone = TimeZone.getDefault();
     }
 
     return this.timeZone;
   }
 
   private ValueSet getValue(int v, String s, int i)
   {
     char c = s.charAt(i);
     String s1 = String.valueOf(v);
     while ((c >= '0') && (c <= '9'))
     {
       s1 = s1 + c;
       ++i;
       if (i >= s.length()) {
         break;
       }
 
       c = s.charAt(i);
     }
     ValueSet val = new ValueSet();
 
     val.pos = ((i < s.length()) ? i : i + 1);
     val.value = Integer.parseInt(s1);
     return val;
   }
 
   private boolean isLeapYear(int year)
   {
     return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
   }
 
   private void setCalendarHour(Calendar cal, int hour)
   {
     cal.set(11, hour);
     if ((cal.get(11) == hour) || (hour == 24))
       return;
     cal.set(11, hour + 1);
   }
 
   private void setDayMonthVals(String s)
     throws HiException
   {
     StringTokenizer vTok = new StringTokenizer(s, ",");
     while (vTok.hasMoreTokens())
     {
       String v = vTok.nextToken();
       storeExpressionVals(v, 3);
     }
   }
 
   private void setHourVals(String s) throws HiException
   {
     StringTokenizer vTok = new StringTokenizer(s, ",");
     while (vTok.hasMoreTokens())
     {
       String v = vTok.nextToken();
       storeExpressionVals(v, 2);
     }
   }
 
   private void setMinuteVals(String s) throws HiException
   {
     try
     {
       StringTokenizer vTok = new StringTokenizer(s, ",");
       while (vTok.hasMoreTokens())
       {
         String v = vTok.nextToken();
         storeExpressionVals(v, 1);
       }
     }
     catch (Exception e)
     {
       System.out.println("minute[" + s + "]");
       if (e instanceof HiException) {
         throw ((HiException)e);
       }
       throw new HiException(e);
     }
   }
 
   private void setMonthVals(String s) throws HiException
   {
     StringTokenizer vTok = new StringTokenizer(s, ",");
     while (vTok.hasMoreTokens())
     {
       String v = vTok.nextToken();
       storeExpressionVals(v, 4);
     }
   }
 
   private void setWeekDayVals(String s) throws HiException
   {
     StringTokenizer vTok = new StringTokenizer(s, ",");
     while (vTok.hasMoreTokens())
     {
       String v = vTok.nextToken();
       storeExpressionVals(v, 5);
     }
   }
 
   private void setWeekMonthVals(String s) throws HiException
   {
     StringTokenizer vTok = new StringTokenizer(s, ",");
     while (vTok.hasMoreTokens())
     {
       String v = vTok.nextToken();
       storeExpressionVals(v, 7);
     }
   }
 
   private void setYearVals(String s) throws HiException
   {
     StringTokenizer vTok = new StringTokenizer(s, ",");
     while (vTok.hasMoreTokens())
     {
       String v = vTok.nextToken();
       storeExpressionVals(v, 6);
     }
   }
 
   private int storeExpressionVals(String s, int type) throws HiException
   {
     int incr = 0;
     int i = 0;
     char c = s.charAt(i);
 
     if ((c == '*') || (c == '/'))
     {
       if ((c == '*') && (i + 1 >= s.length()))
       {
         addToSet(99, -1, incr, type);
         return (i + 1);
       }
       if ((c == '/') && (((i + 1 >= s.length()) || (s.charAt(i + 1) == ' ') || (s.charAt(i + 1) == '\t'))))
       {
         throw new HiException("'/' must be followed by an integer.");
       }
       if (c == '*')
       {
         ++i;
       }
       c = s.charAt(i);
       if (c == '/')
       {
         ++i;
         if (i >= s.length())
         {
           throw new HiException("Unexpected end of string.");
         }
 
         incr = getNumericValue(s, i);
 
         ++i;
         if (incr > 10)
         {
           ++i; break label338:
         }
 
         if ((incr > 23) && (type == 2))
         {
           throw new HiException("Increment > 24 : " + incr);
         }
         if ((incr > 31) && (type == 3))
         {
           throw new HiException("Increment > 31 : " + incr);
         }
         if ((incr > 6) && (type == 7))
         {
           throw new HiException("Increment > 6 : " + incr);
         }
         if ((incr <= 12) || (type != 4))
           break label338;
         throw new HiException("Increment > 12 : " + incr);
       }
 
       incr = 1;
 
       label338: addToSet(99, -1, incr, type);
       return i;
     }
     if ((c >= '0') && (c <= '9'))
     {
       int val = Integer.parseInt(String.valueOf(c));
       ++i;
       if (i >= s.length())
       {
         addToSet(val, -1, -1, type);
       }
       else
       {
         c = s.charAt(i);
         if ((c >= '0') && (c <= '9'))
         {
           ValueSet vs = getValue(val, s, i);
           val = vs.value;
           i = vs.pos;
         }
         i = checkNext(i, s, val, type);
         return i;
       }
     }
     else
     {
       throw new HiException("Unexpected character: " + c);
     }
 
     return i;
   }
 }