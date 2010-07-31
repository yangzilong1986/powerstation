/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Timer;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
//import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import pep.bp.bussinessprocess.MainProcess;
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
        acceptor.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new PmPacket376CodecFactory()));
        //acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
        acceptor.setHandler(serverIoHandle);
        acceptor.bind();
        System.out.println("SG376 server is listenig at port " + PORT);

        Timer checkTimer = new Timer();
        RtuUnrespPacketChecker checker = new RtuUnrespPacketChecker();
        long timestamp = 30*1000;
        checkTimer.schedule(checker, timestamp,timestamp);

        //启动业务处理器
        MainProcess PBProcessor = new MainProcess(rtuMap);
        PBProcessor.run();
    }
}
