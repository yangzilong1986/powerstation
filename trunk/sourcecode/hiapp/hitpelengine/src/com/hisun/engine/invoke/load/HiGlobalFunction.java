 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.engine.invoke.impl.HiAbstractTransaction;
 import com.hisun.engine.invoke.impl.HiFunction;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 
 public class HiGlobalFunction extends HiAbstractTransaction
 {
   public void addChilds(HiIEngineModel child)
     throws HiException
   {
     super.addChilds(child);
     if (!(child instanceof HiFunction))
       return;
     HiFunction fun = (HiFunction)child;
 
     HiContext.getCurrentContext().setProperty("GLOBALFUNCTION." + fun.getName(), child);
   }
 }