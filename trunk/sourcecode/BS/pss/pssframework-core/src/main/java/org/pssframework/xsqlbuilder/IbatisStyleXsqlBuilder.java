package org.pssframework.xsqlbuilder;

/**
 * 支持Ibatis类似的语法
 * <pre>
 * 		String sql = "select * from user where 1=1"
 *			+"/~ and username = #username#~/"
 *			+"/~ and pwd = '$password$'~/";
 * </pre>
 * @author badqiu
 *
 */
public class IbatisStyleXsqlBuilder extends XsqlBuilder{
	
	public IbatisStyleXsqlBuilder() {
		setAsIbatisStyle();
	}

	public IbatisStyleXsqlBuilder(boolean isRemoveEmptyStrings,
			SafeSqlProcesser safeSqlProcesser) {
		super(isRemoveEmptyStrings, safeSqlProcesser);
		setAsIbatisStyle();
	}

	public IbatisStyleXsqlBuilder(boolean isRemoveEmptyStrings) {
		super(isRemoveEmptyStrings);
		setAsIbatisStyle();
	}

	public IbatisStyleXsqlBuilder(SafeSqlProcesser safeSqlProcesser) {
		super(safeSqlProcesser);
		setAsIbatisStyle();
	}
	
	private void setAsIbatisStyle() {
		markKeyEndChar = "#";
		markKeyStartChar = "#";
		
		replaceKeyEndChar = "$";
		replaceKeyStartChar = "$";
	}
	
	
}
