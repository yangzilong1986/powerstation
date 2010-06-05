package org.pssframework.tdd;

public interface Expression {
	public Expression plus(Expression addend);

	public Expression reduce(String to);

	public Expression reduce(Bank bank, String to);

	public Expression times(int i);
}
