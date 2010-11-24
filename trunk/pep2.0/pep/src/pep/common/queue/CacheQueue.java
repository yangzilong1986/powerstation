/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.common.queue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.slf4j.*;
import pep.common.message.MessageLoader;

import pep.common.message.IMessage;
/**
 *
 * @author Thinkpad
 */
public class CacheQueue {
	//辅助属性
	private static String s_cachePath;
	static{
		String workDir = System.getProperty("user.dir");
		s_cachePath = workDir + File.separator + "cache";
		File f = new File(s_cachePath);
		if( !f.exists() )
			f.mkdir();
	}

	//可配置属性
	private int maxSize = 10000;		//当超过maxSize，写文件。写文件消息数maxSize-minSize
	private int minSize = 2000;
	private int maxFileSize = 20;		//缓存文件最大的大小（M）字节。
	private int fileCount = 20;		//缓存文件的个数。
	private String key="undefine";		//队列所属服务器端口
	private MessageLoader messageLoader;
	private String cachePath;

	//辅助属性
	private final static Logger log = LoggerFactory .getLogger(CacheQueue.class);
	private final ArrayList<LinkedList<IMessage>> queue = new ArrayList<LinkedList<IMessage>>(IMessage.PRIORITY_MAX+1);
	private final ArrayList<IMessage> tempQueue = new ArrayList<IMessage>(maxSize);
	private final Object fileLock = new Object();
	private long minInterval = 10*1000;		//最快读缓存间隔10秒。以免堵塞
	private long lastCacheRead = System.currentTimeMillis() - minInterval;
	private long lastCacheWrite = System.currentTimeMillis() - minInterval;

	@SuppressWarnings("unchecked")
	public CacheQueue(){
		for(int i=0; i<IMessage.PRIORITY_MAX+1; i++ ){
			LinkedList<IMessage> list = new LinkedList<IMessage>();
			queue.add(list);
		}
		try{
			Class<MessageLoader> clz = (Class<MessageLoader>)Class.forName(MessageLoader.class.getPackage().getName() + ".MultiProtoMessageLoader");
			messageLoader = (MessageLoader)clz.newInstance();
		}catch(ClassNotFoundException notFoundExp){
			log.error(notFoundExp.getLocalizedMessage(),notFoundExp);
		}catch(Exception e){
			log.error(e.getLocalizedMessage(),e);
		}
		cachePath = s_cachePath;
	}

	/**
	 * 把消息放入优先级队列。
	 * @param message
	 */
	public void offer(IMessage message){
		if( message.getPriority()< IMessage.PRIORITY_LOW || message.getPriority()>IMessage.PRIORITY_MAX )
			message.setPriority(IMessage.PRIORITY_NORMAL);
		synchronized(queue){
			if( size()>= maxSize ){
				//滚动到缓存文件。
				asyncSaveQueue();
			}
			queue.get(message.getPriority()).add(message);
			queue.notifyAll();
		}
	}

	/**
	 * 从优先级队列中取出消息。如果为空，返回null。不阻塞。
	 * @return
	 */
	public IMessage poll(){
		synchronized(queue){
			while(true){
				//首先从队列中取消息
				for(int i=IMessage.PRIORITY_MAX; i>=0; i-- ){
					LinkedList<IMessage> list = queue.get(i);
					if( list.size()>0 )
						return list.remove();
				}
				//理论上，再从队列取。考虑到文件访问的效率。在空闲时刻，直接把文件上传到前置机
				if( _loadFromFile() )
					continue;
				return null;
			}
		}
	}

	/**
	 * peek message from queue if possible.
	 * @return
	 */
	public IMessage peek(){
		synchronized(queue){
			while(true){
				//首先从队列中取消息
				for(int i=IMessage.PRIORITY_MAX; i>=0; i-- ){
					LinkedList<IMessage> list = queue.get(i);
					if( list.size()>0 )
						return list.getFirst();
				}
				//理论上，再从队列取。考虑到文件访问的效率。在空闲时刻，直接把文件上传到前置机
				if( _loadFromFile() )
					continue;
				return null;
			}
		}
	}

