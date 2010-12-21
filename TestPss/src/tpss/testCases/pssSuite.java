/**  
* Filename:    pssSuite.java  
* Description:   
* Copyright:   Copyright (c)2009  
* Company:     topsoft 
* @author:     lj  
* @version:    1.0  
* Create at:   2010-12-17 ����10:09:03  
*  
* Modification History:  
* Date         Author      Version     Description  
* ------------------------------------------------------------------  
* 2010-12-17      lj          1.0          1.0 Version  
*/ 
package tpss.testCases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;





import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * @author lijun 
 *
 */
public class pssSuite extends SeleneseTestCase{
	RemoteControlConfiguration rcc = new RemoteControlConfiguration();

    SeleniumServer             SELENIUM_SERVER;

    @BeforeClass
    public void setUp() {
        try {
            rcc.setPort(4444);
            SELENIUM_SERVER = new SeleniumServer(rcc);
            System.out.println("Selemium server is strating...");
            SELENIUM_SERVER.start();
            System.out.println("Selemium server is started...");
        } catch (Exception e) {
            throw new IllegalStateException("Can't start selenium server", e);
        }

    }
    
    public static void main(String args[]){
    	pssSuite ps = new pssSuite();
        ps.setUp();
        
        //suite tag
        XmlSuite suite = new XmlSuite();
        // set suite name
        suite.setName("Test PSS-WEB");
        // set parameter tag
        HashMap<String, String> para = new HashMap<String, String>();
        para.put("login_para", "/resources/login_para.xml");

        suite.setParameters(para);

        //登录
        XmlTest testQuery = new XmlTest(suite);
        testQuery.setName("Test Login");
        List<XmlClass> queryClasses = new ArrayList<XmlClass>();
        queryClasses.add(new XmlClass(TestLoginTestCase.class));
        testQuery.setXmlClasses(queryClasses);
        
        
        
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();

        ps.tearDown();
    }


    @AfterClass
    public void tearDown() {
        if (SELENIUM_SERVER != null) {
            SELENIUM_SERVER.stop();
        }
    }
}
