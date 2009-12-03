/*     */ package com.hisun.atmp.tcp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.HiMessageInOut;
/*     */ import com.hisun.protocol.tcp.parser.HiHostIPXMLParser;
/*     */ import com.hisun.protocol.tcp.parser.HiHostIpItem;
/*     */ import com.hisun.protocol.tcp.parser.HiHostIpTable;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.URL;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiTcpNotifyConnector
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*  28 */   private static final HiStringManager sm = HiStringManager.getManager();
/*     */   private String _ip;
/*     */   private int _preLen;
/*     */   private int _port;
/*     */   private Logger _log;
/*     */   private HiHostIpTable _hostIpTable;
/*     */   private String _ipFile;
/*     */   private int _tmOut;
/*     */   private HiMessageInOut _msginout;
/*     */   private static final String HOST_NAME = "HST";
/*     */ 
/*     */   public HiTcpNotifyConnector()
/*     */   {
/*  33 */     this._hostIpTable = null;
/*     */ 
/*  35 */     this._tmOut = 30;
/*  36 */     this._msginout = new HiMessageInOut();
/*     */   }
/*     */ 
/*     */   public void notify(HiMessageContext ctx) throws HiException
/*     */   {
/*  41 */     HiMessage msg = ctx.getCurrentMsg();
/*  42 */     Socket socket = new Socket();
/*     */     try {
/*     */       try {
/*  45 */         getHostIp(msg);
/*  46 */         if (this._log.isInfoEnabled()) {
/*  47 */           this._log.info("preLen:[" + this._preLen + "];ip:[" + this._ip + "];port:[" + this._port + "]");
/*     */         }
/*     */ 
/*  50 */         this._msginout.setPreLen(this._preLen);
/*  51 */         socket.connect(new InetSocketAddress(this._ip, this._port), this._tmOut * 1000);
/*     */ 
/*  54 */         socket.setSoTimeout(this._tmOut * 1000);
/*     */       } catch (IOException e) {
/*  56 */         throw new HiException("231204", "connector connect error", e);
/*     */       }
/*     */ 
/*  60 */       if (this._log.isInfoEnabled()) {
/*  61 */         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/*  62 */         this._log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this._ip, String.valueOf(this._port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  75 */         socket.close();
/*     */       } catch (IOException byteBuffer) {
/*  77 */         throw new HiException("231207", "connector close error", e);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  75 */         socket.close();
/*     */       } catch (IOException e) {
/*  77 */         throw new HiException("231207", "connector close error", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/*  85 */     this._log = arg0.getLog();
/*     */   }
/*     */ 
/*     */   public String getHost() {
/*  89 */     return this._ip;
/*     */   }
/*     */ 
/*     */   public void setHost(String host) {
/*  93 */     this._ip = host;
/*     */   }
/*     */ 
/*     */   public int getPreLen() {
/*  97 */     return this._preLen;
/*     */   }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/* 101 */     this._preLen = preLen;
/*     */   }
/*     */ 
/*     */   public int getPort() {
/* 105 */     return this._port;
/*     */   }
/*     */ 
/*     */   public void setPort(int port) {
/* 109 */     this._port = port;
/*     */   }
/*     */ 
/*     */   public int getTmOut() {
/* 113 */     return this._tmOut;
/*     */   }
/*     */ 
/*     */   public void setTmOut(int tmOut) {
/* 117 */     this._tmOut = tmOut;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext arg0) throws HiException {
/* 121 */     notify(arg0);
/*     */   }
/*     */ 
/*     */   private void getHostIp(HiMessage msg)
/*     */   {
/* 126 */     if (msg.hasHeadItem("OIP")) {
/* 127 */       this._ip = msg.getHeadItem("OIP");
/*     */     }
/*     */ 
/* 130 */     if (StringUtils.isEmpty(this._ip)) {
/* 131 */       this._ip = msg.getHeadItem("SIP");
/*     */     }
/*     */ 
/* 134 */     if (msg.hasHeadItem("OPT")) {
/* 135 */       this._port = NumberUtils.toInt(msg.getHeadItem("OPT"));
/*     */     }
/*     */ 
/* 138 */     String hostName = msg.getHeadItem("HST");
/* 139 */     if ((this._hostIpTable != null) && (!(StringUtils.isEmpty(hostName)))) {
/* 140 */       HiHostIpItem item = this._hostIpTable.getHostIpItem(hostName);
/* 141 */       this._ip = item.ip;
/* 142 */       this._port = item.port;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getIpFile() {
/* 147 */     return this._ipFile;
/*     */   }
/*     */ 
/*     */   public void setIpFile(String ipFile) throws HiException {
/* 151 */     this._ipFile = ipFile;
/* 152 */     HiHostIPXMLParser parser = new HiHostIPXMLParser();
/* 153 */     URL url = HiResource.getResource(ipFile);
/* 154 */     if (url == null)
/* 155 */       throw new HiException("213302", ipFile);
/*     */     try
/*     */     {
/* 158 */       this._hostIpTable = ((HiHostIpTable)parser.parse(HiResource.getResource(ipFile)));
/*     */     }
/*     */     catch (Exception e) {
/* 161 */       throw new HiException("212005", ipFile, e);
/*     */     }
/*     */   }
/*     */ }