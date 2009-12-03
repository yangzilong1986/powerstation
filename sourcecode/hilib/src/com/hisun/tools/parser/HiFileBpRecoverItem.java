/*     */ package com.hisun.tools.parser;
/*     */ 
/*     */ import com.hisun.tools.HiFilBrcParam;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import com.hisun.util.HiSystemUtils;
/*     */ import java.io.File;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiFileBpRecoverItem
/*     */ {
/*     */   public static final int WORKDATE_DIR = 0;
/*     */   public static final int SYSDATE_DIR = 1;
/*     */   public static final int OTHER_DIR = 9;
/*     */   public static final int MAGNETIC_TAPE = 0;
/*     */   public static final int HARD_DISK = 1;
/*     */   public static final int HARD_MAGNETIC = 2;
/*     */   private String _fileType;
/*     */   private int _device;
/*     */   private String _defaultTape;
/*     */   private String _sourceDir;
/*     */   private int _sourceDirType;
/*     */   private String _backupDir;
/*     */   private boolean _isCompress;
/*     */   private boolean _isOverlay;
/*     */   private boolean _isClean;
/*     */   private int _cleanHolddays;
/*     */   private boolean _isBydir;
/*     */   private String _recoverDir;
/*     */   private boolean _recoverLastPack;
/*     */   private String _backupScript;
/*     */   private String _recoverScript;
/*     */   private String _cleanScript;
/*     */ 
/*     */   public HiFileBpRecoverItem()
/*     */   {
/*  38 */     this._device = -1;
/*     */ 
/*  42 */     this._defaultTape = "/dev/rmt0";
/*     */ 
/*  52 */     this._sourceDirType = 9;
/*     */ 
/*  60 */     this._isCompress = true;
/*     */ 
/*  65 */     this._isOverlay = false;
/*     */ 
/*  69 */     this._isClean = true;
/*     */ 
/*  74 */     this._cleanHolddays = 1;
/*     */ 
/*  80 */     this._isBydir = true;
/*     */ 
/*  91 */     this._recoverLastPack = true;
/*     */   }
/*     */ 
/*     */   public String getFiletype()
/*     */   {
/* 111 */     return this._fileType;
/*     */   }
/*     */ 
/*     */   public void setFiletype(String filetype) {
/* 115 */     this._fileType = filetype;
/*     */   }
/*     */ 
/*     */   public int getDevice() {
/* 119 */     return this._device;
/*     */   }
/*     */ 
/*     */   public void setDevice(String device) {
/* 123 */     if (StringUtils.equalsIgnoreCase(device, "hd"))
/* 124 */       this._device = 1;
/* 125 */     else if (StringUtils.equalsIgnoreCase(device, "rd"))
/* 126 */       this._device = 0;
/* 127 */     else if (StringUtils.equalsIgnoreCase(device, "hr"))
/* 128 */       this._device = 2;
/*     */   }
/*     */ 
/*     */   public String getDefaulttape()
/*     */   {
/* 133 */     return this._defaultTape;
/*     */   }
/*     */ 
/*     */   public void setDefaulttape(String defaulttape) {
/* 137 */     this._defaultTape = defaulttape;
/*     */   }
/*     */ 
/*     */   public String getSourcedir() {
/* 141 */     return this._sourceDir;
/*     */   }
/*     */ 
/*     */   public void setSourcedir(String sourcedir) {
/* 145 */     this._sourceDir = sourcedir;
/*     */   }
/*     */ 
/*     */   public int getSourcedirtype() {
/* 149 */     return this._sourceDirType;
/*     */   }
/*     */ 
/*     */   public void setSourcedirtype(int sourcedirtype) {
/* 153 */     this._sourceDirType = sourcedirtype;
/*     */   }
/*     */ 
/*     */   public String getBackupdir() {
/* 157 */     return this._backupDir;
/*     */   }
/*     */ 
/*     */   public void setBackupdir(String backupdir) {
/* 161 */     this._backupDir = backupdir;
/*     */   }
/*     */ 
/*     */   public boolean isIscompress() {
/* 165 */     return this._isCompress;
/*     */   }
/*     */ 
/*     */   public void setIscompress(String iscompress) {
/* 169 */     if (StringUtils.equalsIgnoreCase(iscompress, "n"))
/* 170 */       this._isCompress = false;
/*     */   }
/*     */ 
/*     */   public boolean isIsoverlay() {
/* 174 */     return this._isOverlay;
/*     */   }
/*     */ 
/*     */   public void setIsoverlay(String isoverlay) {
/* 178 */     if (StringUtils.equalsIgnoreCase(isoverlay, "y"))
/* 179 */       this._isOverlay = true;
/*     */   }
/*     */ 
/*     */   public boolean isIsclean() {
/* 183 */     return this._isClean;
/*     */   }
/*     */ 
/*     */   public void setIsclean(String isclean) {
/* 187 */     if (StringUtils.equalsIgnoreCase(isclean, "n"))
/* 188 */       this._isClean = false;
/*     */   }
/*     */ 
/*     */   public int isCleanholddays() {
/* 192 */     return this._cleanHolddays;
/*     */   }
/*     */ 
/*     */   public void setCleanholddays(int cleanholddays) {
/* 196 */     this._cleanHolddays = cleanholddays;
/*     */   }
/*     */ 
/*     */   public boolean isIsbydir() {
/* 200 */     return this._isBydir;
/*     */   }
/*     */ 
/*     */   public void setIsbydir(boolean isbydir) {
/* 204 */     this._isBydir = isbydir;
/*     */   }
/*     */ 
/*     */   public String getRecoverdir() {
/* 208 */     return this._recoverDir;
/*     */   }
/*     */ 
/*     */   public void setRecoverdir(String recoverdir) {
/* 212 */     this._recoverDir = recoverdir;
/*     */   }
/*     */ 
/*     */   public boolean isRecoverlastpack() {
/* 216 */     return this._recoverLastPack;
/*     */   }
/*     */ 
/*     */   public void setRecoverlastpack(String recoverlastpack) {
/* 220 */     if (StringUtils.equalsIgnoreCase(recoverlastpack, "n"))
/* 221 */       this._recoverLastPack = false;
/*     */   }
/*     */ 
/*     */   public String getBackupScript() {
/* 225 */     return this._backupScript;
/*     */   }
/*     */ 
/*     */   public void setBackupScript(String backupScript) {
/* 229 */     this._backupScript = backupScript;
/*     */   }
/*     */ 
/*     */   public String getRecoverScript() {
/* 233 */     return this._recoverScript;
/*     */   }
/*     */ 
/*     */   public void setRecoverScript(String recoverScript) {
/* 237 */     this._recoverScript = recoverScript;
/*     */   }
/*     */ 
/*     */   public String getCleanScript() {
/* 241 */     return this._cleanScript;
/*     */   }
/*     */ 
/*     */   public void setCleanScript(String cleanScript) {
/* 245 */     this._cleanScript = cleanScript;
/*     */   }
/*     */ 
/*     */   public void process(HiFilBrcParam param) throws Exception {
/* 249 */     switch (param._action)
/*     */     {
/*     */     case 0:
/* 251 */       cleanOneType(param);
/* 252 */       break;
/*     */     case 1:
/* 254 */       backupOneType(param);
/* 255 */       break;
/*     */     case 2:
/* 257 */       recoverOneType(param);
/* 258 */       break;
/*     */     default:
/* 260 */       backupOneType(param);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cleanOneType(HiFilBrcParam param) throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   private void backupOneType(HiFilBrcParam param) throws Exception
/*     */   {
/* 270 */     String destDir = null; String tmpDate = null;
/* 271 */     switch (this._sourceDirType)
/*     */     {
/*     */     case 0:
/* 273 */       tmpDate = param._workdate;
/* 274 */       destDir = assembleDir(this._sourceDir, param._sysdate);
/* 275 */       break;
/*     */     case 1:
/* 277 */       tmpDate = param._sysdate;
/* 278 */       destDir = assembleDir(this._sourceDir, param._sysdate);
/* 279 */       break;
/*     */     case 9:
/* 281 */       destDir = this._sourceDir;
/* 282 */       break;
/*     */     default:
/* 284 */       destDir = this._sourceDir;
/*     */     }
/*     */ 
/* 288 */     if (StringUtils.isNotEmpty(this._backupScript)) {
/* 289 */       String workdir = HiICSProperty.getWorkDir();
/* 290 */       String cmd = HiStringUtils.format("sh %s/%s %s %s %s %s %s %s", new String[] { workdir, this._backupScript, destDir, this._backupDir, (this._isCompress) ? "Y" : "N", (this._isOverlay) ? "Y" : "N", this._fileType, tmpDate });
/*     */ 
/* 292 */       HiSystemUtils.exec(cmd, true);
/* 293 */       return;
/*     */     }
/*     */ 
/* 296 */     switch (this._device)
/*     */     {
/*     */     case 0:
/* 298 */       backupToRd(this._defaultTape, destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
/*     */ 
/* 300 */       break;
/*     */     case 1:
/* 302 */       backupToHd(destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
/*     */ 
/* 304 */       break;
/*     */     case 2:
/* 306 */       backupToHd(destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
/*     */ 
/* 308 */       backupToRd(this._defaultTape, destDir, this._backupDir, this._isCompress, this._isOverlay, this._fileType, tmpDate);
/*     */ 
/* 310 */       break;
/*     */     default:
/* 312 */       throw new Exception("[40]没有在配置文件中指定设备类型!");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void recoverOneType(HiFilBrcParam param) throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   private String assembleDir(String srcDir, String date)
/*     */   {
/* 322 */     String destDir = null;
/* 323 */     StringUtils.replace(srcDir, "%ymd", date);
/* 324 */     StringUtils.replace(srcDir, "%ym", date.substring(0, 6));
/* 325 */     StringUtils.replace(srcDir, "%md", date.substring(2));
/* 326 */     StringUtils.replace(srcDir, "%m", date.substring(2, 4));
/* 327 */     StringUtils.replace(srcDir, "%d", date.substring(4, 6));
/* 328 */     return destDir;
/*     */   }
/*     */ 
/*     */   private void backupToHd(String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date) throws Exception
/*     */   {
/* 333 */     File f1 = new File(srcDir);
/* 334 */     if (!(f1.exists())) {
/* 335 */       throw new Exception(srcDir + " not existed");
/*     */     }
/* 337 */     File f2 = new File(destDir);
/* 338 */     if (!(f2.exists())) {
/* 339 */       throw new Exception(destDir + " not existed");
/*     */     }
/* 341 */     String packName = null;
/* 342 */     for (int i = 0; !(isOverLay); ++i) {
/* 343 */       if (destDir.endsWith("/")) {
/* 344 */         packName = destDir + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
/*     */       }
/*     */       else {
/* 347 */         packName = destDir + "/" + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
/*     */       }
/*     */ 
/* 350 */       File f3 = new File(packName);
/* 351 */       if (!(f3.exists())) {
/*     */         break;
/*     */       }
/* 354 */       if (i == 99)
/*     */         break;
/*     */     }
/* 357 */     StringBuffer tmp = new StringBuffer();
/* 358 */     String[] tmps = srcDir.split(" \t");
/* 359 */     String workdir = System.getProperty("HWORKDIR");
/* 360 */     for (int i = 0; i < tmps.length; ++i) {
/* 361 */       if (workdir.endsWith("/"))
/* 362 */         tmp.append(workdir + tmps[i]);
/*     */       else {
/* 364 */         tmp.append(workdir + "/" + tmps[i]);
/*     */       }
/*     */     }
/*     */ 
/* 368 */     String cmd = null;
/* 369 */     cmd = HiStringUtils.format("tar cvf %s %s", packName, tmp.toString());
/* 370 */     HiSystemUtils.exec(cmd, true);
/* 371 */     if (isCompress) {
/* 372 */       cmd = HiStringUtils.format("compress -f %s", packName);
/* 373 */       HiSystemUtils.exec(cmd, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void backupToRd(String tape, String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date)
/*     */     throws Exception
/*     */   {
/* 380 */     File f1 = new File(srcDir);
/* 381 */     if (!(f1.exists())) {
/* 382 */       throw new Exception(srcDir + " not existed");
/*     */     }
/* 384 */     File f2 = new File(destDir);
/* 385 */     if (!(f2.exists())) {
/* 386 */       throw new Exception(destDir + " not existed");
/*     */     }
/* 388 */     String packName = null;
/* 389 */     for (int i = 0; !(isOverLay); ++i) {
/* 390 */       if (destDir.endsWith("/")) {
/* 391 */         packName = destDir + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
/*     */       }
/*     */       else {
/* 394 */         packName = destDir + "/" + type + "_" + date + "_" + StringUtils.leftPad(String.valueOf(i), 2, '0');
/*     */       }
/*     */ 
/* 397 */       File f3 = new File(packName);
/* 398 */       if (!(f3.exists())) {
/*     */         break;
/*     */       }
/* 401 */       if (i == 99) {
/*     */         break;
/*     */       }
/*     */     }
/* 405 */     StringBuffer tmp = new StringBuffer();
/* 406 */     String[] tmps = srcDir.split(" \t");
/* 407 */     String workdir = System.getProperty("HWORKDIR");
/* 408 */     for (int i = 0; i < tmps.length; ++i) {
/* 409 */       if (workdir.endsWith("/"))
/* 410 */         tmp.append(workdir + tmps[i]);
/*     */       else {
/* 412 */         tmp.append(workdir + "/" + tmps[i]);
/*     */       }
/*     */     }
/*     */ 
/* 416 */     String cmd = null;
/* 417 */     cmd = HiStringUtils.format("tar cvf %s %s", packName, tmp.toString());
/* 418 */     HiSystemUtils.exec(cmd, true);
/* 419 */     if (isCompress) {
/* 420 */       cmd = HiStringUtils.format("compress -f %s", packName);
/* 421 */       HiSystemUtils.exec(cmd, true);
/* 422 */       packName = packName + ".Z";
/*     */     }
/* 424 */     cmd = HiStringUtils.format("tar cf %s %s; rm -f %s", tape, packName, packName);
/*     */ 
/* 426 */     HiSystemUtils.exec(cmd, true);
/*     */   }
/*     */ 
/*     */   private void restoreFromHd(String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   private void restoreFromRd(String tape, String srcDir, String destDir, boolean isCompress, boolean isOverLay, String type, String date)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ }