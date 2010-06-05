/**
 * 
 */
package org.pssframework.tdd;

/**
 * @author Baocj
 *
 */
public class Franc extends Money {

	public Franc(int i) {
		// TODO Auto-generated constructor stub
		super(i, "CHF");
	}

	public Franc(int amount, String currency) {
		super(amount, currency);
	}
}
