package com.cw.plm.bpserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cw.plm.IBpChannel;
import com.cw.plm.updatertu.UpdateRtuModule;
import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.events.BasicEventHook;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.message.gate.MessageGate;
import com.hzjbbis.fk.message.gw.MessageGw;
import com.hzjbbis.fk.message.zj.MessageZj;
import com.hzjbbis.fk.sockclient.async.event.ClientConnectedEvent;
import com.hzjbbis.fk.sockserver.event.AcceptEvent;
import com.hzjbbis.fk.sockserver.event.ClientCloseEvent;
import com.hzjbbis.fk.sockserver.event.ReceiveMessageEvent;
import com.hzjbbis.fk.sockserver.event.SendMessageEvent;
import com.hzjbbis.fk.tracelog.TraceLog;

public class FillLeakEventHandler extends BasicEventHook implements IBpChannel{
	private static final Logger log = Logger.getLogger(FillLeakEventHandler.class);
	private static final TraceLog trace = TraceLog.getTracer(FillLeakEventHandler.class);
	//configable attributes
	private int maxRequests = 5000;
	//�ڲ�����
	private List<IChannel> clients = Collections.synchronizedList(new ArrayList<IChannel>());
	private List<FaalRequest> requests = new LinkedList<FaalRequest>();
	private List<IMessage> updateMessages = new LinkedList<IMessage>();
	private Object reqLock = new Object();
	private boolean sending = false;
	private long lastSending = System.currentTimeMillis();

	public boolean sendMessage(IMessage msg ){
		if( (msg instanceof MessageZj ) || (msg instanceof MessageGw)){
			MessageGate gm = new MessageGate();
			gm.setDownInnerMessage(msg);
			msg = gm;
		}
		synchronized(reqLock){
			if( sending ){
				long timeConsume = System.currentTimeMillis() - lastSending;
				if( timeConsume > 60000 ){
					log.warn("some message sending take too much time. 1���ӻ�û�з�����ɣ�BP���ӿ��ܴ���");
					return false;
				}
				if( updateMessages.size()>= maxRequests ){
					log.warn("send requests queue overflow. ���Ͷ�����.");
					return false;
				}
				updateMessages.add(msg);
			}
			else{
				//check if there is FE connection. ����Ƿ���FE ����
				IChannel channel = null;
				synchronized(clients){
					if( clients.size()== 0 )
						return false;
					channel = clients.get(0);
				}
				sending = true;
				channel.send(msg);
				lastSending = System.currentTimeMillis();
			}
		}
		return true;
	}
	
	public boolean sendRequest(FaalRequest req){
		synchronized(reqLock){
			if( sending ){
				long timeConsume = System.currentTimeMillis() - lastSending;
				if( timeConsume > 60000 ){
					log.warn("some message sending take too much time. 1���ӻ�û�з�����ɣ�BP���ӿ��ܴ���");
					return false;
				}
				if( requests.size()>= maxRequests ){
					log.warn("send requests queue overflow. ���Ͷ�����.");
					return false;
				}
				requests.add(req);
			}
			else{
				//check if there is bp connection. ����Ƿ���BP����
				IChannel channel = null;
				synchronized(clients){
					if( clients.size()== 0 )
						return false;
					channel = clients.get(0);
				}
				FLMessage msg = new FLMessage(req);
				sending = true;
				channel.send(msg);
				lastSending = System.currentTimeMillis();
			}
		}
		return true;
	}
	
