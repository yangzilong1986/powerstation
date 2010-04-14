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
public class DataModifierSyntaxException extends DataModifierException {

	public DataModifierSyntaxException() {
		super();
	}

	public DataModifierSyntaxException(String msg, Throwable e) {
		super(msg, e);
	}

	public DataModifierSyntaxException(String msg) {
		super(msg);
	}

	public DataModifierSyntaxException(Throwable e) {
		super(e);
	}

}
