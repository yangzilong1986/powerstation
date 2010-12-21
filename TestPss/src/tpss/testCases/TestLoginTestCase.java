/**  
* Filename:    TestQueryTestCase.java  
* Description:   
* Copyright:   Copyright (c)2009  
* Company:     topsoft 
* @author:     lj  
* @version:    1.0  
* Create at:   2010-12-17 ÉÏÎç10:14:40  
*  
* Modification History:  
* Date         Author      Version     Description  
* ------------------------------------------------------------------  
* 2010-12-17      lj          1.0          1.0 Version  
*/ 
package tpss.testCases;

import java.util.HashMap;

import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tpss.tasks.TestLoginTasks;
import tpss.utilities.SeleniumUtils;
import tpss.utilities.XMLParser;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * @author Administrator
 *
 */
public class TestLoginTestCase extends SeleneseTestCase{
	private HashMap<String, Object> paraMap;

    private TestLoginTasks         tlTasks;

    private SeleniumUtils           utils;

    RemoteControlConfiguration      rcc = new RemoteControlConfiguration();

    SeleniumServer                  SELENIUM_SERVER;

    @BeforeClass
    public void setup() {
        selenium = new DefaultSelenium("localhost", 4444, "*iexplore",
                "http://115.238.74.114:9090/psstest/");
        System.out.println("Starting selenium.");
        selenium.start();
        utils = new SeleniumUtils(selenium);
        tlTasks = new TestLoginTasks(utils);
    }

    @Parameters( { "login_para" })
    @Test
    public void testLogin(String paraFile) {
        paraMap = (HashMap<String, Object>) XMLParser.getInstance()
                .parserXml(paraFile);
        System.out.println("the paraMap is" + paraMap);

        tlTasks.openSite();
        tlTasks.typeLoginInfo(paraMap);
        tlTasks.clickLoginBtn();
        utils.pause(10000);
    }
    
    
    
    @AfterClass
    public void stop(){
        if(selenium != null){
            selenium.stop();
        }
    }
}
