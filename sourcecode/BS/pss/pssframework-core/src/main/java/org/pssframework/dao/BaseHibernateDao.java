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
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.pssframework.base.EntityDao;
import org.pssframework.xsqlbuilder.SafeSqlProcesser;
import org.pssframework.xsqlbuilder.SafeSqlProcesserFactory;
import org.pssframework.xsqlbuilder.XsqlBuilder;
import org.pssframework.xsqlbuilder.XsqlBuilder.XsqlFilterResult;
import org.pssframework.xsqlbuilder.safesql.DirectReturnSafeSqlProcesser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

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

	protected XsqlFilterResult queryXsqlResult(final String query, final Map<String, ?> filters) {
		XsqlBuilder builder = getXsqlBuilder();
		//混合使用otherFilters与pageRequest.getFilters()为一个filters使用
		XsqlFilterResult queryXsqlResult = builder.generateHql(query, filters);
		return queryXsqlResult;
	}

	@SuppressWarnings("unchecked")
	private List queryAll(final String query, final Map<String, ?> filters, final boolean hql) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				XsqlFilterResult queryXsqlResult = queryXsqlResult(query, filters);

				Query query = setQueryParameters((hql ? session.createQuery(queryXsqlResult.getXsql()) : session
						.createSQLQuery(queryXsqlResult.getXsql())), queryXsqlResult.getAcceptedFilters());

				return query.list();
			}
		});
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

	public void batchDelete(Collection<E> entities) {
		getHibernateTemplate().deleteAll(entities);

	}

	public void batchUpdate(Collection<E> entities) {
		for (E e : entities) {
			saveOrUpdate(e);
		}

	}

	public void batchInsert(Collection<E> entities) {
		for (E e : entities) {
			save(e);
		}
	}

	/******************************************************************************/
	public long queryForLong(final String queryString) {
		return queryForLong(queryString, new Object[] {});
	}

	public long queryForLong(final String queryString, Object value) {
		return queryForLong(queryString, new Object[] { value });
	}

	public long queryForLong(final String queryString, Object[] values) {
		return DataAccessUtils.longResult(getHibernateTemplate().find(queryString, values));
	}

	/**
	 * 得到全部数据,但执行分页
	 * @param pageRequest
	 * @return
	 */
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
				return PageQueryUtils.executeQueryForPage(pageRequest, query, countQuery);
			}
		});
	}

	public Page pageQuery(final String sql, final PageRequest pageRequest) {
		final String countQuery = "select count(*) " + removeSelect(removeFetchKeyword((sql)));
		return pageQuery(sql, countQuery, pageRequest);
	}

	public Page pageQuery(final String sql, String countQuery, final PageRequest pageRequest) {
		Map otherFilters = new HashMap(1);
		otherFilters.put("sortColumns", pageRequest.getSortColumns());

		XsqlBuilder builder = getXsqlBuilder();

		//混合使用otherFilters与pageRequest为一个filters使用
		XsqlFilterResult queryXsqlResult = builder.generateHql(sql, otherFilters, pageRequest);
		XsqlFilterResult countQueryXsqlResult = builder.generateHql(countQuery, otherFilters, pageRequest);

		return PageQueryUtils.pageQuery(getHibernateTemplate(), pageRequest, queryXsqlResult, countQueryXsqlResult);
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

	static class PageQueryUtils {
		private static Page pageQuery(HibernateTemplate template, final PageRequest pageRequest,
				final XsqlFilterResult queryXsqlResult, final XsqlFilterResult countQueryXsqlResult) {
			return (Page) template.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {

					Query query = setQueryParameters(session.createQuery(queryXsqlResult.getXsql()), pageRequest);
					Query countQuery = setQueryParameters(session.createQuery(removeOrders(countQueryXsqlResult
							.getXsql())), pageRequest);

					return executeQueryForPage(pageRequest, query, countQuery);
				}
			});
		}

		private static Object executeQueryForPage(final PageRequest pageRequest, Query query, Query countQuery) {
			Page page = new Page(pageRequest, ((Number) countQuery.uniqueResult()).intValue());
			if (page.getTotalCount() <= 0) {
				page.setResult(new ArrayList(0));
			} else {
				page.setResult(query.setFirstResult(page.getFirstResult()).setMaxResults(page.getPageSize()).list());
			}
			return page;
		}

		public static Query setQueryParameters(Query q, Object params) {
			q.setProperties(params);
			return q;
		}

		public static Query setQueryParameters(Query q, Map params) {
			q.setProperties(params);
			return q;
		}
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
				return session.createCriteria(getEntityClass()).add(Restrictions.eq(propertyName, value))
						.uniqueResult();
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

	/*************************************************************************/

	/**
	 *	获取全部对象.
	 */
	public List<E> getAll() {
		return find();
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<E> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public E findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (E) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按id列表获取对象.
	 */
	public List<E> findByIds(List<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> findAll(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> List<X> findAll(final String hql, final Map<String, ?> values) {
		return findAllByHql(hql, values);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> List<X> findAllByHql(final String hql, final Map<String, ?> values) {
		return queryAll(hql, values, true);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> List<X> findAllBySql(final String hql, final Map<String, ?> values) {
		return queryAll(hql, values, false);
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public List<E> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public E findUnique(final Criterion... criterions) {
		return (E) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,可实现新的函数,执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initEntity(E entity) {
		Hibernate.initialize(entity);
	}

	/**
	 * @see #initEntity(Object)
	 */
	public void initEntity(List<E> entityList) {
		for (E entity : entityList) {
			Hibernate.initialize(entity);
		}
	}

	/**
	 * 为Query添加distinct transformer.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(getEntityClass());
		return meta.getIdentifierPropertyName();
	}
	/************************************************************************/

}
