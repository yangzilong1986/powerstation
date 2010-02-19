package com.hzjbbis.fk.monitor.biz;

import com.hzjbbis.fk.monitor.exception.MonitorHandleException;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class HandleFile {
    public static final int MAX_BLOCK = 1048576;
    private static final HandleFile handleFile = new HandleFile();

    public static final HandleFile getHandleFile() {
        return handleFile;
    }

    public ByteBuffer getFile(ByteBuffer inputBody) {
        String path = "";
        byte[] btPath = (byte[]) null;
        long position = -1L;
        int index = 0;
        for (index = 0; index < inputBody.limit(); ++index) {
            if (inputBody.get(index) == 0) {
                btPath = new byte[index];
                inputBody.get(btPath);
                path = new String(btPath);
                inputBody.get();
                position = inputBody.getLong();
                inputBody.rewind();
                break;
            }
        }
        if ((-1L == position) || (btPath == null)) throw new MonitorHandleException("监控管理：获取文件异常，输入非法。");
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "r");
            int len = (int) (raf.length() - position);
            if (len <= 0) {
                return inputBody;
            }
            int toRead = 1048576;
            toRead = Math.min(len, toRead);
            ByteBuffer body = ByteBuffer.allocate(inputBody.remaining() + toRead);
            raf.seek(position);
            body.put(inputBody);
            inputBody.rewind();
            raf.read(body.array(), inputBody.remaining(), toRead);
            body.position(0);
            raf.close();
            return body;
        } catch (Exception exp) {
            throw new MonitorHandleException(exp);
        }
    }

    public ByteBuffer putFile(ByteBuffer inputBody) {
        String path = "";
        byte[] btPath = (byte[]) null;
        long position = -1L;
        int index = 0;
        for (index = 0; index < inputBody.limit(); ++index) {
            if (inputBody.get(index) == 0) {
                btPath = new byte[index];
                inputBody.get(btPath);
                path = new String(btPath);
                inputBody.get();
                position = inputBody.getLong();
                break;
            }
        }
        int offset = inputBody.position();
        int dataLen = inputBody.remaining();
        if ((-1L == position) || (btPath == null)) throw new MonitorHandleException("监控管理：写文件命令异常，输入非法。");
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rwd");
            if (position > raf.length()) {
                raf.close();
                throw new MonitorHandleException("监控管理：写文件命令异常，写文件起始位置>文件实际长度。");
            }

            raf.setLength(position);
            raf.seek(position);
            raf.write(inputBody.array(), offset, dataLen);
            raf.close();
            position += dataLen;

            inputBody.clear();
            inputBody.put(btPath);
            inputBody.put(0);
            inputBody.putLong(position);
            inputBody.flip();
            return inputBody;
        } catch (Exception exp) {
            throw new MonitorHandleException(exp);
        }
    }
}