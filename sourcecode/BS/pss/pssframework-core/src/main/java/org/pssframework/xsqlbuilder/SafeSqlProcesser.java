package javacommon.xsqlbuilder;
/**
 * sql字符串安全处理接口,防止sql注入攻击
 * @author badqiu
 */
public interface SafeSqlProcesser {

	public String process(String value);
	
}
