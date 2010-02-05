 package org.apache.hivemind.impl;
 
 import java.util.Locale;
 import org.apache.hivemind.internal.MessageFinder;
 import org.apache.hivemind.service.ThreadLocale;
 import org.apache.hivemind.util.Defense;
 
 public class ModuleMessages extends AbstractMessages
 {
   private MessageFinder _messageFinder;
   private ThreadLocale _threadLocale;
 
   public ModuleMessages(MessageFinder messageFinder, ThreadLocale threadLocale)
   {
     Defense.notNull(messageFinder, "messageFinder");
     Defense.notNull(threadLocale, "threadLocale");
 
     this._messageFinder = messageFinder;
     this._threadLocale = threadLocale;
   }
 
   protected Locale getLocale()
   {
     return this._threadLocale.getLocale();
   }
 
   protected String findMessage(String key)
   {
     return this._messageFinder.getMessage(key, getLocale());
   }
 }