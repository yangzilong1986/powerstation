/*******************************************************************************
 * Copyright (c) 2010 PSS Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PSS Corporation - initial API and implementation
 *******************************************************************************/
package org.pssframework.xsqlbuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pssframework.datamodifier.DataModifierUtils;
import org.pssframework.xsqlbuilder.safesql.DirectReturnSafeSqlProcesser;

/**
 * 用于动态构造sql语句,与SafeSqlProcesser集成提供防止sql注入攻击,与DataModifier集成完成数据类型的转换
 * 动态构造sql示例:
 * <pre>
	String xsql = "select * from user where 1=1
		/~ and username = {username} ~/
		/~ and password = {password} ~/
		/~ and age = [age] ~/"
		/~ and sex = [sex] ~/"
		 
	<br/>
	
	Map filters = new HashMap();
	filters.put("username", "PPT");
	filters.put("age", "12");
	filters.put("sex", "");
	
	<br/>
	
	XsqlFilterResult result = xsqlBuilder.applyFilters(xsql,filters);
	<br/>
	
	结果result.getXsql()将会等于
	select * from user where 1=1 and username={username} and age=12
	而<font color=red>/~ and password = {password} ~/"</font>这一段由于在filters中password不存在而没有被构造出来
	<font color=red>/~ and sex = [sex] ~/</font>由于sex的值为空串也没有被构造出来
	<br/>
	Map acceptedFilters = result.getAcceptedFilters();
	会等于:
	{username=PPT}
	
	<br/>
	相关符号介绍:
	/~ segment... ~/ 为一个条件代码块
	{key} 过滤器中起标记作用的key,作为后面可以替换为sql的?,或是hql的:username标记
	[key] 将直接替换为key value
 * </pre>
 * 
 * <pre>
 * 数据类型转换示例:
 * select * from user where and 1=1 /~ age={age?int} ~/
 * 将会将Map filters中key=age的值转换为int类型
 * </pre>
 * 
 * @author PSS
 *
 */
public class XsqlBuilder {

//	private static final String MARK_KEY_START_CHAR = "{";
//	private static final String MARK_KEY_END_CHAR = "}";
//	
//	private static final String REPLACE_KEY_START_CHAR = "[";
//	private static final String REPLACE_KEY_END_CHAR = "]";

	protected String markKeyStartChar = "{";
	protected String markKeyEndChar = "}";
	
	protected String replaceKeyStartChar = "[";
	protected String replaceKeyEndChar = "]";
	
	final static Log logger = LogFactory.getLog(XsqlBuilder.class);
	
	private boolean isRemoveEmptyString = true;
	private SafeSqlProcesser safeSqlProcesser = DirectReturnSafeSqlProcesser.INSTANCE;
	
	public XsqlBuilder() {
	}
	
	public XsqlBuilder(boolean isRemoveEmptyStrings) {
		setRemoveEmptyString(isRemoveEmptyStrings);
	}

	public XsqlBuilder(SafeSqlProcesser safeSqlProcesser) {
		setSafeSqlProcesser(safeSqlProcesser);
	}
	
	public XsqlBuilder(boolean isRemoveEmptyStrings,SafeSqlProcesser safeSqlProcesser) {
		setRemoveEmptyString(isRemoveEmptyStrings);
		setSafeSqlProcesser(safeSqlProcesser);
	}
	
	/** 
	 * 是否移除空字符串,默认为true
	 **/
	public boolean isRemoveEmptyString() {
		return isRemoveEmptyString;
	}

	public void setRemoveEmptyString(boolean isRemoveEmptyStrings) {
		this.isRemoveEmptyString = isRemoveEmptyStrings;
	}

	public SafeSqlProcesser getSafeSqlProcesser() {
		return safeSqlProcesser;
	}

	public void setSafeSqlProcesser(SafeSqlProcesser safeSqlProcesser) {
		if(safeSqlProcesser == null)
			throw new NullPointerException("'safeSqlProcesser' property must be not null");
		this.safeSqlProcesser = safeSqlProcesser;
	}

	/**
	 * 将xsql中的{key}标志替换为一个字符串
	 * @param xsql 格式: select * from user where username = {username}
	 * @param acceptedFilters 过滤器
	 * @param str 输入的字符串
	 * @return 如果acceptedFilters存在{username}的key，则返回select * from user where username=str
	 * 		否则没有影响，直接返回xsql
	 */
	public String replaceKeyMaskWithString(String xsql, Map acceptedFilters,String str) {
		for(Iterator it = acceptedFilters.keySet().iterator();it.hasNext();){
			Object key = it.next();
			xsql = StringUtils.replace(xsql, markKeyStartChar+key+markKeyEndChar, str);
		}
		return xsql;
	}
	/**
	 * 将xsql中的{key}标志替换与key相对应的值
	 * @param xsql 格式: select * from user where username = {username}
	 * @param acceptedFilters 过滤器
	 * @param str 输入的字符串
	 * @return 如果acceptedFilters存在{username}相对应的key=username的值，则返回select * from user where username=keyvalue
	 * 		否则没有影响，直接返回xsql
	 */
	public String replaceKeyMaskWithKeyValue(String xsql, Map acceptedFilters) {
		for(Iterator it = acceptedFilters.keySet().iterator();it.hasNext();){
			Object key = it.next();
			xsql = StringUtils.replace(xsql, markKeyStartChar+key+markKeyEndChar, acceptedFilters.get(key).toString());
		}
		return xsql;
	}
	
