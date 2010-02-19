package com.hzjbbis.fk.fe.fiber;

import com.hzjbbis.fk.common.spi.abstra.BaseModule;
import com.hzjbbis.fk.utils.State;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FiberManage extends BaseModule {
    private static final Logger log = Logger.getLogger(FiberManage.class);
    private static int threadSeq = 1;

    private int minThreadSize = 2;
    private int maxThreadSize = 10;

    private List<IFiber> runOnce = new LinkedList();

    private List<IFiber> fibers = new ArrayList();

    private volatile State state = new State();
    private List<WorkThread> works = new LinkedList();

    private static final FiberManage instance = new FiberManage();

    public static final FiberManage getInstance() {
        return instance;
    }

    public void scheduleRunOnce(IFiber fiber) {
        synchronized (this.runOnce) {
            this.runOnce.add(fiber);
        }
    }

    public void schedule(IFiber fiber) {
        synchronized (this.fibers) {
            this.fibers.add(fiber);
        }
    }

    public String getName() {
        return "FiberManage";
    }

    public boolean isActive() {
        return this.state.isRunning();
    }

    public boolean start() {
        if (!(this.state.isStopped())) return false;
        this.state = State.STARTING;

        forkThreads(this.minThreadSize);
        while (this.works.size() < this.minThreadSize) {
            Thread.yield();
            try {
                Thread.sleep(100L);
            } catch (Exception localException) {
            }
        }
        this.state = State.RUNNING;
        if (log.isDebugEnabled()) log.debug("纤程池管理器【" + getName() + "】启动成功。,size=" + this.minThreadSize);
        return true;
    }

    public void stop() {
        this.state = State.STOPPING;
        synchronized (this.works) {
            for (WorkThread work : this.works) {
                work.interrupt();
            }
        }
        int cnt = 100;
        while ((cnt-- > 0) && (this.works.size() > 0)) {
            Thread.yield();
            try {
                Thread.sleep(50L);
            } catch (Exception localException) {
            }
            if (cnt < 20) continue;
            synchronized (this.works) {
                for (WorkThread work : this.works) {
                    work.interrupt();
                }
            }
        }
        if (log.isDebugEnabled()) log.debug("线程池【" + getName() + "】停止。,僵死线程数=" + this.works.size());
        this.works.clear();
        this.state = State.STOPPED;
    }

    public String getModuleType() {
        return "moduleContainer";
    }

    private void forkThreads(int delta) {
        if (delta == 0) {
            return;
        }
        if (delta > 0) {
            int maxDelta = this.maxThreadSize - this.works.size();
            delta = Math.min(maxDelta, delta);
            if ((log.isDebugEnabled()) && (delta > 0)) log.debug("调整线程池大小(+" + delta + ")");
            for (; delta > 0; --delta)
                new WorkThread();
        } else {
            delta = -delta;
            int n = this.works.size() - this.minThreadSize;
            delta = Math.min(delta, n);
            if ((log.isDebugEnabled()) && (delta > 0)) log.debug("调整线程池大小(-" + delta + ")");
            for (; delta > 0; --delta)
                this.runOnce.add(new KillThreadFiber(null));
        }
    }

    private void justThreadSize() {
        int size = (this.works.size() > 0) ? this.works.size() : 1;
        int n = this.fibers.size() / size;
        if (n > 2) {
            forkThreads(1);
        } else if (n == 0) forkThreads(-1);
    }

    private IFiber nextFiber() {
        synchronized (this.fibers) {
            if (this.fibers.size() > 0) {
                return ((IFiber) this.fibers.remove(0));
            }
            return null;
        }
    }

    public final void setMinThreadSize(int minThreadSize) {
        this.minThreadSize = minThreadSize;
    }

    public final void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

    public final void setRunOnce(List<IFiber> runOnce) {
        this.runOnce = runOnce;
    }

    public final void setFibers(List<IFiber> fibers) {
        this.fibers = fibers;
    }

    private class KillThreadException extends RuntimeException {
        private static final long serialVersionUID = -4810948231187690635L;
    }

    private class KillThreadFiber implements IFiber {
        public boolean isFiber() {
            return true;
        }

        public void runOnce() {
            throw new FiberManage.KillThreadException(FiberManage.this);
        }

        public void setFiber(boolean isFiber) {
        }
    }

    private class WorkThread extends Thread {
        public WorkThread() {
            super(FiberManage.this.getName() + "." + (FiberManage.threadSeq++));
            super.start();
        }

        // ERROR //
        public void run() {
            // Byte code:
            //   0: aload_0
            //   1: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   4: invokestatic 59	com/hzjbbis/fk/fe/fiber/FiberManage:access$2	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Ljava/util/List;
            //   7: dup
            //   8: astore_1
            //   9: monitorenter
            //   10: aload_0
            //   11: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   14: invokestatic 59	com/hzjbbis/fk/fe/fiber/FiberManage:access$2	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Ljava/util/List;
            //   17: aload_0
            //   18: invokeinterface 63 2 0
            //   23: pop
            //   24: aload_1
            //   25: monitorexit
            //   26: goto +6 -> 32
            //   29: aload_1
            //   30: monitorexit
            //   31: athrow
            //   32: invokestatic 69	com/hzjbbis/fk/fe/fiber/FiberManage:access$3	()Lorg/apache/log4j/Logger;
            //   35: new 12	java/lang/StringBuilder
            //   38: dup
            //   39: ldc 73
            //   41: invokespecial 26	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
            //   44: aload_0
            //   45: invokevirtual 75	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:getName	()Ljava/lang/String;
            //   48: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   51: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   54: invokevirtual 76	org/apache/log4j/Logger:info	(Ljava/lang/Object;)V
            //   57: iconst_0
            //   58: istore_1
            //   59: goto +240 -> 299
            //   62: aconst_null
            //   63: astore_2
            //   64: aload_0
            //   65: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   68: invokestatic 82	com/hzjbbis/fk/fe/fiber/FiberManage:access$5	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Ljava/util/List;
            //   71: dup
            //   72: astore_3
            //   73: monitorenter
            //   74: aload_0
            //   75: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   78: invokestatic 82	com/hzjbbis/fk/fe/fiber/FiberManage:access$5	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Ljava/util/List;
            //   81: invokeinterface 85 1 0
            //   86: ifle +20 -> 106
            //   89: aload_0
            //   90: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   93: invokestatic 82	com/hzjbbis/fk/fe/fiber/FiberManage:access$5	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Ljava/util/List;
            //   96: iconst_0
            //   97: invokeinterface 88 2 0
            //   102: checkcast 92	com/hzjbbis/fk/fe/fiber/IFiber
            //   105: astore_2
            //   106: aload_3
            //   107: monitorexit
            //   108: goto +6 -> 114
            //   111: aload_3
            //   112: monitorexit
            //   113: athrow
            //   114: aload_2
            //   115: ifnull +43 -> 158
            //   118: aload_2
            //   119: invokeinterface 94 1 0
            //   124: goto +34 -> 158
            //   127: astore_3
            //   128: goto +197 -> 325
            //   131: astore_3
            //   132: invokestatic 69	com/hzjbbis/fk/fe/fiber/FiberManage:access$3	()Lorg/apache/log4j/Logger;
            //   135: new 12	java/lang/StringBuilder
            //   138: dup
            //   139: ldc 97
            //   141: invokespecial 26	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
            //   144: aload_3
            //   145: invokevirtual 99	java/lang/Exception:getLocalizedMessage	()Ljava/lang/String;
            //   148: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   151: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   154: aload_3
            //   155: invokevirtual 104	org/apache/log4j/Logger:warn	(Ljava/lang/Object;Ljava/lang/Throwable;)V
            //   158: aload_0
            //   159: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   162: invokestatic 108	com/hzjbbis/fk/fe/fiber/FiberManage:access$6	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Lcom/hzjbbis/fk/fe/fiber/IFiber;
            //   165: astore_2
            //   166: aload_2
            //   167: ifnonnull +12 -> 179
            //   170: ldc2_w 112
            //   173: invokestatic 114	java/lang/Thread:sleep	(J)V
            //   176: goto +123 -> 299
            //   179: aload_2
            //   180: invokeinterface 94 1 0
            //   185: goto +54 -> 239
            //   188: astore_3
            //   189: invokestatic 69	com/hzjbbis/fk/fe/fiber/FiberManage:access$3	()Lorg/apache/log4j/Logger;
            //   192: new 12	java/lang/StringBuilder
            //   195: dup
            //   196: ldc 118
            //   198: invokespecial 26	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
            //   201: aload_3
            //   202: invokevirtual 99	java/lang/Exception:getLocalizedMessage	()Ljava/lang/String;
            //   205: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   208: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   211: aload_3
            //   212: invokevirtual 104	org/apache/log4j/Logger:warn	(Ljava/lang/Object;Ljava/lang/Throwable;)V
            //   215: aload_0
            //   216: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   219: aload_2
            //   220: invokevirtual 120	com/hzjbbis/fk/fe/fiber/FiberManage:schedule	(Lcom/hzjbbis/fk/fe/fiber/IFiber;)V
            //   223: goto +24 -> 247
            //   226: astore 4
            //   228: aload_0
            //   229: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   232: aload_2
            //   233: invokevirtual 120	com/hzjbbis/fk/fe/fiber/FiberManage:schedule	(Lcom/hzjbbis/fk/fe/fiber/IFiber;)V
            //   236: aload 4
            //   238: athrow
            //   239: aload_0
            //   240: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   243: aload_2
            //   244: invokevirtual 120	com/hzjbbis/fk/fe/fiber/FiberManage:schedule	(Lcom/hzjbbis/fk/fe/fiber/IFiber;)V
            //   247: iinc 1 1
            //   250: iload_1
            //   251: bipush 50
            //   253: if_icmple +46 -> 299
            //   256: aload_0
            //   257: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   260: invokestatic 124	com/hzjbbis/fk/fe/fiber/FiberManage:access$7	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)V
            //   263: iconst_0
            //   264: istore_1
            //   265: goto +34 -> 299
            //   268: astore_2
            //   269: goto +30 -> 299
            //   272: astore_2
            //   273: invokestatic 69	com/hzjbbis/fk/fe/fiber/FiberManage:access$3	()Lorg/apache/log4j/Logger;
            //   276: new 12	java/lang/StringBuilder
            //   279: dup
            //   280: ldc 127
            //   282: invokespecial 26	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
            //   285: aload_2
            //   286: invokevirtual 99	java/lang/Exception:getLocalizedMessage	()Ljava/lang/String;
            //   289: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   292: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   295: aload_2
            //   296: invokevirtual 104	org/apache/log4j/Logger:warn	(Ljava/lang/Object;Ljava/lang/Throwable;)V
            //   299: aload_0
            //   300: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   303: invokestatic 129	com/hzjbbis/fk/fe/fiber/FiberManage:access$4	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Lcom/hzjbbis/fk/utils/State;
            //   306: invokevirtual 133	com/hzjbbis/fk/utils/State:isStopping	()Z
            //   309: ifne +16 -> 325
            //   312: aload_0
            //   313: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   316: invokestatic 129	com/hzjbbis/fk/fe/fiber/FiberManage:access$4	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Lcom/hzjbbis/fk/utils/State;
            //   319: invokevirtual 139	com/hzjbbis/fk/utils/State:isStopped	()Z
            //   322: ifeq -260 -> 62
            //   325: aload_0
            //   326: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   329: invokestatic 59	com/hzjbbis/fk/fe/fiber/FiberManage:access$2	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Ljava/util/List;
            //   332: dup
            //   333: astore_2
            //   334: monitorenter
            //   335: aload_0
            //   336: getfield 10	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:this$0	Lcom/hzjbbis/fk/fe/fiber/FiberManage;
            //   339: invokestatic 59	com/hzjbbis/fk/fe/fiber/FiberManage:access$2	(Lcom/hzjbbis/fk/fe/fiber/FiberManage;)Ljava/util/List;
            //   342: aload_0
            //   343: invokeinterface 142 2 0
            //   348: pop
            //   349: aload_2
            //   350: monitorexit
            //   351: goto +6 -> 357
            //   354: aload_2
            //   355: monitorexit
            //   356: athrow
            //   357: invokestatic 69	com/hzjbbis/fk/fe/fiber/FiberManage:access$3	()Lorg/apache/log4j/Logger;
            //   360: new 12	java/lang/StringBuilder
            //   363: dup
            //   364: ldc 144
            //   366: invokespecial 26	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
            //   369: aload_0
            //   370: invokevirtual 75	com/hzjbbis/fk/fe/fiber/FiberManage$WorkThread:getName	()Ljava/lang/String;
            //   373: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   376: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   379: invokevirtual 76	org/apache/log4j/Logger:info	(Ljava/lang/Object;)V
            //   382: return
            //
            // Exception table:
            //   from	to	target	type
            //   10	26	29	finally
            //   29	31	29	finally
            //   74	108	111	finally
            //   111	113	111	finally
            //   118	124	127	com/hzjbbis/fk/fe/fiber/FiberManage$KillThreadException
            //   118	124	131	java/lang/Exception
            //   179	185	188	java/lang/Exception
            //   179	215	226	finally
            //   62	128	268	java/lang/InterruptedException
            //   131	176	268	java/lang/InterruptedException
            //   179	265	268	java/lang/InterruptedException
            //   62	128	272	java/lang/Exception
            //   131	176	272	java/lang/Exception
            //   179	265	272	java/lang/Exception
            //   335	351	354	finally
            //   354	356	354	finally
        }
    }
}