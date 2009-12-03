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
/* 298 */     return new HiETF2HashMapList(root).map();
/*     */   }
/*     */ 
/*     */   public static HiETF fromHashMap(HashMap map) {
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSONStr(HiETF root, String JSONStr) {
/* 306 */     return fromJSON(root, JSONObject.fromObject(JSONStr));
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSONStr(String JSONStr) {
/* 310 */     return fromJSON(JSONObject.fromObject(JSONStr));
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSON(JSONObject object) {
/* 314 */     HiETF root = HiETFFactory.createETF();
/* 315 */     Iterator iter = object.keys();
/* 316 */     while (iter.hasNext()) {
/* 317 */       String key = (String)iter.next();
/* 318 */       Object o = object.get(key);
/* 319 */       if (o instanceof JSONObject) {
/* 320 */         fromJSON(root, object.getJSONObject(key));
/* 321 */       } else if (o instanceof JSONArray) {
/* 322 */         JSONArray array = (JSONArray)o;
/* 323 */         for (int i = 0; i < array.size(); ++i) {
/* 324 */           HiETF grpNod = root.addNode(key + (i + 1));
/* 325 */           fromJSON(grpNod, array.getJSONObject(i));
/*     */         }
/*     */       } else {
/* 328 */         root.setChildValue(key, o.toString());
/*     */       }
/*     */     }
/* 331 */     return root;
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSON(HiETF root, JSONObject object) {
/* 335 */     Iterator iter = object.keys();
/* 336 */     while (iter.hasNext()) {
/* 337 */       String key = (String)iter.next();
/* 338 */       Object o = object.get(key);
/* 339 */       if (o instanceof JSONObject) {
/* 340 */         HiETF grpNod = root.addNode(key);
/* 341 */         fromJSON(root, object);
/*     */       } else {
/* 343 */         root.setChildValue(key, o.toString());
/*     */       }
/*     */     }
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */   public static JSONObject toJSON(HiETF root) {
/* 350 */     return null;
/*     */   }
/*     */ 
/*     */   public static HashMap JSON2HashMap(JSONObject object) {
/* 354 */     return ((HashMap)JSONObject.toBean(object));
/*     */   }
/*     */ 
/*     */   public static JSONObject HashMap2JSON(HashMap map) {
/* 358 */     return null;
/*     */   }
/*     */ }