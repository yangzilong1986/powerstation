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
package org.pssframework.datamodifier;
/**
 * 
 * @author PSS
 *
 */
public class DataModifierException extends RuntimeException{

	public DataModifierException() {
		super();
	}

	public DataModifierException(String msg, Throwable e) {
		super(msg, e);
	}

	public DataModifierException(String msg) {
		super(msg);
	}

	public DataModifierException(Throwable e) {
		super(e);
	}

}
