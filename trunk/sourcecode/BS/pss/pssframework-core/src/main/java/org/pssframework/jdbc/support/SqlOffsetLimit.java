package org.pssframework.jdbc.support;

public class SqlOffsetLimit {
	public String sql;
	public int offset;
	public int limit;

	public SqlOffsetLimit(String sql, int offset, int limit) {
		this.sql = sql;
		this.offset = offset;
		this.limit = limit;
	}
}