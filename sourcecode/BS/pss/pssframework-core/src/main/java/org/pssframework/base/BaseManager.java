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
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author PSS
 */
@Transactional
public abstract class BaseManager<E, PK extends Serializable> implements Manager<E, PK> {

	protected Log log = LogFactory.getLog(getClass());

	protected abstract EntityDao getEntityDao();

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#getById(PK)
	 */
	@Transactional(readOnly = true)
	public E getById(PK id) throws DataAccessException {
		return (E) getEntityDao().getById(id);
	}

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#findAll()
	 */
	@Transactional(readOnly = true)
	public List<E> findAll() throws DataAccessException {
		return getEntityDao().findAll();
	}

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#findAll()
	 */
	@Transactional(readOnly = true)
	public List<E> findAll(Map<?, ?> map) throws DataAccessException {
		return getEntityDao().findAll(map);
	}

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#saveOrUpdate(E)
	 */
	public void saveOrUpdate(E entity) throws DataAccessException {
		getEntityDao().saveOrUpdate(entity);
	}

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#save(E)
	 */
	public void save(E entity) throws DataAccessException {
		getEntityDao().save(entity);
	}

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#removeById(PK)
	 */
	public void removeById(PK id) throws DataAccessException {
		getEntityDao().deleteById(id);
	}

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#update(E)
	 */
	public void update(E entity) throws DataAccessException {
		getEntityDao().update(entity);
	}

	/* (non-Javadoc)
	 * @see org.pssframework.base.Manager#isUnique(E, java.lang.String)
	 */
	@Transactional(readOnly = true)
	public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException {
		return getEntityDao().isUnique(entity, uniquePropertyNames);
	}

}
