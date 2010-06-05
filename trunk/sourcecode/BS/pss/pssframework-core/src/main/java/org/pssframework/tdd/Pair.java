/**
 * 
 */
package org.pssframework.tdd;

/**
 * @author Baocj
 *
 */
public class Pair {
	private String from;
	private String to;

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @param from
	 * @param to
	 */
	public Pair(String from, String to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean equals(Object object) {
		Pair pair = (Pair) object;
		return from.equals(pair.getFrom()) && to.equals(pair.getTo());
	}

	//	public int hashCode() {
	//		return 0;
	//	}

}
