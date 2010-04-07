package com.cw.plm.bpserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import com.hzjbbis.exception.MessageDecodeException;
import com.hzjbbis.exception.MessageEncodeException;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fk.message.gate.MessageGate;

/**
 * 漏点补召消息对象。适用所有Web类型下行。
 * @author Administrator
 *
 */
public class FLMessage extends MessageGate {
	public FLMessage(){
		
	}
	
	public FLMessage(FaalRequest request){
		this.getHead().setCommand(request.getType());
		try{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream serializer = new ObjectOutputStream(out);
			serializer.writeObject(request);
			this.data = ByteBuffer.wrap(out.toByteArray());
			this.head.setIntBodylen(this.data.remaining());
		}catch(Exception exp){
			throw new MessageEncodeException("Error encoding request to byte array. reason="+exp.getLocalizedMessage());
		}
	}
	
	public static final Object getReply(MessageGate gm){
		if( gm.getData().remaining()>10 ){
			try{
				ByteArrayInputStream in = new ByteArrayInputStream(gm.getData().array());
				ObjectInputStream deserializer = new ObjectInputStream(in);
				return deserializer.readObject();
			}catch(Exception exp){
				throw new MessageDecodeException("Error decoding gate message to FaalRequest. reason="+exp.getLocalizedMessage());
			}
		}
		return null;
	}
	
}
