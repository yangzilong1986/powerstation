 package com.hisun.framework.decorator;
 
 import com.hisun.pubinterface.IHandler;
 import com.hisun.service.IObjectDecorator;
 import java.io.PrintStream;
 
 public class HandlerDecorator
   implements IObjectDecorator
 {
   public Object decorate(Object object)
   {
     System.out.println("decorate handler");
     return object;
   }
 
   public Object decorate(Object object, String style) {
     System.out.println("decorate handler with style:" + style);
     if (style.equals("Handler")) {
       decorateHandler((IHandler)object);
     }
     else if (style.equals("SystemHandler")) {
       decorateSystemHandler((IHandler)object);
     }
     return object;
   }
 
   private void decorateHandler(IHandler handler)
   {
   }
 
   private void decorateSystemHandler(IHandler handler)
   {
   }
 }