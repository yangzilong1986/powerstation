/**
 * 
 */
package org.pssframework.tdd;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Baocj
 *
 */
public class DollarTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void multiplication() {
	}

	@Test
	public void equality() {
		assertTrue(new Dollar(5).equals(new Dollar(5)));

		assertFalse(new Dollar(5).equals(new Dollar(6)));

		assertTrue(new Franc(5).equals(new Franc(5)));

		assertFalse(new Franc(5).equals(new Franc(6)));

		assertFalse(new Dollar(5).equals(new Franc(5)));
	}

	@Test
	public void currency() {
		assertEquals("USD", Money.dollar(1).currency());
		assertEquals("CHF", Money.franc(1).currency());
	}

	@Test
	public void addition() {
		Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
		Bank bank = new Bank();
		Expression result = bank.reduce(sum, "USD");
		//assertEquals(Money.dollar(7), result);
	}

	@Test
	public void reduce() {
		Bank bank = new Bank();
		Expression result = bank.reduce(Money.dollar(1), "USD");
		assertEquals(Money.dollar(1), result);
	}

	@Test
	public void reduceMoneyDiffCurrency() {
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Expression result = bank.reduce(Money.franc(2), "USD");
		assertEquals(Money.dollar(1), result);
	}

	@Test
	public void arrayEquals() {
		assertArrayEquals(new Object[] { "abc" }, new Object[] { "abc" });
	}

	@Test
	public void indentityRate() {
		assertEquals(1, new Bank().rate("USD", "USD"));
	}

	@Test
	public void mixedAdd() {
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		if (!bank.getRates().containsKey(new Pair("CHF", "USD"))) {
			bank.addRate("CHF", "USD", 2);
		}
		Expression result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
		assertEquals(Money.dollar(10), result);

	}

	@Test
	public void sumTime() {
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		if (!bank.getRates().containsKey(new Pair("CHF", "USD"))) {
			bank.addRate("CHF", "USD", 2);
		}
		Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
		Expression result = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(10), result);
	}

	@Test
	public void plusSameCurrencyRet() {
		Expression sum = Money.dollar(1).plus(Money.dollar(1));
		assertTrue(sum instanceof Money);
	}
}
