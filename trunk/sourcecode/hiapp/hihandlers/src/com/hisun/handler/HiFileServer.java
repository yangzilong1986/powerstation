package com.hisun.handler;

import com.hisun.dispatcher.HiRouterOut;
import com.hisun.exception.HiException;
import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import com.hisun.message.HiMessage;
import com.hisun.protocol.tcp.HiTcpConnectionHandler;
import com.hisun.protocol.tcp.imp.HiTcpListener;
import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.*;
import java.net.Socket;

public class HiFileServer extends HiTcpListener implements HiTcpConnectionHandler {
    private static final int INNER_MSG_LENGTH = 136;
    private String fileSaveDir = null;
    private int fileNameLen;
    private int packageLenLen;
    private int fileFlag = 0;

    public HiFileServer() {
        setMinThreads(1);
        setMaxThreads(10);
    }

    public int getFileNameLen() {
        return this.fileNameLen;
    }

    public void setFileNameLen(int fileNameLen) {
        this.fileNameLen = fileNameLen;
    }

    public String getFileSaveDir() {
        return this.fileSaveDir;
    }

    public void setFileSaveDir(String fileSaveDir) {
        this.fileSaveDir = fileSaveDir;
    }

    public int getListenPort() {
        return getLocalPort();
    }

    public void setListenPort(int listenPort) {
        setLocalPort(listenPort);
    }

    public int getPackageLenLen() {
        return this.packageLenLen;
    }

    public void setPackageLenLen(int packageLenLen) {
        this.packageLenLen = packageLenLen;
    }

    public void setFileFlag(int fileFlag) {
        this.fileFlag = fileFlag;
    }

    public Object[] initParam() {
        return null;
    }

    public void processConnection(Socket socket, Object[] thData) {
        FileOutputStream fos = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            os = socket.getOutputStream();
            is = socket.getInputStream();
            if (this.log.isInfoEnabled()) {
                this.log.info("文件接收服务器： 文件接收函数开始。");
            }
            byte[] msgBuffer = new byte[256];
            byte[] tmpBuffer = new byte[256];
            byte[] buffer = new byte[10240];
            if (this.log.isDebugEnabled()) {
                this.log.debug("文件接收服务器： 接收数据包长度.");
            }
            int len = is.read(tmpBuffer, 0, this.packageLenLen);
            if (len != this.packageLenLen) {
                throw new HiException("231419");
            }

            int packageLen = 0;
            packageLen = NumberUtils.toInt(new String(tmpBuffer, 0, this.packageLenLen));

            if (packageLen <= 0) {
                throw new HiException("231419");
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("文件接收服务器： 数据包长度[" + String.valueOf(packageLen) + "]。");
            }

            if (this.log.isDebugEnabled()) {
                this.log.debug("文件接收服务器： 接收文件名。");
            }

            len = is.read(tmpBuffer, 0, this.fileNameLen);
            if (len != this.fileNameLen) {
                throw new HiException("231420");
            }

            String fileName = HiICSProperty.getWorkDir() + SystemUtils.FILE_SEPARATOR + this.fileSaveDir + SystemUtils.FILE_SEPARATOR + new String(tmpBuffer, 0, this.fileNameLen).trim();

            if (this.log.isInfoEnabled()) {
                this.log.info("文件接收服务器： 组合文件名:[" + fileName + "]");
            }

            if (this.log.isDebugEnabled()) {
                this.log.debug("文件接收服务器： 创建文件。");
            }

            File f = new File(fileName);
            fos = new FileOutputStream(f);
            if (fos == null) {
                throw new HiException("231421");
            }

            if (this.fileFlag == 1) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("文件接收服务器： 接收内部文件消息数据。");
                }
                len = is.read(msgBuffer, 0, 136);
                if (len != 136) {
                    throw new HiException("231422");
                }

                if (this.log.isDebugEnabled()) {
                    this.log.debug("文件接收服务器： 数据[" + new String(msgBuffer, 0, len) + "]");
                }

            }

            int totalLen = 0;
            if (this.fileFlag == 1) totalLen = this.fileNameLen + 136;
            else {
                totalLen = this.fileNameLen;
            }

            if (this.log.isDebugEnabled()) {
                this.log.debug("文件接收服务器： 接收文件数据。");
            }

            int remainLen = 0;
            while (totalLen < packageLen) {
                remainLen = packageLen - totalLen;
                if (remainLen > buffer.length) remainLen = buffer.length;
                len = is.read(buffer, 0, remainLen);
                if (this.log.isDebugEnabled()) {
                    this.log.debug("文件接收服务器： 接收文件数据:[" + new String(buffer, 0, len) + "]:[" + String.valueOf(totalLen) + "]:[" + String.valueOf(len) + "]");
                }

                fos.write(buffer, 0, len);
                totalLen += len;
            }
            fos.close();

            if (this.fileFlag == 1) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("文件接收服务器： 发送内部文件消息数据。");
                }

                HiMessage msg = new HiMessage("S.FilSvr", "SAF001");
                msg.setHeadItem("SCH", "rq");

                HiETF etf = HiETFFactory.createETF(new String(msgBuffer, 0, 136));

                msg.setBody(etf);
                HiRouterOut.asyncProcess(msg);
            }
            if (this.log.isInfoEnabled()) {
                this.log.info("文件接收服务器： 文件接收成功");
            }
            os.write("SUCCESS".getBytes());
            socket.close();
        } catch (IOException e) {
            try {
                if (os != null) os.write("ERROR".getBytes());
            } catch (IOException e1) {
                this.log.error(e1);
            }
            this.log.error(sm.getString(String.valueOf("231422")), e);
        } catch (HiException e) {
            try {
                if (os != null) os.write("ERROR".getBytes());
            } catch (IOException e1) {
                this.log.error(e1);
            }
            this.log.error(e);
        } finally {
            try {
                if (fos != null) fos.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                this.log.error(e);
            }
        }
    }

    public boolean checkAccessable(Socket arg0) {
        return true;
    }
}