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

package org.pssframework.base;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author PSS
 */
@Transactional
public abstract class BaseManager<E, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	//(getClass());

	protected abstract EntityDao getEntityDao();

	@Transactional(readOnly = true)
	public E getById(PK id) throws DataAccessException {
		return (E) getEntityDao().getById(id);
	}

	@Transactional(readOnly = true)
	public List<E> findAll() throws DataAccessException {
		return getEntityDao().findAll();
	}

	/** 根据id检查是否插入或是更新数据 */
	public void saveOrUpdate(E entity) throws DataAccessException {
		getEntityDao().saveOrUpdate(entity);
	}

	/** 插入数据 */
	public void save(E entity) throws DataAccessException {
		getEntityDao().save(entity);
	}

	public void removeById(PK id) throws DataAccessException {
		getEntityDao().deleteById(id);
	}

	public void update(E entity) throws DataAccessException {
		getEntityDao().update(entity);
	}

	@Transactional(readOnly = true)
	public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException {
		return getEntityDao().isUnique(entity, uniquePropertyNames);
	}

}
