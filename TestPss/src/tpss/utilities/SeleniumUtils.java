package tpss.utilities;

import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;


public class SeleniumUtils extends SeleneseTestCase{
    
    private Selenium selenium;
    
    
    
    public SeleniumUtils(Selenium selenium) {
        super();
        this.selenium = selenium;
    }

    public Selenium getSelenium() {
        return selenium;
    }

    public void pause(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
        }
    }
    
    public boolean waitForElementEditable(String locator, int seconds) {
        boolean elementEditable = false;
        for (int second = 0;; second++) {

            // If loop is reached 60 seconds then break the loop.
            if (second >= seconds)
                break;

            // Search for element "link=ajaxLink" and if available then break
            // loop.
            try {
                if (selenium.isEditable(locator)) {
                    elementEditable = true;
                    break;
                }

            } catch (Exception e) {
            }

            // Pause for 1 second.
            pause(1000);
        }

        return elementEditable;

    }
        
    
    
    public boolean waitForElement(String locator, int seconds) {
        boolean elementExist = false;
        for (int second = 0;; second++) {

            // If loop is reached 60 seconds then break the loop.
            if (second >= seconds)
                break;

            // Search for element "link=ajaxLink" and if available then break
            // loop.
            try {
                if (selenium.isElementPresent(locator)) {
                    elementExist = true;
                    break;
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            // Pause for 1 second.
            pause(1000);
        }

        return elementExist;

    }
    
    public boolean waitForText(String tmplName, int seconds) {
        boolean textExist = false;
        for (int second = 0;; second++) {

            // If loop is reached 60 seconds then break the loop.
            if (second >= seconds)
                break;

            // Search for element "link=ajaxLink" and if available then break
            // loop.
            try {
                if (selenium.isTextPresent(tmplName)) {
                    textExist = true;
                    break;
                }

            } catch (Exception e) {
            }

            // Pause for 1 second.
            pause(1000);
        }
        
        return textExist;
    }

    /**
     * @param mis
     */
    public void waitForPageToLoad(String mis){
        boolean isLoaded = false;
        int count = 0;
        do{
            if(count++>3)break;
            try{
                selenium.waitForPageToLoad(mis);
                isLoaded = true;
            }catch(Exception ex){
                continue;
            }
        }while(!isLoaded);
    }
}
