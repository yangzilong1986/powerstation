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
package org.pssframework.cache;

import java.util.Map;

/**
 * A cache implementation
 * @see play.cache.Cache
 */
public interface ICache {

	public void add(String key, Object value, int expiration);

	public boolean safeAdd(String key, Object value, int expiration);

	public void set(String key, Object value, int expiration);

	public boolean safeSet(String key, Object value, int expiration);

	public void replace(String key, Object value, int expiration);

	public boolean safeReplace(String key, Object value, int expiration);

	public Object get(String key);

	public Map<String, Object> get(String[] keys);

	public long incr(String key, int by);

	public long decr(String key, int by);

	public void clear();

	public void delete(String key);

	public boolean safeDelete(String key);

	public void stop();
}
