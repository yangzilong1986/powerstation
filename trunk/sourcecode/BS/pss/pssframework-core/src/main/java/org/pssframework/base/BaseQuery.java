package org.pssframework.base;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.org.rapid_framework.page.PageRequest;

public class BaseQuery extends PageRequest implements java.io.Serializable {
	private static final long serialVersionUID = -360860474471966681L;
	public static final int DEFAULT_PAGE_SIZE = 10;
	protected Log logger = LogFactory.getLog(getClass());
    static {
        System.out.println("BaseQuery.DEFAULT_PAGE_SIZE="+DEFAULT_PAGE_SIZE);
    }
    
	public BaseQuery() {
		setPageSize(DEFAULT_PAGE_SIZE);
	}
	  
}
