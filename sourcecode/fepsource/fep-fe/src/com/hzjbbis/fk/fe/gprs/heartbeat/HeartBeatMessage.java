package com.hzjbbis.fk.fe.gprs.heartbeat;

import com.hzjbbis.db.heartbeat.HeartBeat;
import com.hzjbbis.db.heartbeat.HeartBeatArray;
import com.hzjbbis.db.heartbeat.HeartBeatDao;
import com.hzjbbis.db.heartbeat.HeartBeatLog;

import java.sql.SQLException;
import java.util.*;

public class HeartBeatMessage {
    private int batchSize = 1000;

    private int weekNum = 2;
    private HeartBeatDao heartBeatDao;
    private static HeartBeatArray[] workList = new HeartBeatArray[35];

    private static List<HeartBeatArray> poolList = new Vector(20);

    private List<HeartBeatArray> batchSaveList = new Vector();

    private String poolListLock = "";
    private Worker worker;
    private boolean working;
    private boolean isNeedInit = false;
    private static int nInitWeekNo = 0;
    private boolean stop;

    public void initHeart(int weekNO) {
        nInitWeekNo = weekNO;
        long curTime = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(curTime);
        System.out.println("initial1：" + System.currentTimeMillis());
        try {
            List list = this.heartBeatDao.getLogResult(weekNO);
            if (list.size() > 0) {
                for (HeartBeatLog hbl : list) {
                    int id = hbl.getId();
                    boolean isSuccess = hbl.getIssuccess();
                    if (!(isSuccess)) {
                        boolean b = this.heartBeatDao.doInit(nInitWeekNo, this.weekNum);
                        if (b) {
                            this.isNeedInit = false;
                        } else {
                            this.isNeedInit = true;
                        }
                        this.heartBeatDao.updateLogResult(b, curTime, id, nInitWeekNo);
                    } else {
                        return;
                    }
                }
                break label237:
            }

            boolean b = this.heartBeatDao.doInit(nInitWeekNo, this.weekNum);
            if (b) {
                this.isNeedInit = false;
            } else {
                this.isNeedInit = true;
            }
            this.heartBeatDao.insertLogResult(nInitWeekNo, curTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        label237:
        System.out.println("initial2：" + System.currentTimeMillis());
    }

    public void putBeat(String rtua, long heartBeatTime, String deptCode) {
        HeartBeat heartBeat = new HeartBeat();
        HeartBeatArray batchSaveArray = null;

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(heartBeatTime);

        int hour = c.get(11);
        int minutes = c.get(12);
        int day = c.get(7);

        int columNum = (day - 1) * 5 + (hour * 60 + minutes) / 315;

        long binaryTemp = (hour * 60 + minutes) % 315;
        long binaryLocation = binaryTemp / 5L;

        int weekOfYear = c.get(3);
        int weekFlag = weekOfYear % this.weekNum;
        heartBeat.setRtua(rtua);
        heartBeat.setWeekOfYear(weekOfYear);
        heartBeat.setValueOrigin(String.valueOf(heartBeatTime));
        heartBeat.setDeptCode(deptCode);
        long hearbeatvalue = 1L;

        hearbeatvalue <<= (int) (62L - binaryLocation);

        heartBeat.setColumnIndex(columNum + 1);
        heartBeat.setWeekTag(weekFlag);
        heartBeat.setValue(hearbeatvalue);

        synchronized (workList) {
            HeartBeatArray heartBeats = workList[columNum];
            if (heartBeats == null) {
                heartBeats = getArrayFromPool(columNum + 1);
                workList[columNum] = heartBeats;
            } else if (heartBeats.isFull()) {
                batchSaveArray = heartBeats;
                heartBeats = getArrayFromPool(columNum + 1);
                workList[columNum] = heartBeats;
            }

            heartBeats.addHeartBeat(heartBeat);
        }

        if (batchSaveArray != null) addBatchSaveArray(batchSaveArray);
    }

    private HeartBeatArray getArrayFromPool(int columnIndex) {
        synchronized (this.poolListLock) {
            if (poolList.size() > 0) {
                HeartBeatArray array = (HeartBeatArray) poolList.remove(0);
                array.setColumnIndex(columnIndex);
                return array;
            }
        }
        return new HeartBeatArray(columnIndex, this.batchSize);
    }

    private void freeMap(HeartBeatArray array) {
        if (poolList.size() < 20) {
            array.initArray();
            poolList.add(array);
        }
    }

    private void addBatchSaveArray(HeartBeatArray heartBeats) {
        this.batchSaveList.add(heartBeats);
        if (this.worker == null) {
            this.stop = false;
            this.worker = new Worker();
            this.worker.start();
        }

        if (!(this.working)) synchronized (this.worker) {
            this.worker.notify();
        }
    }

    public void pleaseStop() {
        this.stop = true;
        if (this.worker.isAlive()) synchronized (this.worker) {
            this.worker.notify();
        }
    }

    private void doBatchSave(HeartBeatArray heartBeats) throws SQLException {
        HeartBeat heartBeat;
        int columnIndex = heartBeats.getColumnIndex();

        List orHeartBeats = new ArrayList();
        for (int i = 0; i < heartBeats.getSize(); ++i) {
            orHeartBeats.add(heartBeats.getHeartBeat(i));
        }

        List insertHeartBeats = new ArrayList();
        if (orHeartBeats.size() > 0) {
            int[] executeds = this.heartBeatDao.batchUpdate(orHeartBeats, columnIndex);
            int i = 0;
            for (Iterator localIterator = orHeartBeats.iterator(); localIterator.hasNext();) {
                heartBeat = (HeartBeat) localIterator.next();
                if (executeds[(i++)] == 0) {
                    insertHeartBeats.add(heartBeat);
                }
            }
        }

        if (insertHeartBeats.size() <= 0) {
            return;
        }
        Map map = new HashMap();
        for (HeartBeat temp : insertHeartBeats) {
            map.put(temp.getKey(), temp);
        }
        List tempList = new ArrayList();
        tempList.addAll(map.values());
        this.heartBeatDao.batchInsert(tempList, columnIndex);
        if (map.size() < this.batchSize) this.heartBeatDao.batchUpdate(insertHeartBeats, columnIndex);
    }

    public int getWeekNo(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);

        return c.get(3);
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setHeartBeatDao(HeartBeatDao heartBeatDao) {
        this.heartBeatDao = heartBeatDao;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    class Worker extends Thread {
        public Worker() {
            super("Work Threads");
        }

        public void run() {
            while (!(HeartBeatMessage.this.stop)) {
                HeartBeatArray array = null;
                HeartBeatMessage.this.working = true;
                if (!(HeartBeatMessage.this.batchSaveList.isEmpty())) {
                    array = (HeartBeatArray) HeartBeatMessage.this.batchSaveList.remove(0);
                    try {
                        HeartBeatMessage.this.doBatchSave(array);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    HeartBeatMessage.this.freeMap(array);

                    if (HeartBeatMessage.this.batchSaveList.size() > 0) {
                        continue;
                    }
                }
                if (HeartBeatMessage.this.isNeedInit) {
                    HeartBeatMessage.this.initHeart(HeartBeatMessage.nInitWeekNo);
                }

                long waitStart = System.currentTimeMillis();
                synchronized (this) {
                    try {
                        HeartBeatMessage.this.working = false;
                        System.out.println("3:" + System.currentTimeMillis());
                        super.wait(120000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                long waited = System.currentTimeMillis() - waitStart;
                if (waited > 119000L) {
                    synchronized (HeartBeatMessage.workList) {
                        for (int i = 0; i < HeartBeatMessage.workList.length; ++i) {
                            HeartBeatArray a = HeartBeatMessage.workList[i];
                            if (a.getSize() > 0) {
                                HeartBeatMessage.workList[i] = null;
                                HeartBeatMessage.this.addBatchSaveArray(a);
                            }
                        }
                    }
                }

            }

            HeartBeatMessage.this.worker = null;
            HeartBeatMessage.this.stop = true;
        }
    }
}