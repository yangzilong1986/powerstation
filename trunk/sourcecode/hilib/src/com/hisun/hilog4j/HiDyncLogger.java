/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HiDyncLogger extends Logger
/*    */   implements HiCloseable
/*    */ {
/*    */   protected int maxQueueSize;
/*    */ 
/*    */   public HiDyncLogger(String name)
/*    */   {
/* 17 */     super(name);
/*    */   }
/*    */ 
/*    */   public HiDyncLogger(String name, String level) {
/* 21 */     super(name, level);
/*    */   }
/*    */ 
/*    */   public HiDyncLogger(String name, Level level) {
/* 25 */     super(name, level);
/*    */   }
/*    */ 
/*    */   public HiDyncLogger(IFileName name) {
/* 29 */     super(name);
/*    */   }
/*    */ 
/*    */   public HiDyncLogger(IFileName name, String level) {
/* 33 */     super(name, level);
/*    */   }
/*    */ 
/*    */   public HiDyncLogger(IFileName name, Level level) {
/* 37 */     super(name, level);
/*    */   }
/*    */ 
/*    */   protected void construct(IFileName name, Level level) {
/* 41 */     String tmp2 = HiICSProperty.getProperty("log.limits_lines", "20");
/* 42 */     this.limitsLines = NumberUtils.toInt(tmp2);
/* 43 */     tmp2 = HiICSProperty.getProperty("log.limits_size", "20");
/* 44 */     this.limitsSize = (NumberUtils.toInt(tmp2) * 1024 * 1024);
/* 45 */     if (this.limitsSize <= 0) {
/* 46 */       this.limitsSize = DEFAULT_LIMIT_SIZE;
/*    */     }
/* 48 */     this.level = level;
/* 49 */     this.fileName = name;
/* 50 */     tmp2 = HiICSProperty.getProperty("log.max_queue_size");
/* 51 */     this.maxQueueSize = NumberUtils.toInt(tmp2);
/* 52 */     if (this.maxQueueSize <= 0) {
/* 53 */       this.maxQueueSize = DEFAULT_QUEUE_MAX_SIZE;
/*    */     }
/*    */ 
/* 56 */     this.logCache = new HiDirectLogCache(this.limitsSize);
/*    */   }
/*    */ }