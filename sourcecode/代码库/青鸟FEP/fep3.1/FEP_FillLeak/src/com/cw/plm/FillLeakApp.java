/**
 * com.cw.plm = com.creaway.PowerLoadManagement
 * Fill Leak (婕忕偣琛ュ彫.) (漏点补召)
 */
package com.cw.plm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hzjbbis.fk.FasSystem;
import com.hzjbbis.fk.utils.ApplicationContextUtil;

/**
 * @author bhw
 *
 */
public class FillLeakApp {

	public static void main(String[] args) {
		//初始化class path
		String path[] = new String[] { 
				"classpath*:applicationContext-common.xml",
				"classpath*:applicationContext-socket.xml",
				"classpath*:applicationContext-db-batch.xml",
				"classpath*:applicationContext.xml"
				};
		ApplicationContext context = new ClassPathXmlApplicationContext(path);
		ApplicationContextUtil.setContext(context);
		//1. 加载FasSystem，负责所有模块的启动和停止。
		FasSystem fasSystem = (FasSystem)context.getBean("fasSystem");
		fasSystem.setApplicationContext(context);
		
		fasSystem.startSystem();
	}

}
