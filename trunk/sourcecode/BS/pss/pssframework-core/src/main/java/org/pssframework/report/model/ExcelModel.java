/**
 * 
 */
package org.pssframework.report.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class ExcelModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -514919004695156883L;

	private ExcelModel() {

	}

	public ExcelModel(String templatePath) {
		this.templatePath = templatePath;
	}

	public ExcelModel(String templatePath, String title) {
		this.templatePath = templatePath;
		this.title = title;

	}

	public ExcelModel(String templatePath, String title, String user) {
		this.templatePath = templatePath;
		this.title = title;
		this.user = user;
	}

	public ExcelModel(String templatePath, String title, String user, Date createTime) {
		this.templatePath = templatePath;
		this.title = title;
		this.user = user;
		this.createTime = createTime;
	}

	public ExcelModel(String templatePath, String title, String user, Date createTime, Map dataMap) {
		this.templatePath = templatePath;
		this.title = title;
		this.user = user;
		this.createTime = createTime;
		this.setDataMap(dataMap);
	}

	private String templatePath = "";

	private String title = "";

	private String user = "";

	private Date createTime = new Date();

	private Map dataMap;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the templatePath
	 */
	public String getTemplatePath() {
		return templatePath;
	}

	/**
	 * @param templatePath the templatePath to set
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	public Map getDataMap() {
		return dataMap;
	}

	

}
