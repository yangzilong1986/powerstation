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
package org.pssframework.web.session.codec.json;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.map.ObjectMapper;
import org.pssframework.web.session.codec.MapDecoder;

public class JsonMapDecoder implements MapDecoder{
	private ObjectMapper objectMapper = new ObjectMapper();

	private JsonEncoding encoding = JsonEncoding.UTF8;
	
	public String decode(String str) {
		return null;
	}

}
