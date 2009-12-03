/*    */ package com.hisun.data.object;
/*    */ 
/*    */ import com.hisun.data.cache.HiDataCache;
/*    */ import com.hisun.data.cache.HiDataCacheConfig;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiMenuInfos
/*    */ {
/*    */   private ArrayList<HiMenuItem> list;
/*    */   public final String id = "MENU_INFOS";
/*    */ 
/*    */   public HiMenuInfos()
/*    */   {
/* 15 */     this.list = null;
/* 16 */     this.id = "MENU_INFOS"; }
/*    */ 
/*    */   public void init(HiDataCacheConfig config) { this.list = config.getDataCache("MENU_INFOS").getDataList();
/*    */   }
/*    */ 
/*    */   public ArrayList getList() {
/* 22 */     return this.list;
/*    */   }
/*    */ 
/*    */   public ArrayList list(String id) {
/* 26 */     ArrayList tmpList = new ArrayList();
/* 27 */     for (int i = 0; i < this.list.size(); ++i) {
/* 28 */       HiMenuItem item = (HiMenuItem)this.list.get(i);
/* 29 */       if (StringUtils.equals(item.pid, id)) {
/* 30 */         tmpList.add(item);
/*    */       }
/*    */     }
/* 33 */     return tmpList;
/*    */   }
/*    */ 
/*    */   public HiMenuItem item(String id) {
/* 37 */     for (int i = 0; i < this.list.size(); ++i) {
/* 38 */       HiMenuItem item = (HiMenuItem)this.list.get(i);
/* 39 */       if (StringUtils.equals(item.id, id)) {
/* 40 */         return item;
/*    */       }
/*    */     }
/* 43 */     return null;
/*    */   }
/*    */ }