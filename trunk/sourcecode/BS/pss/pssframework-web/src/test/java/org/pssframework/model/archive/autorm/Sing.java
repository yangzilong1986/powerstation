package org.pssframework.model.archive.autorm;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-7-25
 * Time: 16:46:31
 * To change this template use File | Settings | File Templates.
 */
public class Sing {
	private static Sing ourInstance = new Sing();

	public static Sing getInstance() {
		return ourInstance;
	}

	private Sing() {
	}
}
