package com.hisun.client;

import com.hisun.message.HiETF;
import com.hisun.message.HiETFFactory;
import com.hisun.message.HiMessage;
import com.hisun.protocol.tcp.HiSocketUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;

public class HiTCPInvokeService extends HiInvokeService {
    private HiETF etf = HiETFFactory.createETF();
    private Logger logger = Logger.getLogger("invokeservice");

    public String toString() {
        return this.etf.toString();
    }

    public HiTCPInvokeService() {
    }

    public HiTCPInvokeService(String code) {
        super(code);
    }

    protected HiETF doInvoke(String code, HiETF root) throws Exception {
        IpPortPair ipPortPair = info.getIpPortPair();
        log("send host [" + ipPortPair.ip + "]:[" + ipPortPair.port + "]:[" + root.toString() + "]");

        Socket socket = null;
        try {
            if (ipPortPair.sslMode == 0) socket = new Socket(ipPortPair.ip, ipPortPair.port);
            else {
                socket = createSSLSocket(ipPortPair.ip, ipPortPair.port, ipPortPair.identityFile, ipPortPair.trustFile, ipPortPair.keyPsw, ipPortPair.trustPsw);
            }

            socket.setSoTimeout(ipPortPair.tmOut * 1000);
            if (ipPortPair.isSRNConn()) root = doSRNInvoke(socket, code, root);
            else {
                root = doTCPInvoke(socket, code, root);
            }
            log("recv host [" + ipPortPair.ip + "]:[" + ipPortPair.port + "]:[" + root.toString() + "]");

            HiETF localHiETF = root;

            return localHiETF;
        } finally {
            if (socket != null) socket.close();
        }
    }

    protected HiETF doSRNInvoke(Socket socket, String code, HiETF root) throws Exception {
        HiMessage msg = new HiMessage(info.getServerName(), info.getMsgType());
        IpPortPair ipPortPair = info.getIpPortPair();
        if (ipPortPair.logSwitch) {
            msg.setHeadItem("STF", "1");
        }
        msg.setHeadItem("STC", code);
        msg.setHeadItem("SCH", "rq");
        msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
        msg.setHeadItem("ECT", "text/etf");
        msg.setBody(root);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        HiSocketUtil.write(os, msg.toString().getBytes(), 8);

        ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
        if (HiSocketUtil.read(is, buf, 8) == 0) {
            throw new Exception("invoke service:[" + root.getChildValue("TXN_CD") + "] error");
        }

        msg = new HiMessage(buf.toString());

        return msg.getETFBody();
    }

    protected HiETF doTCPInvoke(Socket socket, String code, HiETF root) throws Exception {
        root.setChildValue("TXN_CD", code);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        HiSocketUtil.write(os, root.toString().getBytes(), 8);

        ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);
        if (HiSocketUtil.read(is, buf, 8) == 0) {
            throw new Exception("invoke service:[" + root.getChildValue("TXN_CD") + "] error");
        }

        return HiETFFactory.createETF(buf.toString());
    }

    private Socket createSSLSocket(String host, int port, String identityFile, String trustFile, String keyPsw, String trustPsw) {
        Socket socket = null;
        if (StringUtils.isEmpty(host)) {
            log("host is null");
            return null;
        }

        if (StringUtils.isEmpty(identityFile)) {
            log("identityFile is null");
            return null;
        }

        if (StringUtils.isEmpty(trustFile)) {
            log("trustFile is null");
            return null;
        }

        if (StringUtils.isEmpty(keyPsw)) {
            log("keyPsw is null");
            return null;
        }

        if (StringUtils.isEmpty(trustPsw)) {
            log("trustPsw is null");
            return null;
        }
        try {
            SSLContext context = SSLContext.getInstance("SSLv3");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            InputStream identityIs = Thread.currentThread().getContextClassLoader().getResourceAsStream(identityFile);

            InputStream trustIs = Thread.currentThread().getContextClassLoader().getResourceAsStream(trustFile);

            ks.load(identityIs, keyPsw.toCharArray());
            tks.load(trustIs, trustPsw.toCharArray());

            kmf.init(ks, keyPsw.toCharArray());
            tmf.init(tks);

            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());

            socket = context.getSocketFactory().createSocket(host, port);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return socket;
    }

    protected void log(String msg) {
        if (this.logger.isInfoEnabled()) this.logger.info(msg);
    }
}