package com.hzjbbis.util;

import com.hzjbbis.fk.model.TaskTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;

public class RtuTaskUtil {
    private static final Log log = LogFactory.getLog(RtuTaskUtil.class);
    public static final String TIME_UNIT_MINUTE = "02";
    public static final String TIME_UNIT_HOUR = "03";
    public static final String TIME_UNIT_DAY = "04";
    public static final String TIME_UNIT_MONTH = "05";
    public static final long SECONDES_IN_MINUTE = 60L;
    public static final long SECONDES_IN_HOUR = 3600L;
    public static final long SECONDES_IN_DAY = 86400L;
    public static final int[] dayInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static long lastRuntime(TaskTemplate task, long time) {
        long rt = time;
        try {
            if (task.getUploadIntervalUnit().equalsIgnoreCase("02"))
                rt = lastRuntimeM(task.getUploadStartTimeUnit(), task.getUploadStartTime(), task.getUploadIntervalUnit(), task.getUploadInterval(), time);
            else if (task.getUploadIntervalUnit().equalsIgnoreCase("03"))
                rt = lastRuntimeH(task.getUploadStartTimeUnit(), task.getUploadStartTime(), task.getUploadIntervalUnit(), task.getUploadInterval(), time);
            else if (task.getUploadIntervalUnit().equalsIgnoreCase("04")) {
                rt = lastRuntimeD(task.getUploadStartTimeUnit(), task.getUploadStartTime(), task.getUploadIntervalUnit(), task.getUploadInterval(), time);
            }
        } catch (Exception e) {
            log.error("取最近运行时刻", e);
        }
        return rt;
    }

    public static long lastSampTime(TaskTemplate task, long time) {
        long rt = time;
        try {
            if (task.getSampleIntervalUnit().equalsIgnoreCase("02"))
                rt = lastRuntimeM(task.getSampleStartTimeUnit(), task.getSampleStartTime(), task.getSampleIntervalUnit(), task.getSampleInterval(), time);
            else if (task.getSampleIntervalUnit().equalsIgnoreCase("03"))
                rt = lastRuntimeH(task.getSampleStartTimeUnit(), task.getSampleStartTime(), task.getSampleIntervalUnit(), task.getSampleInterval(), time);
            else if (task.getSampleIntervalUnit().equalsIgnoreCase("04")) {
                rt = lastRuntimeD(task.getSampleStartTimeUnit(), task.getSampleStartTime(), task.getSampleIntervalUnit(), task.getSampleInterval(), time);
            }
        } catch (Exception e) {
            log.error("取最近采样时刻", e);
        }
        return rt;
    }

    public static int rCapacity(TaskTemplate task) {
        int rt = 0;
        try {
            if ((task != null) && (task.getSampleInterval() > 0) && (task.getUploadInterval() > 0)) {
                long sinterval = getUnitSecondes(task.getSampleIntervalUnit()) * task.getSampleInterval();
                long rinterval = getUnitSecondes(task.getUploadIntervalUnit()) * task.getUploadInterval();
                if (sinterval == 0L) {
                    if (rinterval == 0L) rt = task.getUploadInterval() / task.getSampleInterval();
                } else {
                    long frq = task.getFrequence();
                    if (frq <= 0L) {
                        frq = 1L;
                    }
                    rt = (int) (rinterval / sinterval * frq);
                }
            }
        } catch (Exception e) {
            log.error("取上报包含数据点数", e);
        }
        return rt;
    }

    public static long getStartTimeOfTask(TaskTemplate task, long time) {
        long rt = time;
        try {
            Calendar ctime = Calendar.getInstance();
            ctime.setTimeInMillis(time);

            ctime.set(13, 0);
            ctime.set(14, 0);
            if (task.getUploadStartTimeUnit().equalsIgnoreCase("02")) {
                ctime.add(12, -task.getUploadStartTime());
            } else if (task.getUploadStartTimeUnit().equalsIgnoreCase(task.getUploadIntervalUnit())) {
                int delt = (int) getUnitSecondes(task.getUploadStartTimeUnit()) * task.getUploadStartTime();
                ctime.add(13, -delt);
            }

            int spu = (int) getUnitSecondes(task.getUploadIntervalUnit()) * task.getUploadInterval();
            ctime.add(13, spu);
            rt = ctime.getTimeInMillis();
        } catch (Exception e) {
            log.error("", e);
        }
        return rt;
    }

    public static long getUnitSecondes(String unit) {
        long rt = 0L;
        if (unit != null) {
            try {
                int u = Integer.parseInt(unit);
                switch (u) {
                    case 2:
                        rt = 60L;
                        break;
                    case 3:
                        rt = 3600L;
                        break;
                    case 4:
                        rt = 86400L;
                }
            } catch (Exception e) {
                log.error("单位时间包含秒数", e);
            }
        }
        return rt;
    }

