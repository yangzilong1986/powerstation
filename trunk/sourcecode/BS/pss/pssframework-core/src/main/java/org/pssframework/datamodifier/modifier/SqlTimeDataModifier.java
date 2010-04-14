package javacommon.datamodifier.modifier;

import java.sql.Time;
import java.text.DateFormat;

import javacommon.datamodifier.DataModifier;
import javacommon.datamodifier.DefaultUtils;

/**
 * @author badqiu
 */
public class SqlTimeDataModifier implements DataModifier{
	
	private static final String DEFAULT_SQLTIME_FORMAT = "HH:mm:ss";
	
	private String dateFormat = DEFAULT_SQLTIME_FORMAT;
	
	public SqlTimeDataModifier(){}
	
	public SqlTimeDataModifier(String dateFormat){
		this.dateFormat = dateFormat;
	}
	
	public Object modify(Object value, String modifierArgument) throws Exception {
		if(value == null) return null;
		if(value instanceof java.sql.Time) return value;
		DateFormat df = DefaultUtils.defaultDateFormat(modifierArgument,dateFormat);
		return new Time(df.parse(value.toString()).getTime());
	}
}
