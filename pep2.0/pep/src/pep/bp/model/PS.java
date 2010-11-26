/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;


/**
 *
 * @author Thinkpad
 */
public class PS {
    private String logicAddress;
    private int gp_sn;
    private String gp_addr;
    private String test_day;
    private String test_hour;



    public PS(){
    }

    /**
     * @return the logicAddress
     */
    public String getLogicAddress() {
        return logicAddress;
    }

    /**
     * @param logicAddress the logicAddress to set
     */
    public void setLogicAddress(String logicAddress) {
        this.logicAddress = logicAddress;
    }

    /**
     * @return the gp_sn
     */
    public int getGp_sn() {
        return gp_sn;
    }

    /**
     * @param gp_sn the gp_sn to set
     */
    public void setGp_sn(int gp_sn) {
        this.gp_sn = gp_sn;
    }

    /**
     * @return the mp_addr
     */
    public String getGp_addr() {
        return gp_addr;
    }

    /**
     * @param mp_addr the mp_addr to set
     */
    public void setGp_addr(String gp_addr) {
        this.gp_addr = gp_addr;
    }

    /**
     * @return the test_day
     */
    public String getTest_day() {
        return test_day;
    }

    /**
     * @param test_day the test_day to set
     */
    public void setTest_day(String test_day) {
        this.test_day = test_day;
    }

    /**
     * @return the test_hour
     */
    public String getTest_hour() {
        return test_hour;
    }

    /**
     * @param test_hour the test_hour to set
     */
    public void setTest_hour(String test_hour) {
        this.test_hour = test_hour;
    }



}
