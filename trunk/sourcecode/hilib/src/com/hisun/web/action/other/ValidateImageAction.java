/*    */ package com.hisun.web.action.other;
/*    */ 
/*    */ import com.hisun.web.action.BaseAction;
/*    */ import com.sun.image.codec.jpeg.JPEGCodec;
/*    */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Random;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.struts2.ServletActionContext;
/*    */ 
/*    */ public class ValidateImageAction extends BaseAction
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public String execute()
/*    */     throws Exception
/*    */   {
/* 29 */     Random random = new Random();
/* 30 */     StringBuffer sb = new StringBuffer();
/* 31 */     for (int i = 0; i < 6; ++i) {
/* 32 */       int x = random.nextInt(25);
/* 33 */       x += 65;
/* 34 */       sb.append(String.valueOf((char)x));
/*    */     }
/*    */ 
/* 37 */     String validateString = sb.toString();
/* 38 */     HttpServletRequest request = ServletActionContext.getRequest();
/* 39 */     HttpSession session = request.getSession();
/* 40 */     session.setAttribute("VALIDATE", validateString);
/* 41 */     HttpServletResponse response = ServletActionContext.getResponse();
/*    */ 
/* 44 */     int width = 85; int height = 20;
/* 45 */     BufferedImage image = new BufferedImage(width, height, 1);
/* 46 */     Graphics g = image.getGraphics();
/*    */ 
/* 49 */     g.setColor(Color.decode("#FFFFFF"));
/* 50 */     g.fillRect(0, 0, width, height);
/*    */ 
/* 53 */     g.setColor(getRandColor(160, 200));
/* 54 */     for (int i = 0; i < 300; ++i) {
/* 55 */       int x = random.nextInt(width);
/* 56 */       int y = random.nextInt(height);
/* 57 */       int xl = random.nextInt(12);
/* 58 */       int yl = random.nextInt(12);
/* 59 */       g.drawLine(x, y, x + xl, y + yl);
/*    */     }
/*    */ 
/* 63 */     g.setColor(Color.decode("#01556B"));
/*    */ 
/* 65 */     g.setFont(new Font(null, 1, 18));
/*    */ 
/* 67 */     g.drawString(validateString, 2, 16);
/* 68 */     g.dispose();
/*    */ 
/* 70 */     OutputStream os = response.getOutputStream();
/* 71 */     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
/* 72 */     encoder.encode(image);
/* 73 */     os.close();
/*    */ 
/* 75 */     return "success";
/*    */   }
/*    */ 
/*    */   private Color getRandColor(int fc, int bc) {
/* 79 */     Random random = new Random();
/* 80 */     if (fc > 255)
/* 81 */       fc = 255;
/* 82 */     if (bc > 255)
/* 83 */       bc = 255;
/* 84 */     int r = fc + random.nextInt(bc - fc);
/* 85 */     int g = fc + random.nextInt(bc - fc);
/* 86 */     int b = fc + random.nextInt(bc - fc);
/* 87 */     return new Color(r, g, b);
/*    */   }
/*    */ }