	/**
	 * 从优先级队列中取出消息。
	 * @return
	 */
	public IMessage take(){
		synchronized(queue){
			while(true){
				//首先从队列中取消息
				for(int i=IMessage.PRIORITY_MAX; i>=0; i-- ){
					LinkedList<IMessage> list = queue.get(i);
					if( list.size()>0 )
						return list.remove();
				}
				// _loadFromFile()返回true，如果已经加载了消息。
				if( _loadFromFile() )
					continue;
				try{
					queue.wait();
				}catch(InterruptedException e){
					return null;
				}
			}
		}
	}

	public int size(){
		int size = 0;
		for(int i=IMessage.PRIORITY_MAX; i>=0; i-- ){
			LinkedList<IMessage> list = queue.get(i);
			size += list.size();
		}
		return size;
	}

	//当对象需要被丢弃时，如系统关闭导致对象释放，把所有数据缓存到文件。
	public void dispose(){
		log.info("cacheQueue disposed. key="+key);
		synchronized(tempQueue){
			for(int i=0; i<=IMessage.PRIORITY_MAX; i++ ){
				LinkedList<IMessage> ar = queue.get(i);
				tempQueue.addAll(ar);
				ar.clear();
			}
			lastCacheWrite = System.currentTimeMillis();
			__saveToFile();
		}
	}

	/**
	 * 异步保存消息队列。
	 */
	public void asyncSaveQueue(){
		int count = maxSize-minSize;
		synchronized(tempQueue){
			long now = System.currentTimeMillis();
			if( now - lastCacheWrite < this.minInterval )
				return;
			//优先级低的部分全部写缓存。
			for(int i=0; i<=IMessage.PRIORITY_MAX; i++ ){
				LinkedList<IMessage> ar = queue.get(i);
				tempQueue.addAll(ar);
				count -= ar.size();
				ar.clear();
				if( count<= 0 )
					break;
			}
			//开始异步写消息到缓存文件。
			lastCacheWrite = System.currentTimeMillis();
			CacheFileWriteThread t = new CacheFileWriteThread();
			t.start();
		}
	}

	private void __saveToFile(){
		synchronized(fileLock){
			PrintWriter out;
			String filename = _findWriteCacheFileName();
			try
			{
				out = new PrintWriter(new BufferedWriter(new FileWriter(filename,true)));
				_saveMessages(out);
				out.close();
				out = null;
			}
			catch(Exception exp)
			{
				StringBuffer sb = new StringBuffer();
				sb.append("消息队列保存异常,filename=").append(filename);
				sb.append(",异常原因：").append(exp.getLocalizedMessage());
				log.error(sb.toString(),exp);
				return ;
			}
		}
	}

	private boolean _loadFromFile(){
		long now = System.currentTimeMillis();
		if( now-this.lastCacheRead < this.minInterval )
			return false;
		this.lastCacheRead = System.currentTimeMillis();

		synchronized(fileLock){
			RandomAccessFile raf = null;
			String filename = _findReadCacheFileName();
			if( null == filename )
				return false;
			if( log.isDebugEnabled() )
				log.debug("begin read cache file(开始加载缓存文件):"+filename);
			try{
				raf = new RandomAccessFile(filename,"rwd");
				String serial;
				int count =0;
				int maxCount = this.maxSize - this.minSize;

				while( null != (serial=raf.readLine()) ){
					if( serial.length()<26 ){
						log.warn("读缓存错误：读取无效内容："+serial);
						continue;
					}
					//注意，目前只支持浙江规约格式消息
					IMessage msg = messageLoader.loadMessage(serial);
					if( null == msg )
						continue;
					this.offer(msg);		//从缓存加载成功
					count++;
					if( count>= maxCount )
						break;
				}
				if( count>0 && log.isInfoEnabled() )
					log.info("本次从缓存文件装载消息情况：file="+filename+",count="+count);

				//如果缓冲区数据太多，则需要把剩余消息移到文件头。
				long readPos = raf.getFilePointer();
				long writePos = 0;
				int n = 0;
				long remaining = raf.length() - readPos;

				byte buffer[] = new byte[512*1024];
				while( remaining>0 ){
					raf.seek(readPos);
					n = raf.read(buffer);
					if( n<=0 )
						break;
					raf.seek(writePos);
					raf.write(buffer,0,n);
					readPos += n;
					writePos += n;
					remaining -= n;
				}
				raf.setLength(writePos);
				raf.close();
				raf = null;
				return count>0;
			}catch(Exception exp){
				StringBuffer sb = new StringBuffer();
				sb.append("从缓存装载到消息队列异常,filename=").append(filename);
				sb.append(",原因：").append(exp.getLocalizedMessage());
				log.error(sb.toString(),exp);
				if( null != raf ){
					try{
						raf.close();
						raf = null;
					}
					catch(Exception e){}
				}
			}
		}
		return false;
	}

