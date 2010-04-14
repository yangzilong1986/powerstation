package javacommon.datamodifier.modifier;

import javacommon.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class LongDataModifier implements DataModifier{
	public Object modify(Object value, String modifierArgument) {
		if(value == null) return null;
		if(value instanceof Long) return value;
		return new Long(value.toString());
	}
}
