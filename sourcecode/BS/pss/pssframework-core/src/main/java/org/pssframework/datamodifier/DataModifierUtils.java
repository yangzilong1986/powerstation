package org.pssframework.datamodifier;
import java.util.Map;

import org.pssframework.datamodifier.modifier.BigDecimalDataModifier;
import org.pssframework.datamodifier.modifier.BigIntegerDataModifier;
import org.pssframework.datamodifier.modifier.BooleanDataModifier;
import org.pssframework.datamodifier.modifier.ByteDataModifier;
import org.pssframework.datamodifier.modifier.DateDataModifier;
import org.pssframework.datamodifier.modifier.DefaultDataModifier;
import org.pssframework.datamodifier.modifier.DoubleDataModifier;
import org.pssframework.datamodifier.modifier.FloatDataModifier;
import org.pssframework.datamodifier.modifier.IntegerDataModifier;
import org.pssframework.datamodifier.modifier.LongDataModifier;
import org.pssframework.datamodifier.modifier.ShortDataModifier;
import org.pssframework.datamodifier.modifier.SqlDateDataModifier;
import org.pssframework.datamodifier.modifier.SqlTimeDataModifier;
import org.pssframework.datamodifier.modifier.StringDataModifier;
import org.pssframework.datamodifier.modifier.TimestampDataModifier;

/**
 * 工具类,将DataModifierBean的相关方法static化,并注册默认的修饰符
 * <pre>
		default = new DefaultDataModifier()
		boolean = new BooleanDataModifier()
		string = new StringDataModifier()
		byte = new ByteDataModifier()
		short = new ShortDataModifier()
		int = new IntegerDataModifier()
		long = new LongDataModifier()
		float = new FloatDataModifier()
		double = new DoubleDataModifier()
		BigInteger = new BigIntegerDataModifier()
		BigDecimal = new BigDecimalDataModifier()
		date = new DateDataModifier()
		sqldate = new SqlDateDataModifier()
		sqltime = new SqlTimeDataModifier()
		timestamp = new TimestampDataModifier()
 * </pre>
 * @author PSS
 *
 */
public class DataModifierUtils {
	
	private static DataModifierBean delegate = new DataModifierBean();
	
	static{
		registerDefaultDataModifiers(delegate);
	}
	
	private DataModifierUtils(){
	}
	
	public static void registerDefaultDataModifiers(DataModifierBean bean) {
		bean.registerDataModifier("default",new DefaultDataModifier());
		bean.registerDataModifier("boolean",new BooleanDataModifier());
		bean.registerDataModifier("string",new StringDataModifier());
		bean.registerDataModifier("byte",new ByteDataModifier());
		bean.registerDataModifier("short",new ShortDataModifier());
		bean.registerDataModifier("int",new IntegerDataModifier());
		bean.registerDataModifier("long",new LongDataModifier());
		bean.registerDataModifier("float",new FloatDataModifier());
		bean.registerDataModifier("double",new DoubleDataModifier());
		bean.registerDataModifier("BigInteger",new BigIntegerDataModifier());
		bean.registerDataModifier("BigDecimal",new BigDecimalDataModifier());
		bean.registerDataModifier("date",new DateDataModifier());
		bean.registerDataModifier("sqldate",new SqlDateDataModifier());
		bean.registerDataModifier("sqltime",new SqlTimeDataModifier());
		bean.registerDataModifier("timestamp",new TimestampDataModifier());
	}
	
	public static void deregisterDataModifier(String modifierName) {
		delegate.deregisterDataModifier(modifierName);
	}

	public static void registerDataModifier(String modifierName, DataModifier modifier) {
		delegate.registerDataModifier(modifierName, modifier);
	}
	
	public Map getAvailableDataModifiers() {
		return delegate.getAvailableDataModifiers();
	}

	public static Object directModify(String dataModifierExpression, Object dataModifyValue) {
		return delegate.directModify(dataModifierExpression, dataModifyValue);
	}

	public static Object modify(String completeExpression, Object dataModifyValue) {
		return delegate.modify(completeExpression, dataModifyValue);
	}

	public static String getModifyVariable(String completeExpression) {
		return DataModifierBean.getModifyVariable(completeExpression);
	}
	
}