	/**
	 * 根据sourceXsql动态构造SQL语句,将{key}替换为?,[key]替换为key value
	 * @param sourceXsql 
	 * @param filters 过滤器,可以为Object or Map
	 * @return 
	 */
	public XsqlFilterResult generateSql(String sourceXsql, Object filters) {
		XsqlFilterResult sfr = applyFilters(sourceXsql, filters);
		return new XsqlFilterResult(replaceKeyMaskWithString(sfr.getXsql(), sfr.getAcceptedFilters(),"?"),sfr.getAcceptedFilters());
	}
	
	public XsqlFilterResult generateSql(String sourceXsql, Map filters) {
		return generateSql(sourceXsql,(Object)filters);
	}
	
	public XsqlFilterResult generateSql(String sourceXsql, Map filtersMap,Object filtersBean) {
		return generateSql(sourceXsql,new MapAndObject(filtersMap,filtersBean));
	}
	
	/**
	 * 根据sourceXsql动态构造Hibernate的hql语句,将{key}替换为:key,[key]替换为key value
	 * @param sourceXsql 
	 * @param filters 过滤器,可以为Object or Map
	 * @return 
	 */
	public XsqlFilterResult generateHql(String sourceXsql, Object filters) {
		
		XsqlFilterResult sfr = applyFilters(sourceXsql, filters);
		
        String resultHql = sfr.getXsql();
		for(Iterator it = sfr.getAcceptedFilters().keySet().iterator();it.hasNext();){
			Object key = it.next();
			resultHql = StringUtils.replace(resultHql, markKeyStartChar+key+markKeyEndChar, ":"+key);
		}
        
		return new XsqlFilterResult(resultHql,sfr.getAcceptedFilters());
	}
	
	public XsqlFilterResult generateHql(String sourceXsql, Map filters) {
		return generateHql(sourceXsql,(Object)filters);
	}
	
	public XsqlFilterResult generateHql(String sourceXsql, Map filtersMap,Object filtersBean) {
		return generateHql(sourceXsql,new MapAndObject(filtersMap,filtersBean));
	}
	/**
	 * 使用过滤器过滤
	 * @param xsql 数据格式：select * from user /where ~username={username}~/
	 * 			其中/~,~/为一个条件的结束与开始标志,{username}为过滤使用的key,key取值为username
	 * 			如果username在filters作为key存在,则返回结果为select * from user where username={username}
	 * 			否则过滤后结果为select * from user
	 * @param filters 过滤器,可以为Object or Map
	 * @return
	 */
	public XsqlFilterResult applyFilters(String xsql, Object filters) {
		if(xsql == null) throw new IllegalArgumentException("'sql' must be not null");
		return applyFilters(new StringBuffer(xsql), filters);
	}
	
	public XsqlFilterResult applyFilters(String xsql, Map filters) {
		return applyFilters(xsql, (Object)filters);
	}
	
	public XsqlFilterResult applyFilters(String xsql, Map filtersMap,Object filtersBean) {
		return applyFilters(new StringBuffer(xsql), filtersMap,filtersBean);
	}
	
	private XsqlFilterResult applyFilters(StringBuffer xsql, Map filtersMap,Object filtersBean) {
		return applyFilters(xsql,new MapAndObject(filtersMap,filtersBean));
	}
	/**
	 * @see #applyFilters(String, Map)
	 */
	private XsqlFilterResult applyFilters(StringBuffer xsql, Object filters) {
		LinkedHashMap acceptedFilters = new LinkedHashMap();
		for (int i = 0, end = 0, start = xsql.indexOf("/~"); ((start = xsql.indexOf("/~", end)) >= 0); i++) {
			end = xsql.indexOf("~/", start);
			KeyMetaDatas metadatas = getKeyMetaDatas(xsql, start, end);
			if(metadatas.markKeys.isEmpty() && metadatas.replaceKeys.isEmpty()) 
				throw new IllegalArgumentException("Not key found in segment="+xsql.substring(start, end+2));
			
			if (isAcceptedAllKeys(filters,metadatas.markKeys) && isAcceptedAllKeys(filters, metadatas.replaceKeys)) {
				if(logger.isDebugEnabled()) {
					logger.debug("The filter markKeys=" + metadatas.markKeys+" replaceKeys="+metadatas.replaceKeys + " is accepted on segment="+xsql.substring(start,end+2));
				}
				String segment = xsql.substring(start+2,end);
				segment = mergeMarkKeysIntoAcceptedFilters(filters, acceptedFilters, metadatas, segment);
				segment = replaceReplaceKeysWithValues(filters, metadatas.replaceKeys, segment);
				xsql.replace(start, end+2, segment);
				end = start + segment.length();
			} else {
				if(logger.isDebugEnabled()) {
					logger.debug("The filter markKeys=" + metadatas.markKeys+" replaceKeys="+metadatas.replaceKeys+ " is removed from the query on segment="+xsql.substring(start,end+2));
				}
				xsql.replace(start, end + 2, "");
				end = start;
			}
		}
		return new XsqlFilterResult(xsql.toString(),acceptedFilters);
	}