	@Override
	public void handleEvent(IEvent event) {
		if( event.getType() == EventType.MSG_RECV ){
			//���յ�ҵ���������б���
			onRecvMessage( (ReceiveMessageEvent)event);
		}
		else if( event.getType() == EventType.MSG_SENT ){
			//���ɹ��ѱ��ķ��͸�ҵ������
			onSendMessage( (SendMessageEvent)event );
		}
		else if( event.getType() == EventType.ACCEPTCLIENT ){
			//���������쳣�Ͽ������CLIENTCLOSE�¼����ܲ��ᷢ����������bpClients�������
			//ÿ��ɾ��1�����ɴﵽ��Ч����������
			for(int i=0; i<clients.size(); i++ ){
				try{
					IChannel client = clients.get(i);
					if( System.currentTimeMillis()-client.getLastIoTime() > 1000*60*30 ){
						clients.remove(i);
						if( trace.isEnabled() )
							trace.trace("garbage client removed:"+client);
						break;
					}
				}catch(Exception exp){
					break;
				}
			}
			AcceptEvent ae = (AcceptEvent)event;
			//��������֮�����жϵ�ǰ�����Ƿ���Ч���������¼�����ģ�͵������Ե��¿��ܳ���close�¼����д��������
			if (ae.getClient().getChannel().isConnected())
				clients.add(ae.getClient());
		}
		else if( event.getType() == EventType.CLIENTCLOSE ){
			ClientCloseEvent ce = (ClientCloseEvent)event;
			clients.remove(ce.getClient());
		}
		else if(event.getType() == EventType.CLIENT_CONNECTED ){
			ClientConnectedEvent ce = (ClientConnectedEvent)event;
			if( ce.getClient().getServer() == null ){
				clients.clear();
				clients.add(ce.getClient());
				
				//�����������ӳɹ��������Ҫ�������ͺ�����������
				if( requests.size() == 0 && updateMessages.size() == 0 ){
					synchronized(reqLock){
						sending = false;
					}
					return;
				}
				synchronized( reqLock ){
					IMessage msg = null;
					if( requests.size()>0 )
						msg = new FLMessage(requests.remove(0));
					else
						msg = updateMessages.remove(0);
					sending = true;
					ce.getClient().send(msg);
					lastSending = System.currentTimeMillis();
				}
			}
		}
		else
			super.handleEvent(event);
	}
	
	//receive BP up coming message.
	private void onRecvMessage(ReceiveMessageEvent e){
		IMessage msg0 = e.getMessage();
		if( msg0 instanceof MessageGate ){
			MessageGate gm = (MessageGate)msg0;
			if( gm.isHeartbeat() ){
				MessageGate rep = MessageGate.createHReply();
				e.getClient().send(rep);
				return;
			}
			//��ʷԭ��MessageGate's command may conflict with FaalRequest's type.
			Object bpUpObj = null;
			try{
				bpUpObj = FLMessage.getReply(gm);
			}catch(Exception exp){
				log.warn("BP up coming message do not contain Object: " + exp.getLocalizedMessage());
			}
			if( null == bpUpObj )
				return;
			//TODO: process BP up coming Object.©�㲹������֪ͨ
		}
		else if( msg0 instanceof MessageGw ){
			//�������ն�����ģ��
			MessageGw msg = (MessageGw)msg0;
			log.info("�յ����ģ�"+msg.toString());
			UpdateRtuModule.getInstance().precessReplyMessage(msg);
		}
		else if( msg0 instanceof MessageZj ){
			//��ʱ����ԭ�������� 
			MessageZj msg = (MessageZj)msg0;
			UpdateRtuModule.getInstance().precessReplyMessage(msg);
		}
	}
	
	private void onSendMessage(SendMessageEvent e){
		log.info("���ͱ��ģ�"+e.getMessage());
		//������Ϻ󣬼������Ƿ���������Ҫ�������͡�ֻ�гɹ�����һ�����Ĳż�������������
		//���BP���ӣ�������Ե�ʣ����ܲ��д�����������
		//check if there are more requests to send.
		if( requests.size() == 0 && updateMessages.size() == 0 ){
			synchronized(reqLock){
				sending = false;
			}
			return;
		}
		synchronized( reqLock ){
			IMessage msg = null;
			if( requests.size()>0 )
				msg = new FLMessage(requests.remove(0));
			else
				msg = updateMessages.remove(0);
			sending = true;
			e.getClient().send(msg);
			lastSending = System.currentTimeMillis();
		}
	}

	public void setMaxRequests(int maxRequests) {
		this.maxRequests = maxRequests;
	}
	
	public int getSendQueueSize(){
		return requests.size()>0 ? requests.size() : updateMessages.size();
	}

	public int getMaxRequests() {
		return maxRequests;
	}

}
