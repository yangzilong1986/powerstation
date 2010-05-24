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
package org.pssframework.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.SimpleStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.Transaction;
import org.pssframework.base.EntityDao;
import org.pssframework.util.PageRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

import cn.org.rapid_framework.beanutils.BeanUtils;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

public abstract class BaseIbatis3Dao<E, PK extends Serializable> extends DaoSupport implements EntityDao<E, PK> {

	/**
	 * log
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	private SqlSessionFactory sqlSessionFactory;
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 
	 */
	protected void checkDaoConfig() throws IllegalArgumentException {
		Assert.notNull("sqlSessionFactory must be not null");
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	/**
	 * 
	 * @param sqlSessionFactory
	 */
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * 
	 * @return
	 */
	public abstract String getPrefix();

	/**
	 * 
	 * @return
	 */
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public Object getById(PK primaryKey) {
		Object object = getSqlSessionTemplate().selectOne(getFindByPrimaryKeyQuery(), primaryKey);
		return object;
	}

	/**
	 * 删除
	 */
	public void deleteById(PK id) {
		getSqlSessionTemplate().delete(getDeleteQuery(), id);
	}

	/**
	 * 保存
	 */
	public void save(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		Object primaryKey = getSqlSessionTemplate().insert(getInsertQuery(), entity);
	}

	/**
	 * 更新
	 */
	public void update(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		Object primaryKey = getSqlSessionTemplate().update(getUpdateQuery(), entity);
	}

	public void batchDelete(List<E> list) {

	}

	public void batchUpdate(List<E> list) {
		// TODO Auto-generated method stub
		//		
		//		try { 
		//
		//	           if (list != null) { 
		//
		//	              this.getSqlSessionTemplate().execute(new SqlMapClientCallback() { 
		//
		//	                  public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException { 
		//
		//	                     executor.startBatch(); 
		//
		//	                     for (int i = 0, n = list.size(); i < n; i++) { 
		//
		//	                         executor.update(statementName, list.get(i)); 
		//
		//	                     } 
		//
		//	                     executor.executeBatch(); 
		//
		//	                     return null; 
		//
		//	                  } 
		//
		//	              }); 
		//
		//	           } 
		//
		//	       } catch (Exception e) { 
		//
		//	           if (log.isDebugEnabled()) { 
		//
		//	              e.printStackTrace(); 
		//
		//	              log.debug("batchUpdate error: id [" + statementName + "], parameterObject ["+ list + "].  Cause: "+ e.getMessage()); 
		//
		//	           } 
		//
		//	       } 
		//
		//	  

	}

	public void batchInsert(List<E> list) {
		// TODO Auto-generated method stub

	}

	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * @param o
	 */
	protected void prepareObjectForSaveOrUpdate(E o) {
	}

	public String getFindByPrimaryKeyQuery() {
		return getPrefix().concat(".getById");
	}

	public String getInsertQuery() {
		return getPrefix().concat(".insert");
	}

	public String getUpdateQuery() {
		return getPrefix().concat(".update");
	}

	public String getDeleteQuery() {
		return getPrefix().concat(".delete");
	}

	public String getCountQuery() {
		return getPrefix().concat(".count");
	}

	/**
	 * 
	 * @param statementName
	 * @param pageRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Page pageQuery(final String statementName, PageRequest pageRequest) {

		Page page = new Page(pageRequest, 0);
		Map filters = creatFilters(pageRequest, page);

		//全部查询
		if (pageRequest.getPageSize() == PageRequestFactory.ALL_PAGE_SIZE) {
			page = new Page(pageRequest, PageRequestFactory.ALL_PAGE_SIZE);
			return queryPage(statementName, filters, page);
		}

		Number totalCount = countQuery(statementName, filters);
		// 获取总条数
		if (totalCount == null || totalCount.intValue() <= 0) {
			return page;
		}

		page = new Page(pageRequest, totalCount.intValue());

		return queryPage(statementName, filters, page);
	}

	private Number countQuery(final String statementName, final Map filters) {
		Number totalCount = (Number) this.getSqlSessionTemplate().selectOne(getCountQuery(statementName), filters);

		return totalCount;
	}

	protected String getCountQuery(String statementName) {
		Assert.notNull(statementName);
		return statementName.concat(".count");
	}

	/**
	 * 创建条件
	 * @param pageRequest
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map creatFilters(final PageRequest pageRequest, final Page page) {
		//其它分页参数,用于不喜欢或是因为兼容性而不使用方言(Dialect)的分页用户使用. 与getSqlMapClientTemplate().queryForList(statementName, parameterObject)配合使用
		Map filters = new HashMap();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());

		//混合两个filters为一个filters,MapAndObject.get()方法将在两个对象取值,Map如果取值为null,则再在Bean中取值
		if (pageRequest.getFilters() instanceof Map) {
			filters.putAll((Map) pageRequest.getFilters());
		} else {
			Map parameterObject = BeanUtils.describe(pageRequest.getFilters());
			filters.putAll(parameterObject);
		}

		return filters;
	}

	/**
	 * 
	 * @param statementName
	 * @param filters
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Page queryPage(final String statementName, final Map filters, Page page) {
		List list = getSqlSessionTemplate().selectList(statementName, filters, page.getFirstResult(),
				page.getPageSize());
		page.setResult(list);
		return page;
	}

	@SuppressWarnings("unchecked")
	public List findAll() {
		throw new UnsupportedOperationException();
	}

	public boolean isUnique(E entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}

	public void flush() {
		//ignore
	}

	public static class SqlSessionTemplate {
		SqlSessionFactory sqlSessionFactory;

		public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
			this.sqlSessionFactory = sqlSessionFactory;
		}

		public Object execute(SqlSessionCallback action) {
			SqlSession session = null;
			try {
				session = sqlSessionFactory.openSession();
				Object result = action.doInSession(session);
				return result;
			} finally {
				if (session != null)
					session.close();
			}
		}

		public Object selectOne(final String statement, final Object parameter) {
			return execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectOne(statement, parameter);
				}
			});
		}

		public List<?> selectList(final String statement, final Object parameter, final int offset, final int limit) {
			return (List<?>) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectList(statement, parameter, new RowBounds(offset, limit));
				}
			});
		}

		public int delete(final String statement, final Object parameter) {
			return (Integer) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.delete(statement, parameter);
				}
			});
		}

		public int update(final String statement, final Object parameter) {
			return (Integer) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.update(statement, parameter);
				}
			});
		}

		public int batchUpdate(final String statement, final List parameters) {
			return (Integer) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					for (Object o : parameters) {
						session.update(statement, 0);
					}
					return null;
				}
			});
		}

		public int insert(final String statement, final Object parameter) {
			return (Integer) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.insert(statement, parameter);
				}
			});
		}
	}

	public static interface SqlSessionCallback {

		public Object doInSession(SqlSession session);

	}

}
