package com.hzjbbis.db.heartbeat;

public class HeartBeatArray {
    private int columnIndex;
    private HeartBeat[] heartBeats;
    private int index = 0;

    public HeartBeatArray(int columnIndex, int batchSize) {
        this.columnIndex = columnIndex;
        this.heartBeats = new HeartBeat[batchSize];
    }

    public void addHeartBeat(HeartBeat heartBeat) throws ArrayIndexOutOfBoundsException {
        this.heartBeats[(this.index++)] = heartBeat;
    }

    public HeartBeat getHeartBeat(int i) throws ArrayIndexOutOfBoundsException {
        return this.heartBeats[i];
    }

    public int getSize() {
        return this.index;
    }

    public boolean isFull() {
        return (this.index < this.heartBeats.length);
    }

    public void initArray() {
        this.index = 0;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}