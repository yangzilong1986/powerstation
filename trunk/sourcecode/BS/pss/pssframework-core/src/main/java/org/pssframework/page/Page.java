/**
 * 
 */
package org.pssframework.page;

import java.util.List;

import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Administrator
 *
 */
public class Page<T> extends cn.org.rapid_framework.page.Page<T> {
	Integer[] defaultPageSizes = new Integer[] { 10, 50, 100 };
	protected String exportReport;

	protected boolean isShowPageSizeList;

	protected Integer[] pageSizeSelectList = defaultPageSizes;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2205123853166451451L;

	public Page(int pageNumber, int pageSize, int totalCount) {
		super(pageNumber, pageSize, totalCount);
		// TODO Auto-generated constructor stub
	}

	public Page(int pageNumber, int pageSize, int totalCount, List<T> result) {
		super(pageNumber, pageSize, totalCount, result);
	}

	public Page(PageRequest<T> p, int totalCount) {
		super(p, totalCount);
	}

	/**
	 * @return the exportReport
	 */
	public String getExportReport() {
		return exportReport;
	}

	/**
	 * @return the pageSizeSelectList
	 */
	public Integer[] getPageSizeSelectList() {
		return pageSizeSelectList;
	}

	/**
	 * @return the isShowPageSizeList
	 */
	public boolean isShowPageSizeList() {
		return isShowPageSizeList;
	}

	/**
	 * @param exportReport the exportReport to set
	 */
	public void setExportReport(String exportReport) {
		this.exportReport = exportReport;
	}

	/**
	 * @param pageSizeSelectList the pageSizeSelectList to set
	 */
	public void setPageSizeSelectList(Integer[] pageSizeSelectList) {
		this.pageSizeSelectList = pageSizeSelectList;
	}

	/**
	 * @param isShowPageSizeList the isShowPageSizeList to set
	 */
	public void setShowPageSizeList(boolean isShowPageSizeList) {
		this.isShowPageSizeList = isShowPageSizeList;
	}

}
