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
package org.pssframework.consistenthash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通过md5提供更加均匀分布的hash
 * @author PSS
 *
 */
public class MD5HashFunction implements HashFunction {

	public int hash(Object obj) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("NoSuchAlgorithmException:MD5", e);
		}
		md5.update(obj.toString().getBytes());
		return Math.abs(new String(md5.digest()).hashCode());
	}

}
