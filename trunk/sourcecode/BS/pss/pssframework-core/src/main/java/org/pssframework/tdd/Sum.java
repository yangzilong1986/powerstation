package org.pssframework.tdd;

public class Sum implements Expression {

	private Expression addend;
	private Expression augend;

	public Sum(Expression augend, Expression addend) {
		// TODO Auto-generated constructor stub
		this.augend = augend;
		this.addend = addend;
	}

	public Sum(Money augend, Money addend) {
		// TODO Auto-generated constructor stub
		this.augend = augend;
		this.addend = addend;
	}

	public Sum(Expression augend) {
	}

	/**
	 * @return the addend
	 */
	public Expression getAddend() {
		return addend;
	}

	/**
	 * @param addend the addend to set
	 */
	public void setAddend(Expression addend) {
		this.addend = addend;
	}

	/**
	 * @return the augend
	 */
	public Expression getAugend() {
		return this.augend;
	}

	/**
	 * @param augend the augend to set
	 */
	public void setAugend(Expression augend) {
		this.augend = augend;
	}

	public Expression plus(Expression addend) {
		// TODO Auto-generated method stub
		return null;
	}

	public Sum add(Sum sum) {
		return this;
	}

	public Money reduce(Bank bank, String to) {
		return new Money(12, to);
	}

	public Expression plus(Money addend) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression reduce(String to) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression times(int i) {
		// TODO Auto-generated method stub
		return new Sum(augend.times(i), addend.times(i));
	}

}
