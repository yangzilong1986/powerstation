package com.hzjbbis.fk.monitor.client.biz;

import com.hzjbbis.fk.monitor.exception.MonitorHandleException;
import com.hzjbbis.fk.utils.PathUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class ClientHandleFile {
    private static final ClientHandleFile handleFile = new ClientHandleFile();
    private static String tmpPath = ".";

    static {
        String root = PathUtil.getRootPath(ClientHandleFile.class);
        File f = new File(root);
        if (("bin".equals(f.getName())) || ("classes".equals(f.getName()))) {
            f = f.getParentFile();
            root = f.getPath();
        }
        if ("plugins".equalsIgnoreCase(f.getName())) {
            f = f.getParentFile();
            root = f.getPath();
        }
        tmpPath = root + File.separator + "tmp";
        f = new File(tmpPath);
        if (!(f.exists())) f.mkdir();
        tmpPath += File.separator;
        System.out.println("tmp=" + tmpPath);
    }

    public static final ClientHandleFile getHandleFile() {
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
                break;
            }
        }
        if ((position < 0L) || (btPath == null)) {
            throw new MonitorHandleException("监控管理：获取文件异常，输入非法。");
        }
        path = path.replace('\\', File.separatorChar);
        path = path.replace('/', File.separatorChar);

        if (path.lastIndexOf(File.separatorChar) > 0) {
            int index1 = path.lastIndexOf(File.separatorChar);
            String subDir = path.substring(0, index1);
            File subFile = new File(tmpPath + subDir);
            if (!(subFile.exists())) subFile.mkdirs();
        }
        path = tmpPath + path;

        int offset = inputBody.position();
        int dataLen = inputBody.remaining();
        if (dataLen == 0) return null;
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
            if (dataLen < 1048576) {
                return null;
            }
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
                inputBody.limit(inputBody.position());
                inputBody.rewind();
                break;
            }
        }
        if (position < 0L) {
            throw new MonitorHandleException("监控管理：上传文件命令异常，输入非法。");
        }
        path = path.replace('\\', File.separatorChar);
        path = path.replace('/', File.separatorChar);
        path = tmpPath + path;
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "r");
            if (position > raf.length()) {
                raf.close();
                throw new MonitorHandleException("监控管理：写文件命令异常，读文件起始位置>文件实际长度。");
            }
            int len = (int) (raf.length() - position);
            if (len <= 0) {
                return null;
            }
            int toRead = 1048576;
            toRead = Math.min(len, toRead);
            ByteBuffer body = ByteBuffer.allocate(inputBody.remaining() + toRead);

            raf.seek(position);
            body.put(inputBody);
            inputBody.rewind();
            raf.read(body.array(), inputBody.limit(), toRead);
            body.position(0);
            raf.close();
            return body;
        } catch (Exception exp) {
            throw new MonitorHandleException(exp);
        }
    }
}