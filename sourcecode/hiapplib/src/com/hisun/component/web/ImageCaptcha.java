/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.util.Random;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class ImageCaptcha
/*    */ {
/*    */   public static final String IMAGE_CAPTCHA = "__IMAGE_CAPTCHA";
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 32 */     HiMessage msg = ctx.getCurrentMsg();
/* 33 */     Logger log = HiLog.getLogger(msg);
/* 34 */     HttpServletResponse response = (HttpServletResponse)msg.getObjectHeadItem("_WEB_RESPONSE");
/*    */ 
/* 37 */     response.setContentType("image/jpeg");
/*    */ 
/* 39 */     BufferedImage image = new BufferedImage(80, 20, 4);
/*    */ 
/* 42 */     Graphics2D graphics = image.createGraphics();
/*    */ 
/* 44 */     graphics.setColor(Color.WHITE);
/* 45 */     graphics.fillRect(0, 0, 100, 20);
/*    */ 
/* 47 */     graphics.setFont(new Font("Roman", 1, 22));
/*    */ 
/* 49 */     String randomStr = randomString(4);
/* 50 */     log.info("randomStr:[" + randomStr + "]");
/* 51 */     for (int i = 0; i < 4; ++i)
/*    */     {
/* 53 */       graphics.setColor(new Color(new Random().nextInt(250), new Random().nextInt(250), new Random().nextInt(250)));
/*    */ 
/* 56 */       graphics.drawString(randomStr.substring(i, i + 1), 15 * i, 20);
/*    */ 
/* 58 */       graphics.drawLine(0, 0, 1, 1);
/*    */     }
/* 60 */     log.info("ImageCaptcha01");
/* 61 */     HttpSession session = (HttpSession)msg.getObjectHeadItem("_WEB_SESSION");
/* 62 */     session.setAttribute("__IMAGE_CAPTCHA", randomStr);
/*    */ 
/* 64 */     response.setHeader("Prama", "no-cache");
/* 65 */     response.setHeader("Coche-Control", "no-cache");
/* 66 */     response.setDateHeader("Expires", 0L);
/*    */     try
/*    */     {
/* 69 */       log.info("ImageCaptcha02");
/* 70 */       ImageIO.write(image, "jpeg", response.getOutputStream());
/* 71 */       log.info("ImageCaptcha03");
/*    */     } catch (IOException e) {
/* 73 */       log.error(e, e);
/*    */     }
/*    */ 
/* 76 */     return 0;
/*    */   }
/*    */ 
/*    */   public String randomString(int number)
/*    */   {
/* 83 */     String str = "1234567890";
/* 84 */     char[] chars = str.toCharArray();
/* 85 */     int length = chars.length;
/* 86 */     StringBuilder sb = new StringBuilder();
/* 87 */     Random random = new Random();
/*    */ 
/* 89 */     for (int i = 0; i < number; ++i)
/*    */     {
/* 91 */       sb.append(new String(new Character(chars[random.nextInt(length)]).toString()));
/*    */     }
/*    */ 
/* 94 */     return sb.toString();
/*    */   }
/*    */ }