	private void _saveMessages(PrintWriter out){
		synchronized(tempQueue){
			String strMsg = null;
			for( IMessage msg : tempQueue ){
				strMsg = messageLoader.serializeMessage(msg);
				if( null != strMsg && strMsg.length()>0  )
					out.println(strMsg);
			}
			tempQueue.clear();
		}
	}

	/**
	 * 为了写缓存，获取一个缓存文件名称。文件名称格式： cache-port(key)-(i).txt
	 * @return filename
	 */
	private String _findWriteCacheFileName(){
		String fname0 = "cache-"+key+"-";
		File f = new File(cachePath);
		File [] list = f.listFiles();
		if( null == list )
			list = new File[0];

		File file, oldestFile = null;
		Date oldDate = new Date(0),curDate = new Date(0);
		boolean found;
		for(int i=0; i<fileCount; i++ ){
			String cname = fname0+i+".txt";
			found = false;
			for(int j=0;j<list.length; j++){
				if( !list[j].isFile() ) continue;
				if( list[j].getName().indexOf(fname0)<0 )
					continue;

				if( null == oldestFile ){
					oldestFile = list[j];
					oldDate.setTime(oldestFile.lastModified());
				}
				else{
					curDate.setTime(list[j].lastModified());
					if( curDate.before(oldDate)){
						oldDate.setTime(curDate.getTime());
						oldestFile = list[j];
					}
				}
				if( cname.equals(list[j].getName()) ){
					found = true;
					file = new File(cachePath+File.separator+cname);
					if( file.length()>= maxFileSize*1024*1024 )
						continue;
					else
						return file.getPath();
				}
			}
			if( !found )
				return cachePath+File.separator + cname;
		}
		//
		if( null != oldestFile ){
			String opath = oldestFile.getPath();
			oldestFile.delete();
			return opath;
		}
		return cachePath+File.separator + fname0+"exp.txt";
	}

	/**
	 * 为了读缓存，获取一个缓存文件名称。文件名称格式： cache-port(key)-(i).txt
	 * @return filename
	 */
	public String _findReadCacheFileName(){
		String fname0 = "cache-"+key+"-";
		File f = new File(cachePath);
		File [] list = f.listFiles();
		if( null == list ){
			log.warn(f.getPath()+":列表错误。null==list");
			return null;
		}

		File file;
		for(int j=0;j<list.length; j++){
			file = list[j];
			if( !file.isFile() || file.length()<=0 ) continue;

			String s = file.getName();
			if( s.indexOf(fname0) == 0 )
				return file.getPath();
		}
		if( log.isDebugEnabled() )
			log.debug(f.getPath()+":目录下无缓存文件。");
		return null;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		if( maxSize<= 100 )
			maxSize = 100;
		else if( maxSize> 200000 )
			maxSize = 200000;
		this.maxSize = maxSize;
		_adjustMinSize();
	}

	private void _adjustMinSize(){
		if( minSize <=(maxSize>>4) || minSize>(maxSize>>2) )
			minSize = maxSize>>4;
	}

	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
		_adjustMinSize();
	}

	public int getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(int maxFileSize) {
		if( maxFileSize<=0 || maxFileSize> 500 )
			maxFileSize = 500;		//最大500M
		this.maxFileSize = maxFileSize;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public MessageLoader getMessageLoader() {
		return messageLoader;
	}

	public void setMessageLoader(MessageLoader messageLoader) {
		this.messageLoader = messageLoader;
	}

	/**
	 * 异步写缓存文件.数据来源： tempQueue
	 * @author hbao
	 *
	 */
	class CacheFileWriteThread extends Thread {
		public CacheFileWriteThread(){
			super("cacheWrite-"+key);
		}
		@Override
		public void run() {
			__saveToFile();
		}
	}

	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
}
