package org.pssframework.xsqlbuilder;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.dialect.Dialect;
import org.pssframework.xsqlbuilder.safesql.DirectReturnSafeSqlProcesser;
import org.pssframework.xsqlbuilder.safesql.EscapeBackslashAndSingleQuotesSafeSqlProcesser;
import org.pssframework.xsqlbuilder.safesql.EscapeSingleQuotesSafeSqlProcesser;
/**
 * 工厂方法,提供不同数据库的SafeSqlProcesser实例生成工厂
 * @author badqiu
 *
 */
public class SafeSqlProcesserFactory {
	
	private SafeSqlProcesserFactory(){}
	
	public static SafeSqlProcesser getMysql() {
		return new EscapeBackslashAndSingleQuotesSafeSqlProcesser();
	}
	
	public static SafeSqlProcesser getPostgreSql() {
		return new EscapeBackslashAndSingleQuotesSafeSqlProcesser();
	}
	
	public static SafeSqlProcesser getMsSqlServer() {
		return new EscapeSingleQuotesSafeSqlProcesser();
	}
	
	public static SafeSqlProcesser getOracle() {
		return new EscapeSingleQuotesSafeSqlProcesser();
	}
	
	public static SafeSqlProcesser getDB2() {
		return new EscapeSingleQuotesSafeSqlProcesser();
	}
	
	public static SafeSqlProcesser getSybase() {
		return new EscapeSingleQuotesSafeSqlProcesser();
	}
	
	private static Map cacheDialectMapping = new HashMap();
	public static SafeSqlProcesser getFromCacheByHibernateDialect(Dialect dialect) {
		SafeSqlProcesser safeSqlProcesser = (SafeSqlProcesser)cacheDialectMapping.get(dialect);
		if(safeSqlProcesser == null) {
			safeSqlProcesser = getByHibernateDialect(dialect);
			cacheDialectMapping.put(dialect, safeSqlProcesser);
		}
		return safeSqlProcesser;
	}

	public static SafeSqlProcesser getByHibernateDialect(Dialect dialect) {
		SafeSqlProcesser result = null;
		String dialectClass = dialect.getClass().getSimpleName();
		if(dialectClass.indexOf("MySQL") >= 0) {
			result = SafeSqlProcesserFactory.getMysql();
		}else if(dialectClass.indexOf("Oracle") >= 0) {
			result = SafeSqlProcesserFactory.getOracle();
		}else if(dialectClass.indexOf("DB2") >= 0) {
			result = SafeSqlProcesserFactory.getDB2();
		}else if(dialectClass.indexOf("Postgre") >= 0) {
			result = SafeSqlProcesserFactory.getPostgreSql();
		}else if(dialectClass.indexOf("Sybase") >= 0) {
			result = SafeSqlProcesserFactory.getSybase();
		}else if(dialectClass.indexOf("SQLServer") >= 0) {
			result = SafeSqlProcesserFactory.getMsSqlServer();
		}else {
			result = new DirectReturnSafeSqlProcesser();
		}
		return result;
	}
}