	private String mergeMarkKeysIntoAcceptedFilters(Object filters, Map acceptedFilters, KeyMetaDatas metadatas, String segment) {
		for(int n = 0; n < metadatas.markKeys.size(); n++) {
			String dataModifierExpression = (String)metadatas.markKeys.get(n);
			String key = DataModifierUtils.getModifyVariable(dataModifierExpression);
			Object value = DataModifierUtils.modify(dataModifierExpression, ObjectUtils.getProperty(filters, key));
			acceptedFilters.put(key, value);
			segment = StringUtils.replace(segment, markKeyStartChar+dataModifierExpression+markKeyEndChar, markKeyStartChar+key+markKeyEndChar);
		}
		return segment;
	}
	
	private String replaceReplaceKeysWithValues(Object filters, List replaceKeys, String segment) {
		for(int n = 0; n < replaceKeys.size(); n++) {
			String dataModifierExpression = (String)replaceKeys.get(n);
			String key = DataModifierUtils.getModifyVariable(dataModifierExpression);
			String value = DataModifierUtils.modify(dataModifierExpression, ObjectUtils.getProperty(filters, key)).toString();
			value = safeSqlProcesser.process(value);
			segment = StringUtils.replace(segment, replaceKeyStartChar+dataModifierExpression+replaceKeyEndChar, value);
		}
		return segment;
	}
	
	private boolean isAcceptedAllKeys(Object filters, List keys) {
		for(int n = 0; n < keys.size(); n++) {
			String dataModifierExpression = (String)keys.get(n);
			String key = DataModifierUtils.getModifyVariable(dataModifierExpression);
			Object value = ObjectUtils.getProperty(filters,key);
			if(!isValuePopulated(value, isRemoveEmptyString)) {
				return false;
			}
		}
		return true;
	}
	
	KeyMetaDatas getKeyMetaDatas(StringBuffer xsql,int start,int end) {
		List markKeys = getKeys(xsql, start, end,markKeyStartChar,markKeyEndChar);
		List replaceKeys = getKeys(xsql, start, end, replaceKeyStartChar, replaceKeyEndChar);
		return new KeyMetaDatas(markKeys,replaceKeys);
	} 
	
	private List getKeys(StringBuffer xsql, int start, int end,String keyPrifix,String keySuffix) {
		List results = new ArrayList();
		int keyStart = start;
		int keyEnd = keyStart;
		while(true) {
			//get keyStart
			keyStart = xsql.indexOf(keyPrifix,keyStart);
			if(keyStart > end || keyStart < 0)
				break;
			
			//get keyEnd
			keyEnd = xsql.indexOf(keySuffix,keyStart+1);
			if(keyEnd > end  || keyEnd <0)
				break;
			
			//get key string
			String key = xsql.substring(keyStart+1,keyEnd);
			results.add(key);
			keyStart = keyEnd + 1;
		}
		return results;
	}

	protected boolean isValuePopulated(Object value, boolean isRemoveEmptyStrings) {
		if (value == null) {
			return false;
		} 
		if (isRemoveEmptyStrings && value instanceof String) {
			return ((String) value).length() > 0;
		} else {
			return true;
		}
	}
	
	class KeyMetaDatas {
		List markKeys;
		List replaceKeys;
		public KeyMetaDatas(List markKeys, List replaceKeys) {
			this.markKeys = markKeys;
			this.replaceKeys = replaceKeys;
		}
	}
	
	public static class XsqlFilterResult {
		private String xsql;
		private Map acceptedFilters;
		public XsqlFilterResult(String xsql, Map acceptedFilters) {
			this.setXsql(xsql);
			this.setAcceptedFilters(acceptedFilters);
		}
		public void setXsql(String xsql) {
			this.xsql = xsql;
		}
		public String getXsql() {
			return xsql;
		}
		public void setAcceptedFilters(Map acceptedFilters) {
			this.acceptedFilters = acceptedFilters;
		}
		public Map getAcceptedFilters() {
			return acceptedFilters;
		}
	}
	
	//copy from spring
	private static class StringUtils {
		public static String replace(String inString, String oldPattern, String newPattern) {
			if (inString == null) {
				return null;
			}
			if (oldPattern == null || newPattern == null) {
				return inString;
			}

			StringBuffer sbuf = new StringBuffer();
			// output StringBuffer we'll build up
			int pos = 0; // our position in the old string
			int index = inString.indexOf(oldPattern);
			// the index of an occurrence we've found, or -1
			int patLen = oldPattern.length();
			while (index >= 0) {
				sbuf.append(inString.substring(pos, index));
				sbuf.append(newPattern);
				pos = index + patLen;
				index = inString.indexOf(oldPattern, pos);
			}
			sbuf.append(inString.substring(pos));

			// remember to append any characters to the right of a match
			return sbuf.toString();
		}
	}
}
