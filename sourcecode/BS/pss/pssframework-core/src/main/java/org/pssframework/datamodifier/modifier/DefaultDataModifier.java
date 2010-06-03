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

package org.pssframework.datamodifier.modifier;

import org.pssframework.datamodifier.DataModifier;
import org.pssframework.datamodifier.DefaultUtils;

/**
 * @author PSS
 */
public class DefaultDataModifier implements DataModifier {
	private static final String DEFAULT_STRING = "";

	public Object modify(Object value, String modifierArgument) {
		if (value == null)
			return DefaultUtils.defaultString(modifierArgument, DEFAULT_STRING);
		return value;
	}
}
