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

import static cn.org.rapid_framework.util.SqlRemoveUtils.removeFetchKeyword;
import static cn.org.rapid_framework.util.SqlRemoveUtils.removeOrders;
import static cn.org.rapid_framework.util.SqlRemoveUtils.removeSelect;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;
import org.pssframework.base.EntityDao;
import org.pssframework.xsqlbuilder.SafeSqlProcesser;
import org.pssframework.xsqlbuilder.SafeSqlProcesserFactory;
import org.pssframework.xsqlbuilder.XsqlBuilder;
import org.pssframework.xsqlbuilder.XsqlBuilder.XsqlFilterResult;
import org.pssframework.xsqlbuilder.safesql.DirectReturnSafeSqlProcesser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import cn.org.rapid_framework.util.CollectionHelper;

/**
 * 
 * @author Baocj
 *
 * @param <E>
 * @param <PK>
 */
public abstract class BaseHibernateDao<E, PK extends Serializable> extends HibernateDaoSupport implements
		EntityDao<E, PK> {
	/**
	 * Logger for subclass
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public long queryForLong(final String queryString) {
		return queryForLong(queryString, new Object[] {});
	}

	public long queryForLong(final String queryString, Object value) {
		return queryForLong(queryString, new Object[] { value });
	}

	public long queryForLong(final String queryString, Object[] values) {
		List list = getHibernateTemplate().find(queryString, values);
		Number n = (Number) CollectionHelper.findSingleObject(list);
		return n.longValue();
	}

	/**
	 * 得到全部数据,但执行分页
	 * @param pageRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page findAll(final PageRequest pageRequest) {
		return (Page) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				StringBuffer queryString = new StringBuffer(" FROM ").append(getEntityClass().getSimpleName());
				String countQueryString = "SELECT count(*) " + queryString.toString();
				if (StringUtils.hasText(pageRequest.getSortColumns())) {
					queryString.append(" ORDER BY " + pageRequest.getSortColumns());
				}

				Query query = session.createQuery(queryString.toString());
				Query countQuery = session.createQuery(countQueryString);
				return executeQueryForPage(pageRequest, query, countQuery);
			}
		});
	}

	public Page pageQuery(final String query, final PageRequest pageRequest) {
		final String countQuery = "select count(*) " + removeSelect(removeFetchKeyword((query)));
		return pageQuery(query, countQuery, pageRequest);
	}

	public Page pageQuery(final String query, String countQuery, final PageRequest pageRequest) {
		Map otherFilters = new HashMap(1);
		otherFilters.put("sortColumns", pageRequest.getSortColumns());

		XsqlBuilder builder = getXsqlBuilder();

		//混合使用otherFilters与pageRequest.getFilters()为一个filters使用
		XsqlFilterResult queryXsqlResult = builder.generateHql(query, otherFilters, pageRequest.getFilters());
		XsqlFilterResult countQueryXsqlResult = builder.generateHql(countQuery, otherFilters, pageRequest.getFilters());

		return pageQuery(pageRequest, queryXsqlResult, countQueryXsqlResult);
	}

	protected XsqlBuilder getXsqlBuilder() {
		SessionFactoryImpl sf = (SessionFactoryImpl) (getSessionFactory());
		Dialect dialect = sf.getDialect();

		//or SafeSqlProcesserFactory.getMysql();
		SafeSqlProcesser safeSqlProcesser = SafeSqlProcesserFactory.getFromCacheByHibernateDialect(dialect);
		XsqlBuilder builder = new XsqlBuilder(safeSqlProcesser);

		if (builder.getSafeSqlProcesser().getClass() == DirectReturnSafeSqlProcesser.class) {
			System.err
					.println(BaseHibernateDao.class.getSimpleName()
							+ ".getXsqlBuilder(): 故意报错,你未开启Sql安全过滤,单引号等转义字符在拼接sql时需要转义,不然会导致Sql注入攻击的安全问题，请修改源码使用new XsqlBuilder(SafeSqlProcesserFactory.getDataBaseName())开启安全过滤");
		}
		return builder;
	}

	@SuppressWarnings("unchecked")
	private Page pageQuery(final PageRequest pageRequest, final XsqlFilterResult queryXsqlResult,
			final XsqlFilterResult countQueryXsqlResult) {
		return (Page) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Query query = setQueryParameters(session.createQuery(queryXsqlResult.getXsql()), queryXsqlResult
						.getAcceptedFilters());
				Query countQuery = setQueryParameters(
						session.createQuery(removeOrders(countQueryXsqlResult.getXsql())), countQueryXsqlResult
								.getAcceptedFilters());

				return executeQueryForPage(pageRequest, query, countQuery);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private Object executeQueryForPage(final PageRequest pageRequest, Query query, Query countQuery) {
		Page page = new Page(pageRequest, ((Number) countQuery.uniqueResult()).intValue());
		if (page.getTotalCount() == 0) {
			page.setResult(new ArrayList(0));
		} else {
			page.setResult(query.setFirstResult(page.getFirstResult()).setMaxResults(page.getPageSize()).list());
		}
		return page;
	}

	public static Query setQueryParameters(Query q, Map params) {
		for (Iterator it = params.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			q.setParameter((String) entry.getKey(), entry.getValue());
		}
		return q;
	}

	public void save(E entity) {
		getHibernateTemplate().save(entity);
	}

	public List<E> findAll() {
		return getHibernateTemplate().loadAll(getEntityClass());
	}

	public E getById(PK id) {
		return (E) getHibernateTemplate().get(getEntityClass(), id);
	}

	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public void delete(Serializable entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteById(PK id) {
		Object entity = getById(id);
		if (entity == null) {
			throw new ObjectRetrievalFailureException(getEntityClass(), id);
		}
		getHibernateTemplate().delete(entity);
	}

	public void update(E entity) {
		getHibernateTemplate().update(entity);
	}

	public void saveOrUpdate(E entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void batchDelete(List<E> list) {
		// TODO Auto-generated method stub

	}

	public void batchUpdate(List<E> list) {
		// TODO Auto-generated method stub

	}

	public void batchInsert(List<E> list) {
		// TODO Auto-generated method stub

	}

	public void refresh(Object entity) {
		getHibernateTemplate().refresh(entity);
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	public void saveAll(Collection<E> entities) {
		for (Iterator<E> it = entities.iterator(); it.hasNext();) {
			save(it.next());
		}
	}

	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	public E findByProperty(final String propertyName, final Object value) {

		return (E) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(getEntityClass()).add(Restrictions.eq(propertyName, value)).uniqueResult();
			}
		});
	}

	public List<E> findAllBy(final String propertyName, final Object value) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(getEntityClass()).add(Restrictions.eq(propertyName, value)).list();
			}
		});
	}

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public boolean isUnique(E entity, String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (int i = 0; i < nameList.length; i++) {
				criteria.add(Restrictions.eq(nameList[i], PropertyUtils.getProperty(entity, nameList[i])));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
			if (idName != null) {
				// 取得entity的主键值
				Serializable id = (Serializable) PropertyUtils.getProperty(entity, idName);

				// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
				if (id != null)
					criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
			}
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return ((Number) criteria.uniqueResult()).intValue() == 0;
	}

	public abstract Class getEntityClass();

}
