package org.pssframework.datamodifier;
/**
 * 
 * @author PSS
 *
 */
public interface DataModifier {
	
	public Object modify(Object value,String modifierArgument) throws Exception;
	
}