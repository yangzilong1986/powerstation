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
package org.pssframework.datamodifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 数据类型修饰工具类,可以将一种类型的数据修饰为另一种类型的数据
 * 如String => Integer,String => Date的转换
 * 例1：将String转为Integer, 表达式为var?int
 * 例2：使用多个修饰，表达式为var?string?int
 * 例3: 为修饰传递参数,表达式为var?date(yyyy-MM-dd)
 * @author PSS
 */
public class DataModifierBean {

	private static final String DATA_MODIFIER_OPERATOR = "?";

	private Map modifiers = new HashMap();

	public DataModifierBean() {
	}

	public void registerDataModifier(String modifierName, DataModifier modifier) {
		modifiers.put(modifierName, modifier);
	}

	public void deregisterDataModifier(String modifierName) {
		modifiers.remove(modifierName);
	}

	public Map getAvailableDataModifiers() {
		return Collections.unmodifiableMap(modifiers);
	}

	public static String getModifyVariable(String completeExpression) {
		int index = completeExpression.indexOf(DATA_MODIFIER_OPERATOR);
		if (index == -1)
			return completeExpression;
		return completeExpression.substring(0, index);
	}

	public Object modify(String completeExpression, Object dataModifyValue) {
		int index = completeExpression.indexOf(DATA_MODIFIER_OPERATOR);
		if (index == -1)
			return dataModifyValue;
		String dataModifierExpression = completeExpression.substring(index);
		try {
			return directModify0(dataModifierExpression, dataModifyValue);
		} catch (DataModifierException e) {
			throw e;
		} catch (Exception e) {
			throw new DataModifierException("cannot modify value:" + dataModifyValue + " with completeExpression:"
					+ completeExpression, e);
		}
	}

	/**
	 * @param dataModifierExpression Syntax = ?modifier1?modifier2(modifierArgument2)...
	 * @param dataModifyValue
	 * @return
	 * @throws Exception
	 */
	public Object directModify(String dataModifierExpression, Object dataModifyValue) {
		try {
			return directModify0(dataModifierExpression, dataModifyValue);
		} catch (DataModifierException e) {
			throw e;
		} catch (Exception e) {
			throw new DataModifierException("cannot modify value:" + dataModifyValue + " with expression:"
					+ dataModifierExpression, e);
		}
	}

	private Object directModify0(String dataModifierExpression, Object dataModifyValue) throws DataModifierException,
			Exception {
		if (isEmptyString(dataModifierExpression))
			return dataModifyValue;
		if (!dataModifierExpression.startsWith(DATA_MODIFIER_OPERATOR))
			throw new DataModifierSyntaxException("Syntax error,DataModifier expression must start with ["
					+ DATA_MODIFIER_OPERATOR + "]. auctal:" + dataModifierExpression);

		StringTokenizer tokenizer = new StringTokenizer(dataModifierExpression, DATA_MODIFIER_OPERATOR);
		Object result = dataModifyValue;
		while (tokenizer.hasMoreElements()) {
			String singleExpression = (String) tokenizer.nextElement();
			String modifierName = singleExpression;
			String modifierArgument = null;
			int index = -1;
			if ((index = singleExpression.indexOf("(")) != -1) {
				if (!singleExpression.endsWith(")"))
					throw new DataModifierSyntaxException("Syntax error,modifierArguments must wrap with '(arg1,arg2)'");
				modifierName = modifierName.substring(0, index);
				modifierArgument = singleExpression.substring(index + 1, singleExpression.length() - 1);
			}

			DataModifier dataModifier = (DataModifier) modifiers.get(modifierName);
			if (dataModifier == null)
				throw new DataModifierSyntaxException("not found DataModifier with give name[" + modifierName
						+ "],available modifier names:" + modifiers.keySet());
			result = dataModifier.modify(result, modifierArgument);
		}
		return result;
	}

	private static boolean isEmptyString(String str) {
		if (str == null || str.length() == 0)
			return true;
		return false;
	}

}
