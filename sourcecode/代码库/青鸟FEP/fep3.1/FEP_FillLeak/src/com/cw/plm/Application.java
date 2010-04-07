package com.cw.plm;

import com.hzjbbis.fk.utils.ClassLoaderUtil;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassLoaderUtil.initializeClassPath();
		FillLeakApp.main(args);
	}

}
