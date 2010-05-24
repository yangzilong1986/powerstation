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

/**
 * @author PSS
 */
public interface EntityDao<E, PK extends Serializable> {

	public Object getById(PK id);

	public void deleteById(PK id);

	public void save(E entity);

	public void update(E entity);

	public void saveOrUpdate(E entity);

	public boolean isUnique(E entity, String uniquePropertyNames);

	public void flush();

	public List<E> findAll();
	
	public void batchInsert(List<E> list);
	
	public void batchUpdate(List<E> list);
	
	public void batchDelete(List<E> list);
	
}
