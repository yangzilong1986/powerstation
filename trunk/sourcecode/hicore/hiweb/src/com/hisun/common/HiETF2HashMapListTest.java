 package com.hisun.common;

 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import junit.framework.TestCase;
 
 public class HiETF2HashMapListTest extends TestCase
 {
   public void testHiETF2HashMapList()
   {
     HiETF etf = HiETFFactory.createETF();
     etf.setChildValue("test01", "value01");
     for (int i = 0; i < 10; ++i) {
       HiETF group = etf.addNode("Group_" + (i + 1));
       group.setChildValue("test2" + i, "value2" + i);
       for (int j = 0; j < 10; ++j) {
         HiETF group1 = group.addNode("Group1_" + (j + 1));
         group1.setChildValue("test3" + j, "value3" + j);
       }
     }
 
     HiETF2HashMapList list = new HiETF2HashMapList(etf);
     System.out.println(list.toString());
     list.map().get("hello");
   }
 }