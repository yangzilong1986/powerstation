/**  
* Filename:    TestPlanTripQuery.java  
* Description:   
* Copyright:   Copyright (c)2009  
* Company:     topsoft 
* @author:     lj  
* @version:    1.0  
* Create at:   2010-12-17 ÉÏÎç10:07:15  
*  
* Modification History:  
* Date         Author      Version     Description  
* ------------------------------------------------------------------  
* 2010-12-17      lj          1.0          1.0 Version  
*/ 
package tpss.tasks;

import java.util.HashMap;
import java.util.Properties;

import tpss.utilities.PropUtils;
import tpss.utilities.SeleniumUtils;



import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

/**
 * @author lijun 
 *
 */
public class TestLoginTasks {
	private final static String PROP_NAME = "tpss/appObjects/login.properties";

    private SeleniumUtils       utils;

    private Selenium            selenium;

    private Properties          elemMap;

    private SeleneseTestCase    stc       = new SeleneseTestCase();

    public TestLoginTasks(SeleniumUtils utils) {
        this.utils = utils;
        selenium = this.utils.getSelenium();
        elemMap = PropUtils.getProperties(PROP_NAME);
    }

    public void openSite() {
        selenium.open("http://115.238.74.114:9090/psstest/");
		/*selenium.type("j_captcha", "tite");
		//selenium.click("submit1");
		selenium.waitForPageToLoad("30000");*/
    }
    
    public void typeLoginInfo(HashMap<String, Object> paraMap){
    	System.out.println("the elemMap is" + elemMap + "..............");
        stc.verifyTrue(utils.waitForElement((String) elemMap
                .get(TestPssConstants.AUTHCODE), 30));
        selenium.type((String) elemMap
                .get(TestPssConstants.AUTHCODE),
                (String) paraMap
                        .get(TestPssConstants.AUTHCODE));
        selenium.type((String) elemMap
                .get(TestPssConstants.INPUT_USERNAME),
                (String) paraMap
                        .get(TestPssConstants.INPUT_USERNAME));
        selenium.type((String) elemMap
                .get(TestPssConstants.INPUT_PASSWORD),
                (String) paraMap
                        .get(TestPssConstants.INPUT_PASSWORD));
    }
    
    public void clickLoginBtn() {
    	stc.verifyTrue(utils.waitForElement((String) elemMap
                .get(TestPssConstants.BUTTON_LOGIN), 30));
        selenium.click((String) elemMap
                .get(TestPssConstants.BUTTON_LOGIN));
    	
    }
}
