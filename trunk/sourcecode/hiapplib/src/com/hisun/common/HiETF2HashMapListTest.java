/*    */ package com.hisun.common;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import junit.framework.TestCase;
/*    */ 
/*    */ public class HiETF2HashMapListTest extends TestCase
/*    */ {
/*    */   public void testHiETF2HashMapList()
/*    */     throws IOException
/*    */   {
/* 19 */     HiETF etf = HiETFFactory.createETF();
/* 20 */     etf.setChildValue("test01", "value01");
/* 21 */     for (int i = 0; i < 10; ++i) {
/* 22 */       HiETF group = etf.addNode("Group_" + (i + 1));
/* 23 */       group.setChildValue("test2" + i, "value2" + i);
/* 24 */       for (int j = 0; j < 10; ++j) {
/* 25 */         HiETF group1 = group.addNode("Group1_" + (j + 1));
/* 26 */         group1.setChildValue("test3" + j, "value3" + j);
/*    */       }
/*    */     }
/* 29 */     HiETF2HashMapList list = new HiETF2HashMapList(etf);
/* 30 */     System.out.println(list.toString());
/* 31 */     list.map().get("hello");
/*    */   }
/*    */ }