package com.hisun.framework.handler;

import com.hisun.pubinterface.IHandler;

public class HiWaitMsgHandler implements IHandler {
    // ERROR //
    public void process(com.hisun.message.HiMessageContext ctx) throws com.hisun.exception.HiException {
        // Byte code:
        //   0: aload_1
        //   1: invokevirtual 2	com/hisun/message/HiMessageContext:getCurrentMsg	()Lcom/hisun/message/HiMessage;
        //   4: astore_2
        //   5: aload_2
        //   6: invokevirtual 3	com/hisun/message/HiMessage:getHead	()Ljava/util/Map;
        //   9: astore_3
        //   10: aload_3
        //   11: dup
        //   12: astore 4
        //   14: monitorenter
        //   15: aload_2
        //   16: ldc 4
        //   18: invokevirtual 5	com/hisun/message/HiMessage:getHeadItem	(Ljava/lang/String;)Ljava/lang/String;
        //   21: astore 5
        //   23: ldc 6
        //   25: aload 5
        //   27: invokevirtual 7	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   30: ifeq +21 -> 51
        //   33: aload_2
        //   34: ldc 4
        //   36: invokevirtual 8	com/hisun/message/HiMessage:delHeadItem	(Ljava/lang/String;)V
        //   39: aload_2
        //   40: ldc 9
        //   42: ldc 10
        //   44: invokevirtual 11	com/hisun/message/HiMessage:setHeadItem	(Ljava/lang/String;Ljava/lang/Object;)V
        //   47: aload 4
        //   49: monitorexit
        //   50: return
        //   51: aload_1
        //   52: ldc 12
        //   54: invokevirtual 13	com/hisun/message/HiMessageContext:getProperty	(Ljava/lang/String;)Ljava/lang/Object;
        //   57: checkcast 14	java/lang/Integer
        //   60: astore 6
        //   62: aload 6
        //   64: ifnonnull +10 -> 74
        //   67: aload_3
        //   68: invokevirtual 15	java/lang/Object:wait	()V
        //   71: goto +47 -> 118
        //   74: aload_3
        //   75: aload 6
        //   77: invokevirtual 16	java/lang/Integer:intValue	()I
        //   80: i2l
        //   81: invokevirtual 17	java/lang/Object:wait	(J)V
        //   84: aload_2
        //   85: ldc 4
        //   87: invokevirtual 5	com/hisun/message/HiMessage:getHeadItem	(Ljava/lang/String;)Ljava/lang/String;
        //   90: astore 5
        //   92: ldc 6
        //   94: aload 5
        //   96: invokevirtual 7	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   99: ifne +13 -> 112
        //   102: new 18	com.hisun.exception.HiException
        //   105: dup
        //   106: ldc 19
        //   108: invokespecial 20	com.hisun.exception.HiException:<init>	(Ljava/lang/String;)V
        //   111: athrow
        //   112: aload_2
        //   113: ldc 4
        //   115: invokevirtual 8	com/hisun/message/HiMessage:delHeadItem	(Ljava/lang/String;)V
        //   118: aload_2
        //   119: ldc 9
        //   121: ldc 10
        //   123: invokevirtual 11	com/hisun/message/HiMessage:setHeadItem	(Ljava/lang/String;Ljava/lang/Object;)V
        //   126: goto +15 -> 141
        //   129: astore 5
        //   131: new 18	com.hisun.exception.HiException
        //   134: dup
        //   135: aload 5
        //   137: invokespecial 22	com.hisun.exception.HiException:<init>	(Ljava/lang/Throwable;)V
        //   140: athrow
        //   141: aload 4
        //   143: monitorexit
        //   144: goto +11 -> 155
        //   147: astore 7
        //   149: aload 4
        //   151: monitorexit
        //   152: aload 7
        //   154: athrow
        //   155: return
        //
        // Exception table:
        //   from	to	target	type
        //   15	47	129	java/lang/InterruptedException
        //   51	126	129	java/lang/InterruptedException
        //   15	50	147	finally
        //   51	144	147	finally
        //   147	152	147	finally
    }
}