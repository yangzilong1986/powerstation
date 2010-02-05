 package com.hisun.web.action.other;

 import com.hisun.web.action.BaseAction;
 import com.sun.image.codec.jpeg.JPEGCodec;
 import com.sun.image.codec.jpeg.JPEGImageEncoder;
 import org.apache.struts2.ServletActionContext;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import java.awt.*;
 import java.awt.image.BufferedImage;
 import java.io.OutputStream;
 import java.util.Random;
 
 public class ValidateImageAction extends BaseAction
 {
   private static final long serialVersionUID = 1L;
 
   public String execute()
     throws Exception
   {
     Random random = new Random();
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < 6; ++i) {
       int x = random.nextInt(25);
       x += 65;
       sb.append(String.valueOf((char)x));
     }
 
     String validateString = sb.toString();
     HttpServletRequest request = ServletActionContext.getRequest();
     HttpSession session = request.getSession();
     session.setAttribute("VALIDATE", validateString);
     HttpServletResponse response = ServletActionContext.getResponse();
 
     int width = 85; int height = 20;
     BufferedImage image = new BufferedImage(width, height, 1);
     Graphics g = image.getGraphics();
 
     g.setColor(Color.decode("#FFFFFF"));
     g.fillRect(0, 0, width, height);
 
     g.setColor(getRandColor(160, 200));
     for (int i = 0; i < 300; ++i) {
       int x = random.nextInt(width);
       int y = random.nextInt(height);
       int xl = random.nextInt(12);
       int yl = random.nextInt(12);
       g.drawLine(x, y, x + xl, y + yl);
     }
 
     g.setColor(Color.decode("#01556B"));
 
     g.setFont(new Font(null, 1, 18));
 
     g.drawString(validateString, 2, 16);
     g.dispose();
 
     OutputStream os = response.getOutputStream();
     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
     encoder.encode(image);
     os.close();
 
     return "success";
   }
 
   private Color getRandColor(int fc, int bc) {
     Random random = new Random();
     if (fc > 255)
       fc = 255;
     if (bc > 255)
       bc = 255;
     int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc);
     int b = fc + random.nextInt(bc - fc);
     return new Color(r, g, b);
   }
 }