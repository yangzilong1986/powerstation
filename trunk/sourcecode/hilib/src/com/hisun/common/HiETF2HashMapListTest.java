/*    */ package com.hisun.common;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import java.io.PrintStream;
/*    */ import junit.framework.TestCase;
/*    */ 
/*    */ public class HiETF2HashMapListTest extends TestCase
/*    */ {
/*    */   public void testHiETF2HashMapList()
/*    */   {
/* 16 */     HiETF etf = HiETFFactory.createETF();
/* 17 */     etf.setChildValue("test01", "value01");
/* 18 */     for (int i = 0; i < 10; ++i) {
/* 19 */       HiETF group = etf.addNode("Group_" + (i + 1));
/* 20 */       group.setChildValue("test2" + i, "value2" + i);
/* 21 */       for (int j = 0; j < 10; ++j) {
/* 22 */         HiETF group1 = group.addNode("Group1_" + (j + 1));
/* 23 */         group1.setChildValue("test3" + j, "value3" + j);
/*    */       }
/*    */     }
/*    */ 
/* 27 */     HiETF2HashMapList list = new HiETF2HashMapList(etf);
/* 28 */     System.out.println(list.toString());
/* 29 */     list.map().get("hello");
/*    */   }
/*    */ }