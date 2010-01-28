 package com.hisun.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import java.io.StringReader;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.Node;
 import org.dom4j.io.SAXReader;
 
 public class HiLogIDHandler
   implements IHandler, IServerInitListener
 {
   private static final String DEFAULT_ID = "DUMMY";
   private static final String LOG_ID_MAP = "__LOG_ID_MAP";
   private String type;
   private String name;
   private int pos;
   private int length;
   private int offset;
   private String deli;
 
   public static synchronized ConcurrentHashMap getLogIDMap()
   {
     HiContext ctx = HiContext.getRootContext();
     if (!(ctx.containsProperty("__LOG_ID_MAP"))) {
       ctx.setProperty("__LOG_ID_MAP", new ConcurrentHashMap());
     }
     return ((ConcurrentHashMap)ctx.getProperty("__LOG_ID_MAP"));
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name)
   {
     this.name = name;
   }
 
   public int getPos()
   {
     return this.pos;
   }
 
   public void setPos(int pos)
   {
     this.pos = pos;
   }
 
   public int getLength()
   {
     return this.length;
   }
 
   public void setLength(int length)
   {
     this.length = length;
   }
 
   public int getOffset()
   {
     return this.offset;
   }
 
   public void setOffset(int offset)
   {
     this.offset = offset;
   }
 
   public void process(HiMessageContext arg0)
     throws HiException
   {
     HiByteBuffer buf;
     String id = "DUMMY";
     HiMessage msg = arg0.getCurrentMsg();
     if ("text/xml".equalsIgnoreCase(this.type)) {
       buf = (HiByteBuffer)msg.getBody();
       SAXReader reader = new SAXReader();
       try {
         Document doc = reader.read(new StringReader(buf.toString()));
         Element root = doc.getRootElement();
         Node node = root.selectSingleNode(this.name);
         if (node != null) {
           id = node.getText();
           if (id != null) {
             id = id.trim();
           }
         }
 
         if (id == null)
           id = "DUMMY";
       }
       catch (DocumentException e)
       {
         e.printStackTrace();
       }
     } else if ("text/etf".equalsIgnoreCase(this.type)) {
       HiETF etf = msg.getETFBody();
       id = etf.getGrandChildValue(this.name);
       if (id == null)
         id = "DUMMY";
     }
     else if ("msg".equalsIgnoreCase(this.type)) {
       Object o = msg.getHeadItem(this.name);
       if (o != null) {
         id = o.toString();
       }
       if (id == null)
         id = "DUMMY";
     }
     else {
       buf = (HiByteBuffer)msg.getBody();
       if (StringUtils.isNotBlank(this.deli)) {
         String[] tmps = StringUtils.split(buf.toString(), this.deli);
         if (this.pos <= tmps.length) {
           id = tmps[(this.pos - 1)];
         }
       }
       else if (this.offset + this.length <= buf.length()) {
         id = buf.substr(this.offset - 1, this.length);
       }
 
     }
 
     ConcurrentHashMap logIDMap = getLogIDMap();
     if (logIDMap.containsKey(id)) {
       String value = (String)logIDMap.get(id);
       if (StringUtils.isNotBlank(value))
         msg.setHeadItem("STF", value);
     }
   }
 
   public String getType()
   {
     return this.type;
   }
 
   public void setType(String type)
   {
     this.type = type;
   }
 
   public String getDeli()
   {
     return this.deli;
   }
 
   public void setDeli(String deli)
   {
     this.deli = deli;
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     if (("text/xml".equalsIgnoreCase(this.type)) || ("text/etf".equalsIgnoreCase(this.type)))
     {
       if (!(StringUtils.isBlank(this.name))) return;
       throw new HiException("213307", "name");
     }
     if ("msg".equalsIgnoreCase(this.type)) {
       if (!(StringUtils.isBlank(this.name))) return;
       throw new HiException("213307", "name");
     }
 
     if (StringUtils.isNotBlank(this.deli)) {
       if (this.pos != 0) return;
       throw new HiException("213307", "pos");
     }
 
     if ((this.offset == 0) || (this.length == 0))
       throw new HiException("213307", "offset|length");
   }
 }