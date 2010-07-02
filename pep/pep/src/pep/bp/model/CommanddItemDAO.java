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
public class CommanddItemDAO {
    private String protocol_no;
    private String commandItemCode;
    private String commandItemName;
    private String gp_char;
    private String aux_info;
    private List<DataItemDAO> DataItems;

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

    /**
     * @return the commandItemName
     */
    public String getCommandItemName() {
        return commandItemName;
    }

    /**
     * @param commandItemName the commandItemName to set
     */
    public void setCommandItemName(String commandItemName) {
        this.commandItemName = commandItemName;
    }

    /**
     * @return the gp_char
     */
    public String getGp_char() {
        return gp_char;
    }

    /**
     * @param gp_char the gp_char to set
     */
    public void setGp_char(String gp_char) {
        this.gp_char = gp_char;
    }

    /**
     * @return the aux_info
     */
    public String getAux_info() {
        return aux_info;
    }

    /**
     * @param aux_info the aux_info to set
     */
    public void setAux_info(String aux_info) {
        this.aux_info = aux_info;
    }

    /**
     * @return the DataItems
     */
    public List<DataItemDAO> getDataItems() {
        return DataItems;
    }

    /**
     * @param DataItems the DataItems to set
     */
    public void setDataItems(List<DataItemDAO> DataItems) {
        this.DataItems = DataItems;
    }
}
