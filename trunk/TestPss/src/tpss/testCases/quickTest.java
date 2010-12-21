/**  
* Filename:    quickTest.java  
* Description:   
* Copyright:   Copyright (c)2009  
* Company:     topsoft 
* @author:     lj  
* @version:    1.0  
* Create at:   2010-12-17 下午02:51:38  
*  
* Modification History:  
* Date         Author      Version     Description  
* ------------------------------------------------------------------  
* 2010-12-17      lj          1.0          1.0 Version  
*/ 
package tpss.testCases;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

import java.util.regex.Pattern;

import org.testng.Assert;


import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class quickTest extends TestCase{
	public Selenium selenium;
	public void setUp() throws Exception{
		selenium = new DefaultSelenium("localhost",4444,"*firefox","http://www.google.com");
		selenium.start();
	}
	
	public void testGoogle () throws Exception{        
		selenium.windowMaximize();
		selenium.open("http://www.google.com/");
		selenium.type("q", "selenium 中文论坛");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
		Assert.assertTrue(selenium.isTextPresent("selenium 中文论坛"));
	}
}
