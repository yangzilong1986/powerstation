/**
 * 
 */
package org.pssframework.tdd;

/**
 * @author Baocj
 *
 */
public class Money implements Expression {

	protected int amount;
	protected String currency;

	public Money() {
	}

	public Money(int amount, String currency) {
		this.currency = currency;
		this.amount = amount;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	public Money(int amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object object) {
		Money money = (Money) object;
		return this.amount == money.amount && currency.equals(money.currency());
	}

	//子类实现
	public Expression times(int i) {
		return new Money(i * amount, currency);
	}

	public static Money dollar(int i) {
		// TODO Auto-generated method stub
		return new Dollar(i, "USD");
	}

	public String currency() {
		return currency;
	}

	public static Money franc(int i) {
		// TODO Auto-generated method stub
		return new Franc(i, "CHF");
	}

	@Override
	public String toString() {
		return amount + " " + currency;
	}

	public Expression plus(Expression add) {
		// TODO Auto-generated method stub
		return new Sum(this, add);
	}

	public Expression reduce(String to) {
		// TODO Auto-generated method stub
		return this;
	}

	public Money reduce(Bank bank, String to) {
		// TODO Auto-generated method stub
		return new Money(amount / bank.rate(currency, to), to);
	}
}
