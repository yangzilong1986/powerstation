package org.pssframework.xsqlbuilder.safesql;

import org.pssframework.xsqlbuilder.SafeSqlProcesser;
/**
 * 过滤单个单引号为双引号的SafeSqlFilter<p>
 * 适用数据库(MS SqlServer,Oracle,DB2)
 * @author badqiu
 *
 */
public class EscapeSingleQuotesSafeSqlProcesser implements SafeSqlProcesser{

	public String process(String value) {
		if(value == null) return null;
		return value.replaceAll("'", "''");
	}

}
