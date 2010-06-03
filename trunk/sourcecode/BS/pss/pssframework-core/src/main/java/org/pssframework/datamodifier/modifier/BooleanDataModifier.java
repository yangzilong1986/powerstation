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

public class BooleanDataModifier implements DataModifier {
	public Object modify(Object value, String modifierArgument) {
		if (value == null)
			return null;
		if (value instanceof Boolean)
			return value;
		return new Boolean(value.toString());
	}
}
