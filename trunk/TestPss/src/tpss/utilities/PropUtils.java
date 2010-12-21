package tpss.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class PropUtils {
	/** true if the Properties has been initialized. */
	private static Properties properties = null;
	private PropUtils() {
	}

	private static synchronized void initialize(String prop) {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(prop);
		if (is == null) {
		    System.out.println("The prop is null.");
			return;
		}
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
		    System.out.println("properties loading fails.");
			throw new RuntimeException(e);
		}finally{
			try{
				if(is!=null)
					is.close();
			}catch(Exception ex){
			    System.out.println("properties loading fails for runtime exception.");
				throw new RuntimeException(ex);
			}
		}
	}

	public static synchronized Properties getProperties(String prop)
			throws RuntimeException {
		initialize(prop);
		return properties;
	}
	
	public static String getFormatString(String value, Object[] params) {
	    if (params != null) {
	    	MessageFormat mf = new MessageFormat(value);
	    	value = mf.format(params);
	    }
	    return value;
	}
}
