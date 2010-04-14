package javacommon.datamodifier.modifier;

import java.util.Date;

import javacommon.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class DoubleDataModifier implements DataModifier{
	public Object modify(Object value, String modifierArgument) {
		if(value == null) return null;
		if(value instanceof Double) return value;
		return new Double(value.toString());
	}
}
