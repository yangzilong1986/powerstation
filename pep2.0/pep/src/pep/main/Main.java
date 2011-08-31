/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Timer;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import pep.bp.bussinessprocess.MainProcess;
import pep.bp.db.commLog.CommLogWriter;
import pep.bp.processor.SmsRespProcessor;
import pep.mina.common.RtuUnrespPacketChecker;
import pep.mina.protocolcodec.gb.PepGbCommunicator;
import pep.mina.protocolcodec.gb.gb376.PmPacket376CodecFactory;
import pep.mina.protocolcodec.gb.gb376.PmPacket376ServerIoHandler;

/**
 *
 * @author luxiaochung
 */
public class Main {
    public final static int PORT = 10000;

    public static void main(String[] args) throws IOException {
    	
        //组装底层通讯模块
        PepGbCommunicator rtuMap = new PepGbCommunicator();
        PmPacket376ServerIoHandler serverIoHandle = new PmPacket376ServerIoHandler(rtuMap);
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        //acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new PmPacket376CodecFactory()));

        acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
        acceptor.setHandler(serverIoHandle);
        acceptor.bind();
        System.out.println("SG376 server is listenig at port " + PORT);
        System.out.println("Idle Timeout "+acceptor.getSessionConfig().getIdleTime(IdleStatus.BOTH_IDLE));

        Timer checkTimer = new Timer();
        RtuUnrespPacketChecker checker = new RtuUnrespPacketChecker(rtuMap);
        long timestamp = 10*1000;
        checkTimer.schedule(checker, timestamp,timestamp);

        //启动通信日志记录器
        Timer commLogTimer = new Timer();
        CommLogWriter commLogWriter = CommLogWriter.getInstance();
        checkTimer.schedule(commLogWriter, CommLogWriter.maxCacheTime,CommLogWriter.maxCacheTime);

        //启动业务处理器
        SmsRespProcessor.setRtuMap(rtuMap);
        MainProcess PBProcessor = new MainProcess(rtuMap);
        PBProcessor.run();
    }
}
