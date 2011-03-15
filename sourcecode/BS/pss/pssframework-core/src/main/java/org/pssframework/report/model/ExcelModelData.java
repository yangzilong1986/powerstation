package org.pssframework.report.model;

import java.util.HashMap;

public class ExcelModelData extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8416655829008641193L;

	private String item = "";

	private Object data ;

	private ExcelModelData() {
	};

	public ExcelModelData(String item, Object data) {
		this.item = item;
		this.data = data;

	}



	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}


}
