package javacommon.datamodifier.modifier;

import java.math.BigDecimal;

import javacommon.datamodifier.DataModifier;

/**
 * @author badqiu
 */
public class BigDecimalDataModifier implements DataModifier{
	public Object modify(Object value, String modifierArgument) {
		if(value == null) return null;
		if(value instanceof BigDecimal) return value;
		return new BigDecimal(value.toString());
	}
}
