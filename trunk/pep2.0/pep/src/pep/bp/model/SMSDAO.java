/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;


/**
 *
 * @author Thinkpad
 */
public class SMSDAO {
    private String logicAddress;
    private int smsid;
    private String gp_addr;




    public SMSDAO(){
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
     * @return the smsid
     */
    public int getSmsid() {
        return smsid;
    }

    /**
     * @param smsid the smsid to set
     */
    public void setSmsid(int smsid) {
        this.smsid = smsid;
    }




}
