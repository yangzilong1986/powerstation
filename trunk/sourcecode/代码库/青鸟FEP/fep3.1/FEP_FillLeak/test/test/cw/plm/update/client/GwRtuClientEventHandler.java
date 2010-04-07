package test.cw.plm.update.client;

import org.apache.log4j.Logger;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.IEventHandler;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;

public class GwRtuClientEventHandler implements IEventHandler {
	private static final Logger log = Logger.getLogger(GwRtuClientEventHandler.class);

	public void handleEvent(IEvent event) {
		if( event.getType().equals(EventType.MSG_RECV) )
			onRecvMessage( (ReceiveMessageEvent)event);
		else if( event.getType().equals(EventType.MSG_SENT) )
			onSendMessage( (SendMessageEvent)event );
	}

	private void onRecvMessage(ReceiveMessageEvent e){
		MessageGw msg = null;
		if( e.getMessage() instanceof MessageGate )
			msg = (MessageGw)((MessageGate)e.getMessage()).getInnerMessage();
		else
			msg = (MessageGw)e.getMessage();
		log.info("收到国网消息:"+msg.toString());
	}
	
	private void onSendMessage(SendMessageEvent e){
		MessageGw msg = (MessageGw)e.getMessage();
		log.info("发送国网消息:"+msg.toString());
	}
}
