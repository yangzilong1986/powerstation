package org.pssframework.web.session.codec;

import java.io.IOException;
import java.util.Map;

public interface MapEncoder  {
	
	public String encode(Map map) throws IOException;
	
}
