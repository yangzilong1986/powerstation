 package com.hisun.atc.common;
 
 import com.hisun.exception.HiException;
 import com.hisun.pubinterface.HiCloseable;
 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import org.apache.commons.lang.StringUtils;
 
 public class HiFile
   implements HiCloseable
 {
   private BufferedWriter _bw;
   private BufferedReader _br;
   private FileInputStream _fis;
   private String _name;
 
   public void setName(String name)
   {
     this._name = name;
   }
 
   private String read(int offset, int len) throws HiException {
     char[] buffer = new char[len];
     try {
       this._br.read(buffer, offset, len);
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
     return new String(buffer);
   }
 
   public String read(int len) throws HiException {
     return read(0, len);
   }
 
   public int readBytes(StringBuffer buffer, int len) throws HiException {
     byte[] charBuffer = new byte[len];
     int ret = 0;
     try {
       ret = this._fis.read(charBuffer, 0, len);
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
     buffer.append(new String(charBuffer, 0, ret));
     return ret; }
 
   public int readBytes(ByteArrayOutputStream buffer, int offset, int len) throws HiException {
     byte[] charBuffer = new byte[len];
     int ret = 0;
     try {
       this._fis.skip(offset);
       ret = this._fis.read(charBuffer, 0, len);
 
       buffer.write(charBuffer);
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
 
     return ret;
   }
 
   public int available() throws HiException {
     try {
       if (this._fis != null)
         return this._fis.available();
     }
     catch (IOException e) {
       throw new HiException(e);
     }
     return 0; }
 
   public int read(StringBuffer buffer, int len) throws HiException {
     char[] charBuffer = new char[len];
     int ret = 0;
     try {
       ret = this._br.read(charBuffer, 0, len);
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
     buffer.append(new String(charBuffer, 0, ret));
     return ret;
   }
 
   public String readLine() throws HiException {
     String buffer;
     try {
       buffer = this._br.readLine();
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
     return buffer;
   }
 
   public void open(String fileName, String mode) throws HiException {
     this._name = fileName;
     try {
       if (StringUtils.equalsIgnoreCase(mode, "r")) {
         this._br = new BufferedReader(new FileReader(this._name));
         this._fis = new FileInputStream(this._name);
       } else if (StringUtils.equalsIgnoreCase(mode, "w")) {
         createFile(fileName);
         this._bw = new BufferedWriter(new FileWriter(this._name, false));
       }
       else if (StringUtils.equalsIgnoreCase(mode, "a")) {
         createFile(fileName);
         this._bw = new BufferedWriter(new FileWriter(this._name, true));
       }
       else
       {
         throw new HiException("220079", this._name + "[" + mode + "]");
       }
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
   }
 
   public void createFile(String fileName) throws HiException {
     try {
       File f = new File(fileName);
       if (!(f.exists()))
         f.createNewFile();
     } catch (IOException e) {
       throw new HiException("215117", this._name, e);
     }
   }
 
   public void write(String value) throws HiException {
     try {
       this._bw.write(value);
       this._bw.flush();
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
   }
 
   public void close() throws HiException {
     try {
       if (this._br != null)
         this._br.close();
       if (this._bw != null)
         this._bw.close();
       if (this._fis != null)
         this._fis.close();
     } catch (IOException e) {
       throw new HiException("220079", this._name, e);
     }
   }
 
   public static void copy(String srcFile, String destFile)
     throws HiException
   {
     copy(srcFile, destFile, false);
   }
 
   public static void copy(String srcFile, String destFile, boolean append)
     throws HiException
   {
     InputStream in = null;
     OutputStream out = null;
     try {
       File file = new File(destFile);
       if (!(file.getParentFile().exists()))
       {
         file.getParentFile().mkdirs();
       }
 
       in = new FileInputStream(srcFile);
       out = new FileOutputStream(destFile, append);
       byte[] buf = new byte[1024];
 
       while ((len = in.read(buf)) > 0)
       {
         int len;
         out.write(buf, 0, len);
       }
     }
     catch (IOException e)
     {
       String[] files;
       throw new HiException("220030", files, e);
     } finally {
       try {
         if (in != null)
           in.close();
         if (out != null)
           out.close();
       }
       catch (IOException e)
       {
       }
     }
   }
 }