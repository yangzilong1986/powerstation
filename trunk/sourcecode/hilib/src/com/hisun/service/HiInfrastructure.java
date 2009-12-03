/*    */ package com.hisun.service;
/*    */ 
/*    */ import com.hisun.event.UpdateListener;
/*    */ import com.hisun.service.imp.UpdateListenerHubImpl;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import com.hisun.util.HiThreadPool;
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.Executors;
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
/*    */ import org.apache.hivemind.Registry;
/*    */ import org.apache.hivemind.impl.RegistryBuilder;
/*    */ 
/*    */ public class HiInfrastructure
/*    */ {
/*    */   private static Registry registry;
/*    */   private static UpdateListenerHub hub;
/*    */   private static ScheduledExecutorService executor;
/* 39 */   private static int file_check_interval = 60000;
/*    */ 
/*    */   public static Registry getRegistry()
/*    */   {
/* 20 */     if (registry == null)
/* 21 */       registry = RegistryBuilder.constructDefaultRegistry();
/* 22 */     return registry;
/*    */   }
/*    */ 
/*    */   public static Object getService(Class serviceInterface) {
/* 26 */     return getRegistry().getService(serviceInterface);
/*    */   }
/*    */ 
/*    */   public static HiThreadPool getThreadPoolService(String id) {
/* 30 */     IThreadPoolFactory factory = (IThreadPoolFactory)getRegistry().getService(IThreadPoolFactory.class);
/*    */ 
/* 32 */     HiThreadPool pool = factory.getThreadPool(id);
/* 33 */     pool.setMaximumPoolSize(10);
/* 34 */     return pool;
/*    */   }
/*    */ 
/*    */   public static void addUpdateListener(UpdateListener listener)
/*    */   {
/* 58 */     hub.addUpdateListener(listener);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 42 */     hub = new UpdateListenerHubImpl();
/* 43 */     if (HiICSProperty.isDevEnv()) {
/* 44 */       executor = Executors.newSingleThreadScheduledExecutor();
/* 45 */       Runnable checker = new Runnable()
/*    */       {
/*    */         public void run() {
/* 48 */           HiInfrastructure.hub.fireUpdateEvent();
/*    */         }
/*    */       };
/* 52 */       executor.scheduleWithFixedDelay(checker, 30000L, file_check_interval, TimeUnit.MILLISECONDS);
/*    */     }
/*    */   }
/*    */ }