    private static long lastRuntimeM(String bUU, int bNN, String iUU, int iNN, long time) {
        long rt = time;
        try {
            Calendar ctime = Calendar.getInstance();
            ctime.setTimeInMillis(time);

            ctime.set(13, 0);
            ctime.set(14, 0);

            int minutes = ctime.get(11) * 60 + ctime.get(12);
            if (minutes < bNN) {
                ctime.set(12, bNN);
                ctime.add(12, -iNN);
                rt = ctime.getTimeInMillis();
            } else {
                minutes -= bNN;
                long mtime = minutes % iNN;
                if (mtime > 0L) {
                    ctime.add(12, -(int) mtime);
                }

                rt = ctime.getTimeInMillis();
            }
        } catch (Exception e) {
            log.error("获取最近运行时刻-分", e);
        }
        return rt;
    }

    private static long lastRuntimeH(String bUU, int bNN, String iUU, int iNN, long time) {
        long rt = time;
        try {
            Calendar ctime = Calendar.getInstance();
            ctime.setTimeInMillis(time);

            ctime.set(13, 0);
            ctime.set(14, 0);

            long dtime = ctime.get(11) * 3600L + ctime.get(12) * 60L;

            long ftime = getUnitSecondes(bUU) * bNN;

            long interval = getUnitSecondes(iUU) * iNN;
            if (ftime > dtime) {
                if (bUU.equalsIgnoreCase("02")) {
                    ctime.set(12, bNN);
                    ctime.set(11, 0);
                    ctime.add(13, (int) interval);
                    rt = ctime.getTimeInMillis();
                } else if (bUU.equalsIgnoreCase("03")) {
                    ctime.set(12, 0);
                    ctime.set(11, bNN);
                    ctime.add(13, (int) interval);
                    rt = ctime.getTimeInMillis();
                } else {
                    log.warn("错误的时间配置 bUU--" + bUU + " iUU--" + iUU);
                }
            } else {
                long delt = (dtime - ftime) % interval;
                ctime.add(13, (int) delt);
                rt = ctime.getTimeInMillis();
            }
        } catch (Exception e) {
            log.error("获取最近运行时刻-时", e);
        }
        return rt;
    }

    private static long lastRuntimeD(String bUU, int bNN, String iUU, int iNN, long time) {
        long rt = time;
        try {
            int cday;
            Calendar ctime = Calendar.getInstance();
            ctime.setTimeInMillis(time);

            ctime.set(12, 0);
            ctime.set(13, 0);
            ctime.set(14, 0);

            if (bUU.equalsIgnoreCase("04")) {
                cday = ctime.get(5);
                if (bNN > cday) {
                    ctime.set(5, bNN);
                    ctime.add(5, -iNN);
                    rt = ctime.getTimeInMillis();
                } else {
                    cday -= (cday - bNN) % iNN;
                    ctime.set(5, cday);
                    rt = ctime.getTimeInMillis();
                }
            } else {
                int delt;
                if (bUU.equalsIgnoreCase("03")) {
                    cday = ctime.get(5);
                    delt = (cday - 1) % iNN;
                    if (delt <= 0) {
                        if (ctime.get(11) < bNN) {
                            ctime.set(11, bNN);
                            ctime.add(5, -iNN);
                            rt = ctime.getTimeInMillis();
                        } else {
                            ctime.set(11, bNN);
                            rt = ctime.getTimeInMillis();
                        }
                    } else {
                        cday -= delt;
                        ctime.set(5, cday);
                        ctime.set(11, bNN);
                        rt = ctime.getTimeInMillis();
                    }
                } else if (bUU.equalsIgnoreCase("02")) {
                    cday = ctime.get(5);
                    delt = (cday - 1) % iNN;
                    if (delt <= 0) {
                        if ((ctime.get(11) == 0) && (ctime.get(12) < bNN)) {
                            ctime.set(12, bNN);
                            ctime.add(5, -iNN);
                            rt = ctime.getTimeInMillis();
                        } else {
                            ctime.set(11, 0);
                            ctime.set(12, bNN);
                            rt = ctime.getTimeInMillis();
                        }
                    } else {
                        cday -= delt;
                        ctime.set(5, cday);
                        ctime.set(11, 0);
                        ctime.set(12, bNN);
                        rt = ctime.getTimeInMillis();
                    }
                } else {
                    log.warn("错误的时间配置 bUU--" + bUU + " iUU--" + iUU);
                }
            }
        } catch (Exception e) {
            log.error("获取最近运行时刻-天", e);
        }
        return rt;
    }
}