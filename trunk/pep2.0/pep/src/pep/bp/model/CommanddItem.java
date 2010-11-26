/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.util.List;

/**
 *
 * @author Thinkpad
 */
public class CommanddItem {
    private String protocol_no;
    private String commandItemCode;


    /**
     * @return the protocol_no
     */
    public String getProtocol_no() {
        return protocol_no;
    }

    /**
     * @param protocol_no the protocol_no to set
     */
    public void setProtocol_no(String protocol_no) {
        this.protocol_no = protocol_no;
    }

    /**
     * @return the commandItemCode
     */
    public String getCommandItemCode() {
        return commandItemCode;
    }

    /**
     * @param commandItemCode the commandItemCode to set
     */
    public void setCommandItemCode(String commandItemCode) {
        this.commandItemCode = commandItemCode;
    }
}
