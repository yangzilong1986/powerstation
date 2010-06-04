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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @author PSS
 */
public interface EntityDao<E, PK extends Serializable> {

	public Object getById(PK id) throws DataAccessException;

	public void deleteById(PK id) throws DataAccessException;

	public void save(E entity) throws DataAccessException;

	public void update(E entity) throws DataAccessException;

	public void saveOrUpdate(E entity) throws DataAccessException;

	public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException;

	public void flush() throws DataAccessException;

	public List<E> findAll() throws DataAccessException;

	public List<E> findAll(Map<?, ?> map) throws DataAccessException;

	public void batchInsert(Collection<E> entities) throws DataAccessException;;

	public void batchUpdate(Collection<E> entities) throws DataAccessException;

	public void batchDelete(Collection<E> entities) throws DataAccessException;

}
