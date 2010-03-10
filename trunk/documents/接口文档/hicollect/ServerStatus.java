package peis.interfaces.hicollect;

import java.util.*;

public class ServerStatus {
	public static ServerStatus createServerInformation() {
		return new ServerStatus();
	}

	/**
	 * ±àºÅ
	 */
	private String machNo;
	/**
	 * ÀàÐÍ
	 */
	private String type;
	
	/**
	 * ×´Ì¬
	 */
	private HashMap status;
	
	private ServerStatus() {
		this.machNo = null;
		this.type = null;
		status = new HashMap();
	}
	
	public String getMachNo() {
		return machNo;
	}
	
	public void setMachNo(String machNo) {
		this.machNo = machNo;
	}
	
	public Object get(String key) {
		return status.get(key);
	}
	
	public void put(String key, Object value) {
		this.status.put(key, value);
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
