package javacommon.xsqlbuilder.safesql;

import javacommon.xsqlbuilder.SafeSqlProcesser;
/**
 * 过滤(单引号,反斜杠)为(两个单引号,两个反斜杠)的SafeSqlFilter<p>
 * 适用数据库(Mysql,PostgreSql)
 * @author badqiu
 *
 */
public class EscapeBackslashAndSingleQuotesSafeSqlProcesser implements SafeSqlProcesser{

	public String process(String value) {
		if(value == null) return null;
		return value.replaceAll("'", "''").replaceAll("\\\\", "\\\\\\\\");
	}

}
