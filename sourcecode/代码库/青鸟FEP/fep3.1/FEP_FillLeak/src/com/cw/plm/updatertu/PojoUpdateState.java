package com.cw.plm.updatertu;

import com.hzjbbis.fk.utils.HexDump;

public class PojoUpdateState {
	private int rtua;
	private String batchId;
	private int state = 0;
	private int total = 0;
	private int cur = 0;

	public PojoUpdateState(){}
	
	public PojoUpdateState(int rtu, String pici, boolean success,int sum,int index){
		rtua = rtu;
		batchId = pici;
		state = success ? 1 : 2;
		total = sum;
		cur = index;
	}
	
	public PojoUpdateState(int rtu, String pici,int sum,int index){
		rtua = rtu;
		batchId = pici;
		state = 0;
		total = sum;
		cur = index;
	}

	public int getRtua() {
		return rtua;
	}
	
	public String getRtuaHex(){
		return HexDump.toHex(rtua);
	}

	public void setRtua(int rtua) {
		this.rtua = rtua;
	}

	public String getBatchId() {
		return batchId;
	}
	
	/**
	 * ÖÕ¶ËÉý¼¶Åú´ÎºÅ
	 * @return
	 */
	public String getPc(){
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCur() {
		return cur;
	}

	public void setCur(int cur) {
		this.cur = cur;
	}
}
