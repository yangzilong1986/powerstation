/**
 * 
 */
package org.pssframework.tdd;

import java.util.Hashtable;

/**
 * @author Baocj
 *
 */
public class Bank {

	private static Hashtable<Pair, Integer> rates = new Hashtable<Pair, Integer>();

	/**
	 * @return the rates
	 */
	public Hashtable<Pair, Integer> getRates() {
		return Bank.rates;
	}

	/**
	 * @param rates the rates to set
	 */
	public void setRates(Hashtable<Pair, Integer> rates) {
		Bank.rates = rates;
	}

	public Expression reduce(Expression source, String to) {

		return source.reduce(to);
	}

	int rate(String from, String to) {
		if (from.equals(to))
			return 1;
		return rates.get(new Pair(from, to));
	}

	void addRate(String from, String to, int rate) {
		rates.put(new Pair(from, to), rate);
	}
}
