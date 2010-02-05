 package com.hisun.tools.parser;
 
 import com.hisun.tools.HiFilBrcParam;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringUtils;
 import com.hisun.util.HiSystemUtils;
 import java.io.File;
 import org.apache.commons.lang.StringUtils;
 
 public class HiFileBpRecoverItem
 {
   public static final int WORKDATE_DIR = 0;
   public static final int SYSDATE_DIR = 1;
   public static final int OTHER_DIR = 9;
   public static final int MAGNETIC_TAPE = 0;
   public static final int HARD_DISK = 1;
   public static final int HARD_MAGNETIC = 2;
   private String _fileType;
   private int _device;
   private String _defaultTape;
   private String _sourceDir;
   private int _sourceDirType;
   private String _backupDir;
   private boolean _isCompress;
   private boolean _isOverlay;
   private boolean _isClean;
   private int _cleanHolddays;
   private boolean _isBydir;
   private String _recoverDir;
   private boolean _recoverLastPack;
   private String _backupScript;
   private String _recoverScript;
   private String _cleanScript;
 
   public HiFileBpRecoverItem()
   {
     this._device = -1;
 
     this._defaultTape = "/dev/rmt0";
 
     this._sourceDirType = 9;
 
     this._isCompress = true;
 
     this._isOverlay = false;
 
     this._isClean = true;
 
     this._cleanHolddays = 1;
 
     this._isBydir = true;
 
     this._recoverLastPack = true;
   }
 
   public String getFiletype()
   {
     return this._fileType;
   }
 
   public void setFiletype(String filetype) {
     this._fileType = filetype;
   }
 
   public int getDevice() {
     return this._device;
   }
 
   public void setDevice(String device) {
     if (StringUtils.equalsIgnoreCase(device, "hd"))
       this._device = 1;
     else if (StringUtils.equalsIgnoreCase(device, "rd"))
       this._device = 0;
     else if (StringUtils.equalsIgnoreCase(device, "hr"))
       this._device = 2;
   }
 
   public String getDefaulttape()
   {
     return this._defaultTape;
   }
 
   public void setDefaulttape(String defaulttape) {
     this._defaultTape = defaulttape;
   }
 
   public String getSourcedir() {
     return this._sourceDir;
   }
 
   public void setSourcedir(String sourcedir) {
     this._sourceDir = sourcedir;
   }
 
   public int getSourcedirtype() {
     return this._sourceDirType;
   }
 
   public void setSourcedirtype(int sourcedirtype) {
     this._sourceDirType = sourcedirtype;
   }
 
   public String getBackupdir() {
     return this._backupDir;
   }
 
   public void setBackupdir(String backupdir) {
     this._backupDir = backupdir;
   }
 
   public boolean isIscompress() {
     return this._isCompress;
   }
 
   public void setIscompress(String iscompress) {
     if (StringUtils.equalsIgnoreCase(iscompress, "n"))
       this._isCompress = false;
   }
 
   public boolean isIsoverlay() {
     return this._isOverlay;
   }
 
   public void setIsoverlay(String isoverlay) {
     if (StringUtils.equalsIgnoreCase(isoverlay, "y"))
       this._isOverlay = true;
   }
 
   public boolean isIsclean() {
     return this._isClean;
   }
 
   public void setIsclean(String isclean) {
     if (StringUtils.equalsIgnoreCase(isclean, "n"))
       this._isClean = false;
   }
 
   public int isCleanholddays() {
     return this._cleanHolddays;
   }
 
   public void setCleanholddays(int cleanholddays) {
     this._cleanHolddays = cleanholddays;
   }
 
   public boolean isIsbydir() {
     return this._isBydir;
   }
 
   public void setIsbydir(boolean isbydir) {
     this._isBydir = isbydir;
   }
 
   public String getRecoverdir() {
     return this._recoverDir;
   }
 
   public void setRecoverdir(String recoverdir) {
     this._recoverDir = recoverdir;
   }
 
   public boolean isRecoverlastpack() {
     return this._recoverLastPack;
   }
 
   public void setRecoverlastpack(String recoverlastpack) {
     if (StringUtils.equalsIgnoreCase(recoverlastpack, "n"))
       this._recoverLastPack = false;
   }
 
   public String getBackupScript() {
     return this._backupScript;
   }
 
   public void setBackupScript(String backupScript) {
     this._backupScript = backupScript;
   }
 
   public String getRecoverScript() {
     return this._recoverScript;
   }
 
   public void setRecoverScript(String recoverScript) {
     this._recoverScript = recoverScript;
   }
 
   public String getCleanScript() {
     return this._cleanScript;
   }
 
   public void setCleanScript(String cleanScript) {
     this._cleanScript = cleanScript;
   }
 
   public void process(HiFilBrcParam param) throws Exception {
     switch (param._action)
     {
     case 0:
       cleanOneType(param);
       break;
     case 1:
       backupOneType(param);
       break;
     case 2:
       recoverOneType(param);
       break;
     default:
       backupOneType(param);
     }
   }
 
   private void cleanOneType(HiFilBrcParam param) throws Exception
   {
   }
 
   private void backupOneType(HiFilBrcParam param) throws Exception
   {
     String destDir = null; String tmpDate = null;
     switch (this._sourceDirType)
     {
     case 0:
       tmpDate = param._workdate;
       destDir = assembleDir(this._sourceDir, param._sysdate);
       break;
     case 1:
       tmpDate = param._sysdate;
       destDir = assembleDir(this._sourceDir, param._sysdate);
       break;
     case 9:
       destDir = this._sourceDir;
       break;
     default:
       destDir = this._sourceDir;
     }
 
     if (StringUtils.isNotEmpty(this._backupScript)) {
       String workdir = HiICSProperty.getWorkDir();
       String cmd = HiStringUtils.format("sh %s/%s %s %s %s %s %s %s", new String[] { workdir, this._backupScript, destDir, this._backupDir, (this._isCompress) ? "Y" : "N", (this._isOverlay) ? "Y" : "N", this._fileType, tmpDate });
 
       HiSystemUtils.exec(cmd, true);
       return;
     }
 
     switch (this._device)
     {
     case 0:
       backupToRd(this._defaultTape, destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
 
       break;
     case 1:
       backupToHd(destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
 
       break;
     case 2:
       backupToHd(destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
 
       backupToRd(this._defaultTape, destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
 
       break;
     default:
       throw new Exception("[40]没有在配置文件中指定设备类型!");
     }
   }
 
   private void recoverOneType(HiFilBrcParam param) throws Exception
   {
   }
 
   private String assembleDir(String srcDir, String date)
   {
     String destDir = null;
     StringUtils.replace(srcDir, "%ymd", date);
     StringUtils.replace(srcDir, "%ym", date.substring(0, 6));
     StringUtils.replace(srcDir, "%md", date.substring(2));
     StringUtils.replace(srcDir, "%m", date.substring(2, 4));
     StringUtils.replace(srcDir, "%d", date.substring(4, 6));
     return destDir;
   }
 
   private void backupToHd(String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date) throws Exception
   {
     File f1 = new File(srcDir);
     if (!(f1.exists())) {
       throw new Exception(srcDir + " not existed");
     }
     File f2 = new File(destDir);
     if (!(f2.exists())) {
       throw new Exception(destDir + " not existed");
     }
     String packName = null;
     for (int i = 0; !(isOverLay); ++i) {
       if (destDir.endsWith("/")) {
         packName = destDir + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
       }
       else {
         packName = destDir + "/" + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
       }
 
       File f3 = new File(packName);
       if (!(f3.exists())) {
         break;
       }
       if (i == 99)
         break;
     }
     StringBuffer tmp = new StringBuffer();
     String[] tmps = srcDir.split(" \t");
     String workdir = System.getProperty("HWORKDIR");
     for (int i = 0; i < tmps.length; ++i) {
       if (workdir.endsWith("/"))
         tmp.append(workdir + tmps[i]);
       else {
         tmp.append(workdir + "/" + tmps[i]);
       }
     }
 
     String cmd = null;
     cmd = HiStringUtils.format("tar cvf %s %s", packName, tmp.toString());
     HiSystemUtils.exec(cmd, true);
     if (isCompress) {
       cmd = HiStringUtils.format("compress -f %s", packName);
       HiSystemUtils.exec(cmd, true);
     }
   }
 
   private void backupToRd(String tape, String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date)
     throws Exception
   {
     File f1 = new File(srcDir);
     if (!(f1.exists())) {
       throw new Exception(srcDir + " not existed");
     }
     File f2 = new File(destDir);
     if (!(f2.exists())) {
       throw new Exception(destDir + " not existed");
     }
     String packName = null;
     for (int i = 0; !(isOverLay); ++i) {
       if (destDir.endsWith("/")) {
         packName = destDir + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
       }
       else {
         packName = destDir + "/" + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
       }
 
       File f3 = new File(packName);
       if (!(f3.exists())) {
         break;
       }
       if (i == 99) {
         break;
       }
     }
     StringBuffer tmp = new StringBuffer();
     String[] tmps = srcDir.split(" \t");
     String workdir = System.getProperty("HWORKDIR");
     for (int i = 0; i < tmps.length; ++i) {
       if (workdir.endsWith("/"))
         tmp.append(workdir + tmps[i]);
       else {
         tmp.append(workdir + "/" + tmps[i]);
       }
     }
 
     String cmd = null;
     cmd = HiStringUtils.format("tar cvf %s %s", packName, tmp.toString());
     HiSystemUtils.exec(cmd, true);
     if (isCompress) {
       cmd = HiStringUtils.format("compress -f %s", packName);
       HiSystemUtils.exec(cmd, true);
       packName = packName + ".Z";
     }
     cmd = HiStringUtils.format("tar cf %s %s; rm -f %s", tape, packName, packName);
 
     HiSystemUtils.exec(cmd, true);
   }
 
   private void restoreFromHd(String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date)
     throws Exception
   {
   }
 
   private void restoreFromRd(String tape, String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date)
     throws Exception
   {
   }
 }