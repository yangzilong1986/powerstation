package com.cw.plm.bpserver;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.IMessageCreator;
import com.hzjbbis.fk.message.gate.MessageGate;

public class FLMessageCreator implements IMessageCreator {

	public IMessage create() {
		return new FLMessage();
	}

	public IMessage createHeartBeat(int reqNum) {
		return MessageGate.createHRequest(reqNum);
	}

}
