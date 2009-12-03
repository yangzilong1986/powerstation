/*    */ package com.hisun.common;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Iterator;
/*    */ import net.sf.json.JSONArray;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class TestJSONObject
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 17 */     JSONObject object = JSONObject.fromObject("{items:[{'value': 'New', 'onclick': 'CreateNewDoc()'}, {'value': 'Open', 'onclick': 'OpenDoc()'},{'value': 'Close', 'onclick': 'CloseDoc()'}]}");
/*    */ 
/* 19 */     JSONArray array = object.getJSONArray("items");
/* 20 */     for (int i = 0; i < array.size(); ++i) {
/* 21 */       JSONObject obj = (JSONObject)array.get(i);
/* 22 */       obj.accumulate("hello", "world");
/* 23 */       System.out.println(obj.getInt("value"));
/* 24 */       System.out.println(obj);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main01(String[] args)
/*    */   {
/* 34 */     JSONObject object = JSONObject.fromObject("{'menu': { 'id': 'file', 'value': 'File:', 'popup': { 'menuitem': [ {'onclick': 'CreateNewDoc()'}, {'value': 'Open', 'onclick': 'OpenDoc()'}, {'value': 'Close', 'onclick': 'CloseDoc()'} ]} }}");
/*    */ 
/* 36 */     dumpObject(object);
/*    */   }
/*    */ 
/*    */   public static void dumpObject(JSONObject object) {
/* 40 */     Iterator iter = object.keys();
/* 41 */     while (iter.hasNext()) {
/* 42 */       String key = (String)iter.next();
/* 43 */       Object o = object.get(key);
/* 44 */       if (o instanceof JSONObject) {
/* 45 */         System.out.println(key + "is object");
/* 46 */         dumpObject((JSONObject)o);
/* 47 */       } else if (o instanceof JSONArray) {
/* 48 */         System.out.println(key + "is array");
/* 49 */         JSONArray array = (JSONArray)o;
/* 50 */         for (int i = 0; i < array.size(); ++i)
/* 51 */           dumpObject(array.getJSONObject(i));
/*    */       }
/*    */       else {
/* 54 */         System.out.println(key + ":" + o.toString());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main021(String[] args) {
/* 60 */     JSONObject jsonObject = new JSONObject();
/* 61 */     jsonObject.put("a", Integer.valueOf(1));
/* 62 */     jsonObject.put("b", Double.valueOf(1.1D));
/* 63 */     jsonObject.put("c", Long.valueOf(1L));
/* 64 */     jsonObject.put("d", "test");
/* 65 */     jsonObject.put("e", Boolean.valueOf(true));
/* 66 */     System.out.println(jsonObject);
/*    */ 
/* 68 */     JSONObject object = JSONObject.fromObject("{d:'test',e:true,b:1.1,c:1,a:1}");
/*    */ 
/* 70 */     System.out.println(object);
/* 71 */     System.out.println(object.getInt("a"));
/* 72 */     System.out.println(object.getDouble("b"));
/* 73 */     System.out.println(object.getLong("c"));
/* 74 */     System.out.println(object.getString("d"));
/* 75 */     System.out.println(object.getBoolean("e"));
/*    */   }
/*    */ }