 package com.hisun.component.web;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.util.Random;
 import javax.imageio.ImageIO;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 
 public class ImageCaptcha
 {
   public static final String IMAGE_CAPTCHA = "__IMAGE_CAPTCHA";
 
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HttpServletResponse response = (HttpServletResponse)msg.getObjectHeadItem("_WEB_RESPONSE");
 
     response.setContentType("image/jpeg");
 
     BufferedImage image = new BufferedImage(80, 20, 4);
 
     Graphics2D graphics = image.createGraphics();
 
     graphics.setColor(Color.WHITE);
     graphics.fillRect(0, 0, 100, 20);
 
     graphics.setFont(new Font("Roman", 1, 22));
 
     String randomStr = randomString(4);
     log.info("randomStr:[" + randomStr + "]");
     for (int i = 0; i < 4; ++i)
     {
       graphics.setColor(new Color(new Random().nextInt(250), new Random().nextInt(250), new Random().nextInt(250)));
 
       graphics.drawString(randomStr.substring(i, i + 1), 15 * i, 20);
 
       graphics.drawLine(0, 0, 1, 1);
     }
     log.info("ImageCaptcha01");
     HttpSession session = (HttpSession)msg.getObjectHeadItem("_WEB_SESSION");
     session.setAttribute("__IMAGE_CAPTCHA", randomStr);
 
     response.setHeader("Prama", "no-cache");
     response.setHeader("Coche-Control", "no-cache");
     response.setDateHeader("Expires", 0L);
     try
     {
       log.info("ImageCaptcha02");
       ImageIO.write(image, "jpeg", response.getOutputStream());
       log.info("ImageCaptcha03");
     } catch (IOException e) {
       log.error(e, e);
     }
 
     return 0;
   }
 
   public String randomString(int number)
   {
     String str = "1234567890";
     char[] chars = str.toCharArray();
     int length = chars.length;
     StringBuilder sb = new StringBuilder();
     Random random = new Random();
 
     for (int i = 0; i < number; ++i)
     {
       sb.append(new String(new Character(chars[random.nextInt(length)]).toString()));
     }
 
     return sb.toString();
   }
 }