package javacommon.datamodifier.modifier;

import java.math.BigDecimal;

import javacommon.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class BooleanDataModifier implements DataModifier{
	public Object modify(Object value, String modifierArgument) {
		if(value == null) return null;
		if(value instanceof Boolean) return value;
		return new Boolean(value.toString());
	}
}
