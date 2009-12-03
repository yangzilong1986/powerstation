/*     */ package com.hisun.web.tag;
/*     */ 
/*     */ import com.hisun.common.HiETF2HashMapList;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ 
/*     */ class HiETFUtils
/*     */ {
/*     */   public static HashMap toHashMap(HiETF root)
/*     */   {
/* 333 */     return new HiETF2HashMapList(root).map();
/*     */   }
/*     */ 
/*     */   public static HiETF fromHashMap(HashMap map) {
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSONStr(HiETF root, String JSONStr) {
/* 341 */     return fromJSON(root, JSONObject.fromObject(JSONStr));
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSONStr(String JSONStr) {
/* 345 */     return fromJSON(JSONObject.fromObject(JSONStr));
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSON(JSONObject object) {
/* 349 */     HiETF root = HiETFFactory.createETF();
/* 350 */     Iterator iter = object.keys();
/* 351 */     while (iter.hasNext()) {
/* 352 */       String key = (String)iter.next();
/* 353 */       Object o = object.get(key);
/* 354 */       if (o instanceof JSONObject) {
/* 355 */         fromJSON(root, object.getJSONObject(key));
/* 356 */       } else if (o instanceof JSONArray) {
/* 357 */         JSONArray array = (JSONArray)o;
/* 358 */         for (int i = 0; i < array.size(); ++i) {
/* 359 */           HiETF grpNod = root.addNode(key + (i + 1));
/* 360 */           fromJSON(grpNod, array.getJSONObject(i));
/*     */         }
/*     */       } else {
/* 363 */         root.setChildValue(key, o.toString());
/*     */       }
/*     */     }
/* 366 */     return root;
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSON(HiETF root, JSONObject object) {
/* 370 */     Iterator iter = object.keys();
/* 371 */     while (iter.hasNext()) {
/* 372 */       String key = (String)iter.next();
/* 373 */       Object o = object.get(key);
/* 374 */       if (o instanceof JSONObject) {
/* 375 */         HiETF grpNod = root.addNode(key);
/* 376 */         fromJSON(root, object);
/*     */       } else {
/* 378 */         root.setChildValue(key, o.toString());
/*     */       }
/*     */     }
/* 381 */     return null;
/*     */   }
/*     */ 
/*     */   public static JSONObject toJSON(HiETF root) {
/* 385 */     return null;
/*     */   }
/*     */ 
/*     */   public static HashMap JSON2HashMap(JSONObject object) {
/* 389 */     return ((HashMap)JSONObject.toBean(object));
/*     */   }
/*     */ 
/*     */   public static JSONObject HashMap2JSON(HashMap map) {
/* 393 */     return null;
/*     */   }
/*     */ }