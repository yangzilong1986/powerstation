package com.hisun.atc.rpt;

public abstract interface HiReportConstants
{
  public static final int PART_NUM = 9;
  public static final int TYPE_PH = 0;
  public static final int TYPE_RH = 1;
  public static final int TYPE_TH = 2;
  public static final int TYPE_ITER = 3;
  public static final int TYPE_ST = 4;
  public static final int TYPE_SUM = 5;
  public static final int TYPE_TF = 6;
  public static final int TYPE_RF = 7;
  public static final int TYPE_PF = 8;
  public static final int TYPE_COMMENT = 99;
  public static final int FILEEND = -99;
  public static final String HEAD_PH = "[0]";
  public static final String HEAD_RH = "[1]";
  public static final String HEAD_TH = "[2]";
  public static final String HEAD_ITER = "[3]";
  public static final String HEAD_ST = "[4]";
  public static final String HEAD_SUM = "[5]";
  public static final String HEAD_TF = "[6]";
  public static final String HEAD_RF = "[7]";
  public static final String HEAD_PF = "[8]";
  public static final String DATA_HEADDELI = ":";
  public static final String DATA_COMMENT = "#";
  public static final String DATA_EVALUATE = "=";
  public static final String DATA_DELIMITER = "|";
  public static final int FORM_FEED = 12;
  public static final String DATE = "DATE";
  public static final String TIME = "TIME";
  public static final String PAGEROWS = "PAGEROWS";
  public static final String PAGENUM = "PAGENUM";
  public static final String PAGESUM = "PAGESUM";
  public static final String RPTNAME = "RPTNAME";
  public static final String RECNUM = "RECNUM";
}